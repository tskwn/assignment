package com.tskwn.assignment.entity;

import com.tskwn.assignment.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Brand brand;
    @Enumerated(EnumType.STRING)
    private Category category;
    private Long price;

    public ProductDto toDto() {
        return ProductDto.builder()
                .id(getId())
                .brandName(getBrand().getName())
                .categoryName(getCategory().getLabel())
                .price(getPrice())
                .build();
    }

    public Product update(ProductDto productDto) {
        setCategory(Category.valueOf(productDto.getCategoryName()));
        setPrice(productDto.getPrice());
        return this;
    }
}
