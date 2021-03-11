package com.epam.cloud.service;

import java.util.List;

import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.entity.CategoryEntity;

public interface CategoryService {

    void removeCategory(int categoryId);

    CategoryEntity createCategory(CategoryEntity categoryEntity);

    CategoryEntity getCategory(int categoryId);

    CategoryEntity updateCategory(CategoryEntity categoryEntity);

    List<CategoryEntity> getCategories(CategorySearchConfig categorySearchConfig);

}
