package com.epam.cloud.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.entity.CategoryEntity;

@Component
public class CategoryDtoToCategoryEntityConverter implements Converter<CategoryDto, CategoryEntity> {

    @Override
    public CategoryEntity convert(CategoryDto source) {
        CategoryEntity target = new CategoryEntity();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}

