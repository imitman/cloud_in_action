package com.epam.cloud.controller;

import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_CATEGORY_SORT;
import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_PAGE_SIZE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cloud.annotation.SwaggerPageable;
import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.facade.CategoryFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "CategoryController")
@RestController
public class CategoryController {
    @Autowired
    private CategoryFacade categoryFacade;

    @ApiOperation(value = "Remove a category")
    @DeleteMapping("/category/{categoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCategory(
            @ApiParam(
                    value = "Identifier of a category",
                    example = "4",
                    required = true)
            @PathVariable int categoryId) {
        categoryFacade.removeCategory(categoryId);
    }

    @ApiOperation(value = "Create a category")
    @PostMapping(value = "/category", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto createCategory(@ApiParam(value = "Category data")
                                      @RequestBody CategoryDto categoryDto) {
        return categoryFacade.createCategory(categoryDto);
    }

    @ApiOperation(value = "Get a category")
    @GetMapping(value = "/category/{categoryId}", produces = APPLICATION_JSON_VALUE)
    public CategoryDto getCategory(@ApiParam(value = "Identifier of a category", example = "2", required = true)
                                   @PathVariable int categoryId) {
        return categoryFacade.getCategory(categoryId);
    }

    @ApiOperation(value = "Update a category")
    @PutMapping(value = "/category", produces = APPLICATION_JSON_VALUE)
    public CategoryDto updateCategory(@ApiParam(value = "Category data")
                                      @RequestBody CategoryDto categoryDto) {
        return categoryFacade.updateCategory(categoryDto);
    }

    @ApiOperation(value = "Get categories")
    @SwaggerPageable
    @GetMapping(value = "/categories", produces = APPLICATION_JSON_VALUE)
    public List<CategoryDto> getCategories(@ApiParam(value = "Pageable data")
                                           @PageableDefault(sort = {DEFAULT_CATEGORY_SORT}, size = DEFAULT_PAGE_SIZE)
                                           @ApiIgnore Pageable pageable) {
        CategorySearchConfig categorySearchConfig = new CategorySearchConfig();
        categorySearchConfig.setPageable(pageable);
        return categoryFacade.getCategories(categorySearchConfig);
    }
}
