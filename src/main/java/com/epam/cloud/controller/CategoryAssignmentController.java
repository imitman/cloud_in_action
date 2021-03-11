package com.epam.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.epam.cloud.dto.CategoryAssignmentDto;
import com.epam.cloud.facade.CategoryAssignmentFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "CategoryAssignmentController")
@Controller
public class CategoryAssignmentController {

    @Autowired
    private CategoryAssignmentFacade categoryAssignmentFacade;

    @ApiOperation(value = "Create a category assignment for a product")
    @PostMapping("/assignment")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createAssignment(@ApiParam(value = "Category assignment data")
                                 @RequestBody CategoryAssignmentDto categoryAssignmentDto) {
        categoryAssignmentFacade.createCategoryAssignment(categoryAssignmentDto);
    }

    @ApiOperation(value = "Remove a category assignment for a product")
    @DeleteMapping("/assignment")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeAssignment(@ApiParam(value = "Category assignment data")
                                 @RequestBody CategoryAssignmentDto categoryAssignmentDto) {
        int productId = categoryAssignmentDto.getProductId();
        int categoryId = categoryAssignmentDto.getCategoryId();
        categoryAssignmentFacade.removeCategoryAssignment(categoryAssignmentDto);
    }
}
