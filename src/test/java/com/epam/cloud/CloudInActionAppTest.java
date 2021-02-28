package com.epam.cloud;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cloud.controller.CloudInActionController;

@WebMvcTest(CloudInActionController.class)
class CloudInActionAppTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultGreeting() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to Cloud in Action, Guest")));
    }

    @Test
    public void shouldReturnGreetingWithName() throws Exception {
        mockMvc.perform(get("/welcome").param("name", "Max"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome to Cloud in Action, Maxxxxx")));
    }
}