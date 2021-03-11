package com.epam.cloud.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cloud.dao.CategoryDao;
import com.epam.cloud.dao.ProductDao;
import com.epam.cloud.entity.CategoryEntity;
import com.epam.cloud.entity.ProductEntity;
import com.epam.cloud.service.CategoryAssignmentService;

@Service
@Transactional
public class DefaultCategoryAssignmentService implements CategoryAssignmentService {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    @Autowired
    public DefaultCategoryAssignmentService(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public void assignProductToCategory(int productId, int categoryId) {
        ProductEntity productEntity = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with such id does not exist"));
        CategoryEntity categoryEntity = categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with such id does not exist"));
        Set<CategoryEntity> categories = Optional.ofNullable(productEntity.getCategories()).orElse(new HashSet<>());
        if (categories.add(categoryEntity)) {
            productDao.save(productEntity);
        } else {
            throw new IllegalArgumentException("Category already contains the product");
        }

    }

    @Override
    public void unAssignProductFromCategory(int productId, int categoryId) {
        ProductEntity productEntity = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with such id does not exist"));
        CategoryEntity categoryEntity = categoryDao.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with such id does not exist"));
        Set<CategoryEntity> categories = Optional.ofNullable(productEntity.getCategories())
                .orElseThrow(() -> new IllegalArgumentException("Category does not contain the product"));
        if (categories.remove(categoryEntity)) {
            productDao.save(productEntity);
        } else {
            throw new IllegalArgumentException("Category does not contain the product");
        }
    }
}
