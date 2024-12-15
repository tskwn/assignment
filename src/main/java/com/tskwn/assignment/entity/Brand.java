package com.tskwn.assignment.entity;

import com.tskwn.assignment.dto.BrandDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public BrandDto toDto() {
        return BrandDto.builder()
                .id(getId())
                .name(getName())
                .build();
    }

    public Brand update(BrandDto brandDto) {
        setName(brandDto.getName());
        return this;
    }
}
