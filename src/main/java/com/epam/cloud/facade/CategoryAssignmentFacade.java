package com.epam.cloud.facade;

import com.epam.cloud.dto.CategoryAssignmentDto;

public interface CategoryAssignmentFacade {

    void createCategoryAssignment(CategoryAssignmentDto categoryAssignmentDto);

    void removeCategoryAssignment(CategoryAssignmentDto categoryAssignmentDto);
}
