package com.epam.cloud.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.converter.ConvertHelper;
import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.entity.CategoryEntity;
import com.epam.cloud.facade.CategoryFacade;
import com.epam.cloud.service.CategoryService;

@Component
public class DefaultCategoryFacade implements CategoryFacade {

    private final CategoryService categoryService;
    private final Converter<CategoryDto, CategoryEntity> dtoToEntityCategoryConverter;
    private final Converter<CategoryEntity, CategoryDto> entityToDtoCategoryConverter;

    @Autowired
    public DefaultCategoryFacade(CategoryService categoryService,
                                 Converter<CategoryDto, CategoryEntity> dtoToEntityCategoryConverter,
                                 Converter<CategoryEntity, CategoryDto> entityToDtoCategoryConverter) {
        this.categoryService = categoryService;
        this.dtoToEntityCategoryConverter = dtoToEntityCategoryConverter;
        this.entityToDtoCategoryConverter = entityToDtoCategoryConverter;
    }

    @Override
    public void removeCategory(int categoryId) {
        categoryService.removeCategory(categoryId);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryService.createCategory(dtoToEntityCategoryConverter.convert(categoryDto));
        return entityToDtoCategoryConverter.convert(categoryEntity);
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        return entityToDtoCategoryConverter.convert(categoryService.getCategory(categoryId));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryService.createCategory(dtoToEntityCategoryConverter.convert(categoryDto));
        return entityToDtoCategoryConverter.convert(categoryEntity);
    }

    @Override
    public List<CategoryDto> getCategories(CategorySearchConfig categorySearchConfig) {
        List<CategoryEntity> categories = categoryService.getCategories(categorySearchConfig);
        return ConvertHelper.convertAll(categories, entityToDtoCategoryConverter);
    }
}
