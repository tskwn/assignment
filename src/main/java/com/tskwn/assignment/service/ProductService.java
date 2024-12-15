package com.tskwn.assignment.service;

import com.tskwn.assignment.dto.BrandProductDto;
import com.tskwn.assignment.dto.CategoryLowestAndHighestPriceDto;
import com.tskwn.assignment.dto.ProductDto;
import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.entity.Category;
import com.tskwn.assignment.entity.Product;
import com.tskwn.assignment.repository.BrandRepository;
import com.tskwn.assignment.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 상품 정보를 관리하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final ResourceLoader resourceLoader;

    @Transactional
    public void initData() throws IOException {
        String filePath = "classpath:data.csv";
        InputStream inputStream = resourceLoader.getResource(filePath).getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        productRepository.deleteAll();
        brandRepository.deleteAll();
        String line;
        while ((line = br.readLine()) != null) {
            String brandName = line.split(",")[0];
            String category = line.split(",")[1];
            Long price = Long.parseLong(line.split(",")[2]);
            Brand brand = brandRepository.findOneByName(brandName).orElseGet(() -> brandRepository.save(Brand.builder()
                    .name(brandName).build()));
            Product product = Product.builder().brand(brand).category(Category.valueOf(category)).price(price).build();
            productRepository.save(product);
        }
    }

    /**
     * 카테고리 별 최저가격 브랜드와 상품 가격을 조회함.
     *
     * @return ProductDto List
     */
    public List<ProductDto> getCategoryLowestPrice() {
        return Arrays.stream(Category.values())
                .map(productRepository::findFirstByCategoryOrderByPriceAsc).filter(Optional::isPresent)
                .map(Optional::get).map(Product::toDto).collect(Collectors.toList());
    }

    /**
     * 상품 가격 총액 계산.
     *
     * @param productDtoList ProductDto List
     * @return Long total amount
     */
    public Long getTotal(List<ProductDto> productDtoList) {
        long total = 0;
        for (ProductDto productDto : productDtoList) {
            total += productDto.getPrice();
        }
        return total;
    }

    /**
     * 단일 브랜드 최저 총액 기준 모든 카테고리 가격 조회.
     *
     * @return BrandProductDto
     */
    public BrandProductDto getBrandCategoryLowestPrice() {
        Brand brand = brandRepository.findBrandByLowestPrice().orElseThrow(() -> new IllegalArgumentException("Lowest price brand not found"));
        BrandProductDto dto = new BrandProductDto();

        long total = 0;
        List<BrandProductDto.LowesPriceInfo.CategoryPriceInfo> categoryPriceInfos = new ArrayList<>();
        BrandProductDto.LowesPriceInfo lowesPriceInfo = new BrandProductDto.LowesPriceInfo();
        lowesPriceInfo.setBrandName(brand.getName());
        for (Product product : productRepository.findLowestPriceProductByBrand(brand)) {
            categoryPriceInfos.add(new BrandProductDto.LowesPriceInfo.CategoryPriceInfo(product.getCategory().getLabel(),
                    NumberFormat.getInstance().format(product.getPrice())));
            total += product.getPrice();
        }
        lowesPriceInfo.setCategoryPriceInfos(categoryPriceInfos);
        lowesPriceInfo.setTotal(NumberFormat.getInstance().format(total));
        dto.setLowesPriceInfo(lowesPriceInfo);
        return dto;
    }

    /**
     * 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회.
     *
     * @param categoryName 카테고리명
     * @return CategoryLowestAndHighestPriceDto
     */
    public CategoryLowestAndHighestPriceDto getCategoryLowestAndHighestPrice(String categoryName) {
        Category category = Category.getByLabel(categoryName).orElseThrow(() -> new IllegalArgumentException("Invalid category name"));
        CategoryLowestAndHighestPriceDto dto = new CategoryLowestAndHighestPriceDto();
        dto.setCategoryName(categoryName);
        productRepository.findLowestPriceByCategory(category).ifPresent(
                product -> dto.setLowestPriceInfo(new CategoryLowestAndHighestPriceDto.PriceInfo(
                        product.getBrand().getName(), NumberFormat.getInstance().format(product.getPrice())))
        );
        productRepository.findHighestPriceByCategory(category).ifPresent(
                product -> dto.setHighestPriceInfo(new CategoryLowestAndHighestPriceDto.PriceInfo(
                        product.getBrand().getName(), NumberFormat.getInstance().format(product.getPrice())))
        );
        return dto;
    }

    /**
     * 상품정보 생성.
     *
     * @param productDto 상품정보
     * @return 생성된 상품정보 ProductDto
     */
    public ProductDto create(ProductDto productDto) {
        Brand brand = brandRepository.findOneByName(productDto.getBrandName()).orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        return productRepository.save(productDto.toEntity(brand)).toDto();
    }

    /**
     * 상품정보 업데이트
     *
     * @param productId  상품 아이디
     * @param productDto 상품 정보
     * @return 업데이트 된 상품정보 ProductDto
     */
    public ProductDto update(Long productId, ProductDto productDto) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found")).update(productDto).toDto();
    }

    /**
     * 상품정보 삭제.
     *
     * @param productId 상품 아이디
     */
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        productRepository.delete(product);
    }

}
