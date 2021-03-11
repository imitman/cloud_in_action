package com.epam.cloud.dto;

import java.util.Objects;

import org.springframework.data.domain.Pageable;

public class ProductSearchConfig {

    private Pageable pageable;
    private String text;
    private int categoryId;
    private int minPrice;
    private int maxPrice;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSearchConfig that = (ProductSearchConfig) o;
        return categoryId == that.categoryId && minPrice == that.minPrice && maxPrice == that.maxPrice && Objects.equals(pageable, that.pageable) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, text, categoryId, minPrice, maxPrice);
    }

    public static class Builder {
        private final Pageable pageable;
        private String text;
        private int categoryId;
        private int minPrice;
        private int maxPrice;

        public Builder(Pageable pageable) {
            this.pageable = pageable;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder minPrice(int minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder maxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ProductSearchConfig build() {
            ProductSearchConfig productSearchConfig = new ProductSearchConfig();
            productSearchConfig.pageable = this.pageable;
            productSearchConfig.categoryId = this.categoryId;
            productSearchConfig.text = this.text;
            productSearchConfig.minPrice = this.minPrice;
            productSearchConfig.maxPrice = this.maxPrice;
            return productSearchConfig;
        }
    }
}
