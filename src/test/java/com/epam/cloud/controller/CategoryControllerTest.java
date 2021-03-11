package com.epam.cloud.controller;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.epam.cloud.dao.CategorySearchConfig;
import com.epam.cloud.dto.CategoryDto;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest extends BaseControllerTest {

    @Test
    public void shouldCreateCategory() throws Exception {
        CategoryDto expectedCategory = generateCategoryDto();
        when(categoryFacade.createCategory(expectedCategory)).thenReturn(expectedCategory);
        String expectedJson = objectMapper.writeValueAsString(expectedCategory);
        mockMvc.perform(post("/category")
                .contentType(APPLICATION_JSON)
                .content(expectedJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetCategory() throws Exception {
        CategoryDto expectedCategory = generateCategoryDto();
        int id = expectedCategory.getId();
        when(categoryFacade.getCategory(id)).thenReturn(expectedCategory);
        mockMvc.perform(get("/category/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCategory)));
    }

    @Test
    public void shouldUpdateCategory() throws Exception {
        CategoryDto expectedCategory = generateCategoryDto();
        when(categoryFacade.updateCategory(expectedCategory)).thenReturn(expectedCategory);
        String expectedJson = objectMapper.writeValueAsString(expectedCategory);
        mockMvc.perform(put("/category")
                .contentType(APPLICATION_JSON)
                .content(expectedJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/category/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetCategoriesWithDefaultPageable() throws Exception {
        List<CategoryDto> expectedCategories = generateCategories();
        CategorySearchConfig searchConfig
                = getCategorySearchConfig(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.by(DEFAULT_CATEGORY_SORT)));
        when(categoryFacade.getCategories(searchConfig)).thenReturn(expectedCategories);

        String expectedJson = objectMapper.writeValueAsString(expectedCategories);
        mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetCategoriesWithPageableConfigured() throws Exception {
        List<CategoryDto> expectedCategories = generateCategories();
        CategorySearchConfig searchConfig
                = getCategorySearchConfig(PageRequest.of(1, 3, Sort.by("name").descending()));
        when(categoryFacade.getCategories(searchConfig)).thenReturn(expectedCategories);

        String expectedJson = objectMapper.writeValueAsString(expectedCategories);
        mockMvc.perform(get("/categories")
                .queryParam("page", "1")
                .queryParam("size", "3")
                .queryParam("sort", "name,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    private CategorySearchConfig getCategorySearchConfig(Pageable pageable) {
        CategorySearchConfig categorySearchConfig = new CategorySearchConfig();
        categorySearchConfig.setPageable(pageable);
        return categorySearchConfig;
    }
}
