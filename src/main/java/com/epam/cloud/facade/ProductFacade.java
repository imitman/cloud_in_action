package com.epam.cloud.facade;

import java.util.List;

import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.dto.ProductSearchConfig;

public interface ProductFacade {

    void removeProduct(int productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProduct(int productId);

    ProductDto updateProduct(ProductDto productDto);

    List<ProductDto> getProducts(ProductSearchConfig productSearchConfig);

}
