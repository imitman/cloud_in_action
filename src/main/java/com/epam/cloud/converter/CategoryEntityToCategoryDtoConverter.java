package com.epam.cloud.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.entity.CategoryEntity;

@Component
public class CategoryEntityToCategoryDtoConverter implements Converter<CategoryEntity, CategoryDto> {

    @Override
    public CategoryDto convert(CategoryEntity source) {
        CategoryDto target = new CategoryDto();
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }
}
