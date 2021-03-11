package com.epam.cloud.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.cloud.dto.CategoryAssignmentDto;
import com.epam.cloud.facade.CategoryAssignmentFacade;
import com.epam.cloud.service.CategoryAssignmentService;

@Component
public class DefaultCategoryAssignmentFacade implements CategoryAssignmentFacade {

    private final CategoryAssignmentService categoryAssignmentService;

    @Autowired
    public DefaultCategoryAssignmentFacade(CategoryAssignmentService categoryAssignmentService) {
        this.categoryAssignmentService = categoryAssignmentService;
    }

    @Override
    public void createCategoryAssignment(CategoryAssignmentDto categoryAssignmentDto) {
        int productId = categoryAssignmentDto.getProductId();
        int categoryId = categoryAssignmentDto.getCategoryId();
        categoryAssignmentService.assignProductToCategory(productId, categoryId);
    }

    @Override
    public void removeCategoryAssignment(CategoryAssignmentDto categoryAssignmentDto) {
        int categoryId = categoryAssignmentDto.getCategoryId();
        int productId = categoryAssignmentDto.getProductId();
        categoryAssignmentService.unAssignProductFromCategory(productId, categoryId);
    }
}
