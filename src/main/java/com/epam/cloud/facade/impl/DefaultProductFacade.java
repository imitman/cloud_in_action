package com.epam.cloud.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.epam.cloud.converter.ConvertHelper;
import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.dto.ProductSearchConfig;
import com.epam.cloud.entity.ProductEntity;
import com.epam.cloud.facade.ProductFacade;
import com.epam.cloud.service.ProductService;

@Component
public class DefaultProductFacade implements ProductFacade {

    private final ProductService productService;
    private final Converter<ProductDto, ProductEntity> dtoToEntityProductConverter;
    private final Converter<ProductEntity, ProductDto> entityToDtoProductConverter;

    @Autowired
    public DefaultProductFacade(ProductService productService,
                                Converter<ProductDto, ProductEntity> dtoToEntityProductConverter,
                                Converter<ProductEntity, ProductDto> entityToDtoProductConverter) {
        this.productService = productService;
        this.dtoToEntityProductConverter = dtoToEntityProductConverter;
        this.entityToDtoProductConverter = entityToDtoProductConverter;
    }

    @Override
    public void removeProduct(int productId) {
        productService.removeProduct(productId);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity productEntity = productService.createProduct(dtoToEntityProductConverter.convert(productDto));
        return entityToDtoProductConverter.convert(productEntity);
    }

    @Override
    public ProductDto getProduct(int productId) {
        return entityToDtoProductConverter.convert(productService.getProduct(productId));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        ProductEntity productEntity = productService.updateProduct(dtoToEntityProductConverter.convert(productDto));
        return entityToDtoProductConverter.convert(productEntity);
    }

    @Override
    public List<ProductDto> getProducts(ProductSearchConfig productSearchConfig) {
        List<ProductEntity> products = productService.getProducts(productSearchConfig);
        return ConvertHelper.convertAll(products, entityToDtoProductConverter);
    }
}
