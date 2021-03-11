package com.epam.cloud.dao;

import java.util.Objects;

import org.springframework.data.domain.Pageable;

public class CategorySearchConfig {

    private Pageable pageable;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorySearchConfig that = (CategorySearchConfig) o;
        return Objects.equals(pageable, that.pageable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable);
    }
}
