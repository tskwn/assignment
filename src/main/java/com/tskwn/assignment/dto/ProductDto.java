package com.tskwn.assignment.dto;

import com.tskwn.assignment.entity.Brand;
import com.tskwn.assignment.entity.Category;
import com.tskwn.assignment.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto extends ResponseDto {
    private Long id;
    private String brandName;
    private String categoryName;
    private Long price;

    public Product toEntity(Brand brand) {
        return Product.builder().brand(brand).category(Category.valueOf(getCategoryName())).price(getPrice()).build();
    }
}
