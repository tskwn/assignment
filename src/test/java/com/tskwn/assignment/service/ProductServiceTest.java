package com.tskwn.assignment.service;


import com.tskwn.assignment.dto.ProductDto;
import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.entity.Category;
import com.tskwn.assignment.entity.Product;
import com.tskwn.assignment.repository.BrandRepository;
import com.tskwn.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ResourceLoader resourceLoader;

    private ProductService productService;

    @BeforeEach
    void init() {
        productService = new ProductService(brandRepository, productRepository, resourceLoader);
    }

    @Test
    void test_getCategoryLowestPrice() {
        // given
        var product1 = mock(Product.class);
        var product2 = mock(Product.class);
        var product3 = mock(Product.class);
        var product4 = mock(Product.class);
        var product5 = mock(Product.class);

        // when
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.TOP)).thenReturn(Optional.of(product1));
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.OUTER)).thenReturn(Optional.of(product2));
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.PANTS)).thenReturn(Optional.of(product3));
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.SNEAKERS)).thenReturn(Optional.of(product4));
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.BAG)).thenReturn(Optional.of(product5));
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.HAT)).thenReturn(Optional.empty());
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.SOCKS)).thenReturn(Optional.empty());
        when(productRepository.findFirstByCategoryOrderByPriceAsc(Category.ACCESSORY)).thenReturn(Optional.empty());
        var result = productService.getCategoryLowestPrice();

        // then
        assertEquals(result.size(), 5);
    }

    @Test
    void test_getBrandCategoryLowestPrice_not_exist_brand() {
        // when
        when(brandRepository.findBrandByLowestPrice()).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> productService.getBrandCategoryLowestPrice());
    }

    @Test
    void test_getBrandCategoryLowestPrice() {
        // given
        var brand = mock(Brand.class);
        var product1 = Product.builder().category(Category.OUTER).price(10000L).build();
        var product2 = Product.builder().category(Category.PANTS).price(20000L).build();
        var product3 = Product.builder().category(Category.BAG).price(30000L).build();
        var expectedTotal = "60,000";
        var products = List.of(product1, product2, product3);

        // when
        when(brandRepository.findBrandByLowestPrice()).thenReturn(Optional.of(brand));
        when(productRepository.findLowestPriceProductByBrand(brand)).thenReturn(products);
        var result = productService.getBrandCategoryLowestPrice();

        // then
        assertEquals(result.getLowesPriceInfo().getCategoryPriceInfos().size(), 3);
        assertEquals(result.getLowesPriceInfo().getTotal(), expectedTotal);
    }

    @Test
    void test_getCategoryLowestAndHighestPrice_invalid_category() {
        // given
        var categoryName = "INVALID_CATEGORY_NAME";

        // then
        assertThrows(IllegalArgumentException.class, () -> productService.getCategoryLowestAndHighestPrice(categoryName));
    }

    @Test
    void test_getCategoryLowestAndHighestPrice() {
        // given
        var categoryName = "상의";
        var category = Category.TOP;
        var lowestPrice = 25000L;
        var highestPrice = 55000L;
        var expectedLowestPrice = "25,000";
        var expectedHighestPrice = "55,000";
        var brandA = Brand.builder().name("A").build();
        var brandB = Brand.builder().name("B").build();
        var lowestPriceProduct = Product.builder().price(lowestPrice).category(category).brand(brandA).build();
        var highestPriceProduct = Product.builder().price(highestPrice).category(category).brand(brandB).build();

        // when
        when(productRepository.findLowestPriceByCategory(category)).thenReturn(Optional.of(lowestPriceProduct));
        when(productRepository.findHighestPriceByCategory(category)).thenReturn(Optional.of(highestPriceProduct));
        var result = productService.getCategoryLowestAndHighestPrice(categoryName);

        // then
        assertEquals(result.getCategoryName(), categoryName);
        assertEquals(result.getLowestPriceInfo().getPrice(), expectedLowestPrice);
        assertEquals(result.getHighestPriceInfo().getPrice(), expectedHighestPrice);
    }


    @Test
    void test_getTotal() {
        // given
        var product1 = ProductDto.builder().price(10000L).build();
        var product2 = ProductDto.builder().price(20000L).build();
        var product3 = ProductDto.builder().price(30000L).build();
        var expectedTotal = 60000L;
        var productDtos = List.of(product1, product2, product3);

        // when
        var result = productService.getTotal(productDtos);

        // then
        assertEquals(result, expectedTotal);

    }

    @Test
    void test_create_brand_not_exists() {
        // given
        var productDto = mock(ProductDto.class);

        // when
        when(brandRepository.findOneByName(productDto.getBrandName())).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> productService.create(productDto));
    }



    @Test
    void test_create() {
        // given
        var productDto = mock(ProductDto.class);
        var createdDto = mock(ProductDto.class);
        var product = mock(Product.class);
        var brand = mock(Brand.class);

        // when
        when(brandRepository.findOneByName(productDto.getBrandName())).thenReturn(Optional.of(brand));
        when(productRepository.save(productDto.toEntity(brand))).thenReturn(product);
        when(product.toDto()).thenReturn(createdDto);
        var result = productService.create(productDto);

        // then
        assertEquals(result, createdDto);
    }

    @Test
    void test_update_not_exist() {
        // given
        var productDto = mock(ProductDto.class);
        var productId = 1L;

        // when
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> productService.update(productId, productDto));
    }

    @Test
    void test_update() {
        // given
        var productDto = mock(ProductDto.class);
        var productId = 1L;
        var product = mock(Product.class);
        var updatedProduct = mock(Product.class);
        var updatedDto = mock(ProductDto.class);

        // when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(product.update(productDto)).thenReturn(updatedProduct);
        when(updatedProduct.toDto()).thenReturn(updatedDto);
        var result = productService.update(productId, productDto);

        // then
        assertEquals(result, updatedDto);
    }

    @Test
    void test_delete_not_exist() {
        // given
        var productId = 1L;

        // when
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> productService.delete(productId));
    }

    @Test
    void test_delete() {
        // given
        var product = mock(Product.class);
        var productId = 1L;

        // when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        productService.delete(productId);

        // then
        verify(productRepository, times(1)).delete(product);
    }

}
