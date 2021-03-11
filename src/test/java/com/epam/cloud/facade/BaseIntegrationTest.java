package com.epam.cloud.facade;

import static java.util.Collections.singletonList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cloud.CloudInActionApp;
import com.epam.cloud.dto.CategoryAssignmentDto;
import com.epam.cloud.dto.CategoryDto;
import com.epam.cloud.dto.ProductDto;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = CloudInActionApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    protected static final int BOUND = 999999999;
    private static final Map<Integer, Integer> existingIds = new ConcurrentHashMap<>();

    @Autowired
    protected ProductFacade productFacade;
    @Autowired
    protected CategoryFacade categoryFacade;
    @Autowired
    protected CategoryAssignmentFacade categoryAssignmentFacade;

    protected ProductDto generateProductDtoWithSingleCategory() {
        ProductDto productDto = new ProductDto();
        productDto.setId(generateUniqueIdPerTestRun());
        productDto.setName(ThreadLocalRandom.current().nextInt(BOUND) + "");
        productDto.setPrice(ThreadLocalRandom.current().nextInt(BOUND));
        productDto.setCategories(singletonList(generateCategoryDto()));
        return productDto;
    }

    protected CategoryDto generateCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(generateUniqueIdPerTestRun());
        categoryDto.setName(ThreadLocalRandom.current().nextInt(BOUND) + "");
        return categoryDto;
    }

    protected CategoryAssignmentDto getCategoryAssignmentDto(ProductDto productDto, CategoryDto categoryDto) {
        CategoryAssignmentDto categoryAssignmentDto = new CategoryAssignmentDto();
        categoryAssignmentDto.setProductId(productDto.getId());
        categoryAssignmentDto.setCategoryId(categoryDto.getId());
        return categoryAssignmentDto;
    }

    protected ProductDto generateAndStoreProductWithSingleCategory() {
        return productFacade.createProduct(generateProductDtoWithSingleCategory());
    }

    protected CategoryDto generateAndStoreCategory() {
        return categoryFacade.createCategory(generateCategoryDto());
    }

    protected List<ProductDto> generateAndStoreProductsWithCategories() {
        ProductDto productDto1 = generateProductDtoWithSingleCategory();
        ProductDto productDto2 = generateProductDtoWithSingleCategory();
        ProductDto productDto3 = generateProductDtoWithSingleCategory();
        ProductDto productDto4 = generateProductDtoWithSingleCategory();
        ProductDto productDto5 = generateProductDtoWithSingleCategory();
        ProductDto productDto6 = generateProductDtoWithSingleCategory();
        CategoryDto categoryDto1 = generateCategoryDto();
        CategoryDto categoryDto2 = generateCategoryDto();
        CategoryDto categoryDto3 = generateCategoryDto();
        productDto1.setCategories(singletonList(categoryDto1));
        productDto2.setCategories(singletonList(categoryDto1));
        productDto3.setCategories(singletonList(categoryDto1));
        productDto4.setCategories(singletonList(categoryDto2));
        productDto5.setCategories(singletonList(categoryDto2));
        productDto6.setCategories(singletonList(categoryDto3));

        return Stream.of(productDto1, productDto2, productDto3, productDto4, productDto5, productDto6)
                .map(productFacade::createProduct)
                .collect(Collectors.toList());
    }

    protected List<CategoryDto> generateAndStoreCategories() {
        return Stream.of(generateCategoryDto(), generateCategoryDto(), generateCategoryDto(), generateCategoryDto())
                .map(categoryFacade::createCategory)
                .collect(Collectors.toList());
    }

    private int generateUniqueIdPerTestRun() {
        int randomInBound = ThreadLocalRandom.current().nextInt(BOUND);
        while (existingIds.put(randomInBound, randomInBound) != null) {
            randomInBound = ThreadLocalRandom.current().nextInt(BOUND);
        }
        return randomInBound;
    }
}
