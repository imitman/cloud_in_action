package com.epam.cloud.converter;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.entity.CategoryEntity;
import com.epam.cloud.entity.ProductEntity;

@Component
public class ProductDtoToProductEntityConverter implements Converter<ProductDto, ProductEntity> {

    private final Converter<CategoryDto, CategoryEntity> categoryDtoToCategoryEntityConverter;

    @Autowired
    public ProductDtoToProductEntityConverter(Converter<CategoryDto, CategoryEntity> categoryDtoToCategoryEntityConverter) {
        this.categoryDtoToCategoryEntityConverter = categoryDtoToCategoryEntityConverter;
    }

    @Override
    public ProductEntity convert(ProductDto source) {
        ProductEntity target = new ProductEntity();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setCategories(new HashSet<>(ConvertHelper.convertAll(source.getCategories(), categoryDtoToCategoryEntityConverter)));
        return target;
    }
}

