package com.tskwn.assignment.dto;

import com.tskwn.assignment.entity.Brand;
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
public class BrandDto extends ResponseDto {
    private Long id;
    private String name;

    public Brand toEntity() {
        return Brand.builder()
                .id(getId())
                .name(getName())
                .build();
    }
}
