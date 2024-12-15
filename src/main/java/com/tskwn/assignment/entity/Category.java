package com.tskwn.assignment.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Category {
    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("악세서리");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public static Optional<Category> getByLabel(String label) {
        return Arrays.stream(values()).filter(category -> category.getLabel().equals(label)).findFirst();
    }
}
