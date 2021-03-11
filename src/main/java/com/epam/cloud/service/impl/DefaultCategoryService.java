package com.epam.cloud.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cloud.dao.CategoryDao;
import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.entity.CategoryEntity;
import com.epam.cloud.service.CategoryService;

@Service
@Transactional(readOnly = true)
public class DefaultCategoryService implements CategoryService {

    private final CategoryDao categoryDao;

    @Autowired
    public DefaultCategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    @Transactional
    public void removeCategory(int categoryId) {
        categoryDao.deleteById(categoryId);
    }

    @Override
    @Transactional
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return categoryDao.save(categoryEntity);
    }

    @Override
    public CategoryEntity getCategory(int categoryId) {
        return categoryDao.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("There is no any category for given id"));
    }

    @Override
    @Transactional
    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        if (categoryDao.existsById(categoryEntity.getId())) {
            return categoryDao.save(categoryEntity);
        } else {
            throw new IllegalArgumentException("There is no any category for given id");
        }
    }

    @Override
    public List<CategoryEntity> getCategories(CategorySearchConfig categorySearchConfig) {
        return categoryDao.findAll(categorySearchConfig.getPageable()).getContent();
    }
}
