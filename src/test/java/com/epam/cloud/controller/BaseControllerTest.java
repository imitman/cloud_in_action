package com.epam.cloud.controller;

import static java.util.Collections.singletonList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cloud.dto.CategoryAssignmentDto;
import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.facade.CategoryFacade;
import com.epam.cloud.facade.ProductFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {

    protected static final int DEFAULT_MIN_PRICE = 0;
    protected static final int DEFAULT_MAX_PRICE = 999999999;
    protected static final int DEFAULT_PAGE = 0;
    protected static final int DEFAULT_SIZE = 5;
    protected static final String DEFAULT_TEXT = "";
    protected static final String DEFAULT_PRODUCT_SORT = "name";
    protected static final String DEFAULT_CATEGORY_SORT = "productCount";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected ProductFacade productFacade;
    @MockBean
    protected CategoryFacade categoryFacade;

    protected ProductDto generateProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(ThreadLocalRandom.current().nextInt(DEFAULT_MAX_PRICE));
        productDto.setName(ThreadLocalRandom.current().nextInt(DEFAULT_MAX_PRICE) + "");
        productDto.setPrice(ThreadLocalRandom.current().nextInt(DEFAULT_MAX_PRICE));
        return productDto;
    }

    protected CategoryDto generateCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(ThreadLocalRandom.current().nextInt(DEFAULT_MAX_PRICE));
        categoryDto.setName(ThreadLocalRandom.current().nextInt(DEFAULT_MAX_PRICE) + "");
        return categoryDto;
    }

    protected CategoryAssignmentDto getCategoryAssignmentDto(ProductDto productDto, CategoryDto categoryDto) {
        CategoryAssignmentDto categoryAssignmentDto = new CategoryAssignmentDto();
        categoryAssignmentDto.setProductId(productDto.getId());
        categoryAssignmentDto.setCategoryId(categoryDto.getId());
        return categoryAssignmentDto;
    }

    protected List<ProductDto> generateProducts() {
        ProductDto productDto1 = generateProductDto();
        ProductDto productDto2 = generateProductDto();
        ProductDto productDto3 = generateProductDto();
        CategoryDto categoryDto1 = generateCategoryDto();
        CategoryDto categoryDto2 = generateCategoryDto();
        productDto1.setCategories(singletonList(categoryDto1));
        productDto2.setCategories(singletonList(categoryDto1));
        productDto3.setCategories(singletonList(categoryDto2));
        return Arrays.asList(productDto1, productDto2, productDto3);
    }

    protected List<CategoryDto> generateCategories() {
        CategoryDto categoryDto1 = generateCategoryDto();
        CategoryDto categoryDto2 = generateCategoryDto();
        CategoryDto categoryDto3 = generateCategoryDto();
        return Arrays.asList(categoryDto1, categoryDto2, categoryDto3);
    }
}
