package com.epam.cloud.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.epam.cloud.facade.CategoryAssignmentFacade;

@WebMvcTest(CategoryAssignmentController.class)
public class CategoryAssignmentControllerTest extends BaseControllerTest {

    @MockBean
    private CategoryAssignmentFacade categoryAssignmentFacade;

    @Test
    public void shouldCallAssignCategoryApi() throws Exception {
        mockMvc.perform(post("/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCategoryAssignmentDto(generateProductDto(), generateCategoryDto()))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldCallUnAssignCategoryApi() throws Exception {
        mockMvc.perform(delete("/assignment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getCategoryAssignmentDto(generateProductDto(), generateCategoryDto()))))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
