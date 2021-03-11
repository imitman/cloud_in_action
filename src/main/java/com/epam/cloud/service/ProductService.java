package com.epam.cloud.service;

import java.util.List;

import com.epam.cloud.dto.ProductSearchConfig;
import com.epam.cloud.entity.ProductEntity;

public interface ProductService {

    void removeProduct(int productId);

    ProductEntity createProduct(ProductEntity productEntity);

    ProductEntity getProduct(int productId);

    ProductEntity updateProduct(ProductEntity productEntity);

    List<ProductEntity> getProducts(ProductSearchConfig productSearchConfig);

}
