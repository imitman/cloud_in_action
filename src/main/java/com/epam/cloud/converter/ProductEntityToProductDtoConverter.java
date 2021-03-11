package com.epam.cloud.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.entity.ProductEntity;

@Component
public class ProductEntityToProductDtoConverter implements Converter<ProductEntity, ProductDto> {

    private final CategoryEntityToCategoryDtoConverter categoryEntityToCategoryDtoConverter;

    @Autowired
    public ProductEntityToProductDtoConverter(CategoryEntityToCategoryDtoConverter categoryEntityToCategoryDtoConverter) {
        this.categoryEntityToCategoryDtoConverter = categoryEntityToCategoryDtoConverter;
    }

    @Override
    public ProductDto convert(ProductEntity source) {
        ProductDto target = new ProductDto();
        target.setId(source.getId());
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setCategories(ConvertHelper.convertAll(source.getCategories(), categoryEntityToCategoryDtoConverter));
        return target;
    }
}
