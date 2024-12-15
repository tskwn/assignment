package com.tskwn.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BrandProductDto {
    @JsonProperty("최저가")
    private LowesPriceInfo lowesPriceInfo;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LowesPriceInfo {
        @JsonProperty("브랜드")
        private String brandName;
        @JsonProperty("카테고리")
        private List<CategoryPriceInfo> categoryPriceInfos;
        @JsonProperty("총액")
        private String total;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class CategoryPriceInfo {
            @JsonProperty("카테고리")
            private String category;
            @JsonProperty("가격")
            private String price;
        }
    }
}
