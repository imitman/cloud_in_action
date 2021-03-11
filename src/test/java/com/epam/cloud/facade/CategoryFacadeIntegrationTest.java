package com.epam.cloud.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.dto.CategoryDto;

public class CategoryFacadeIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateCategory() {
        CategoryDto expectedCategory = generateCategoryDto();

        CategoryDto actual = categoryFacade.createCategory(expectedCategory);

        assertThat(actual.getId()).isEqualTo(expectedCategory.getId());
        assertThat(actual.getName()).isEqualTo(expectedCategory.getName());
    }

    @Test
    public void shouldGetCategory() {
        CategoryDto expectedCategory = generateAndStoreCategory();

        CategoryDto actual = categoryFacade.getCategory(expectedCategory.getId());

        assertThat(actual.getId()).isEqualTo(expectedCategory.getId());
        assertThat(actual.getName()).isEqualTo(expectedCategory.getName());
    }

    @Test
    public void shouldUpdateCategory() {
        CategoryDto categoryDto = generateAndStoreCategory();
        int generatedId = categoryDto.getId();
        CategoryDto updatedCategory = generateCategoryDto();
        updatedCategory.setId(generatedId);

        CategoryDto actual = categoryFacade.updateCategory(updatedCategory);

        assertThat(actual.getId()).isEqualTo(generatedId);
        assertThat(actual.getName()).isEqualTo(updatedCategory.getName());
    }

    @Test
    public void shouldDeleteCategory() {
        CategoryDto categoryDto = generateAndStoreCategory();

        categoryFacade.removeCategory(categoryDto.getId());

        assertThrows(EntityNotFoundException.class, () -> categoryFacade.getCategory(categoryDto.getId()));
    }

    @Test
    public void shouldGetCategoriesSortByNameAsc() {
        List<CategoryDto> generatedCategories = generateAndStoreCategories();
        CategoryDto[] expectedCategories = generatedCategories.stream()
                .sorted(Comparator.comparing(CategoryDto::getName))
                .toArray(CategoryDto[]::new);
        CategorySearchConfig categorySearchConfig
                = getCategorySearchConfig(PageRequest.of(0, BOUND, Sort.by("name")));

        CategoryDto[] actualCategories = categoryFacade.getCategories(categorySearchConfig).toArray(new CategoryDto[0]);

        assertThat(actualCategories).containsExactly(expectedCategories);
    }

    @Test
    public void shouldGetCategoriesSortByNameDesc() {
        List<CategoryDto> generatedCategories = generateAndStoreCategories();
        CategoryDto[] expectedCategories = generatedCategories.stream()
                .sorted(Comparator.comparing(CategoryDto::getName).reversed())
                .toArray(CategoryDto[]::new);
        CategorySearchConfig categorySearchConfig
                = getCategorySearchConfig(PageRequest.of(0, BOUND, Sort.by("name").descending()));

        CategoryDto[] actualCategories = categoryFacade.getCategories(categorySearchConfig).toArray(new CategoryDto[0]);

        assertThat(actualCategories).containsExactly(expectedCategories);
    }

    private CategorySearchConfig getCategorySearchConfig(Pageable pageable) {
        CategorySearchConfig categorySearchConfig = new CategorySearchConfig();
        categorySearchConfig.setPageable(pageable);
        return categorySearchConfig;
    }
}
