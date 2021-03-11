package com.epam.cloud.facade;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;


import org.junit.jupiter.api.Test;

import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.dto.ProductDto;

public class CategoryAssignmentIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldAssignProductToCategory() {
        ProductDto productDto = generateProductDtoWithSingleCategory();
        productDto.setCategories(Collections.emptyList());
        CategoryDto categoryDto = generateCategoryDto();
        ProductDto createdProduct = productFacade.createProduct(productDto);
        CategoryDto createdCategory = categoryFacade.createCategory(categoryDto);

        categoryAssignmentFacade.createCategoryAssignment(getCategoryAssignmentDto(createdProduct, createdCategory));
        ProductDto productAfterCategoryAssigned = productFacade.getProduct(productDto.getId());

        assertThat(createdProduct.getCategories()).isEmpty();
        assertThat(productAfterCategoryAssigned.getCategories()).containsExactly(createdCategory);
    }

    @Test
    public void shouldOnAssignProductFromCategory() {
        ProductDto productDto = generateAndStoreProductWithSingleCategory();
        CategoryDto assignedCategory = productDto.getCategories().get(0);

        categoryAssignmentFacade.removeCategoryAssignment(getCategoryAssignmentDto(productDto, assignedCategory));
        ProductDto productAfterCategoryUnAssigned = productFacade.getProduct(productDto.getId());

        assertThat(assignedCategory).isNotNull();
        assertThat(productAfterCategoryUnAssigned.getCategories()).isEmpty();
    }
}