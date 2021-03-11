package com.epam.cloud.facade;

import java.util.List;

import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.dto.CategoryDto;

public interface CategoryFacade {

    void removeCategory(int categoryId);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategory(int categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    List<CategoryDto> getCategories(CategorySearchConfig categorySearchConfig);
}
