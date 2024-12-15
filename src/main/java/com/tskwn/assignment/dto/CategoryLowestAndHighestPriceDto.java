package com.tskwn.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CategoryLowestAndHighestPriceDto {
    @JsonProperty("카테고리")
    private String categoryName;
    @JsonProperty("최저가")
    private PriceInfo lowestPriceInfo;
    @JsonProperty("최고가")
    private PriceInfo highestPriceInfo;

    @Getter
    @AllArgsConstructor
    public static class PriceInfo {
        @JsonProperty("브랜드")
        private String brandName;
        @JsonProperty("가격")
        private String price;
    }
}
