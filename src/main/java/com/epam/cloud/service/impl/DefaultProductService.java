package com.epam.cloud.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cloud.dao.ProductDao;
import com.epam.cloud.dto.ProductSearchConfig;
import com.epam.cloud.entity.ProductEntity;
import com.epam.cloud.service.ProductService;

@Service
@Transactional(readOnly = true)
public class DefaultProductService implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional
    public void removeProduct(int productId) {
        productDao.deleteById(productId);
    }

    @Override
    @Transactional
    public ProductEntity createProduct(ProductEntity productEntity) {
        return productDao.save(productEntity);
    }

    @Override
    public ProductEntity getProduct(int productId) {
        return productDao.findById(productId).orElseThrow(() -> new EntityNotFoundException("There is no any product for given id"));
    }

    @Override
    @Transactional
    public ProductEntity updateProduct(ProductEntity productEntity) {
        if (productDao.existsById(productEntity.getId())) {
            return productDao.save(productEntity);
        } else {
            throw new IllegalArgumentException("There is no any product for given id");
        }
    }

    @Override
    public List<ProductEntity> getProducts(ProductSearchConfig productSearchConfig) {
        Pageable pageable = productSearchConfig.getPageable();
        String text = productSearchConfig.getText();
        int categoryId = productSearchConfig.getCategoryId();
        int minPrice = productSearchConfig.getMinPrice();
        int maxPrice = productSearchConfig.getMaxPrice();

        Page<ProductEntity> productsPage = categoryId == 0
                ? productDao.findAllByNameContainingAndPriceBetween(text, minPrice, maxPrice, pageable)
                : productDao.findAllByNameAndPriceForGivenCategory(text, categoryId, minPrice, maxPrice, pageable);
        return productsPage.getContent();
    }
}
