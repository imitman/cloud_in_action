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

import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.dto.ProductSearchConfig;

@WebMvcTest(ProductController.class)
public class ProductControllerTest extends BaseControllerTest {

    @Test
    public void shouldCreateProduct() throws Exception {
        ProductDto expectedProduct = generateProductDto();
        when(productFacade.createProduct(expectedProduct)).thenReturn(expectedProduct);
        String expectedJson = objectMapper.writeValueAsString(expectedProduct);
        mockMvc.perform(post("/product")
                .contentType(APPLICATION_JSON)
                .content(expectedJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetProduct() throws Exception {
        ProductDto expectedProduct = generateProductDto();
        int id = expectedProduct.getId();
        when(productFacade.getProduct(id)).thenReturn(expectedProduct);
        mockMvc.perform(get("/product/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct)));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        ProductDto expectedProduct = generateProductDto();
        when(productFacade.updateProduct(expectedProduct)).thenReturn(expectedProduct);
        String expectedJson = objectMapper.writeValueAsString(expectedProduct);
        mockMvc.perform(put("/product")
                .contentType(APPLICATION_JSON)
                .content(expectedJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        mockMvc.perform(delete("/product/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetProductsWithDefaultPageable() throws Exception {
        List<ProductDto> expectedProducts = generateProducts();
        ProductSearchConfig searchConfig
                = getProductSearchConfig(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.by(DEFAULT_PRODUCT_SORT)));
        when(productFacade.getProducts(searchConfig)).thenReturn(expectedProducts);

        String expectedJson = objectMapper.writeValueAsString(expectedProducts);
        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetProductsWithPageableConfigured() throws Exception {
        List<ProductDto> expectedProducts = generateProducts();
        ProductSearchConfig searchConfig
                = getProductSearchConfig(PageRequest.of(1, 3, Sort.by("price").descending()));
        searchConfig.setText("test");
        searchConfig.setMinPrice(123);
        searchConfig.setMaxPrice(456);
        when(productFacade.getProducts(searchConfig)).thenReturn(expectedProducts);

        String expectedJson = objectMapper.writeValueAsString(expectedProducts);
        mockMvc.perform(get("/products")
                .queryParam("page", "1")
                .queryParam("size", "3")
                .queryParam("sort", "price,desc")
                .queryParam("minPrice", "123")
                .queryParam("maxPrice", "456")
                .queryParam("text", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetProductsForCategoryWithDefaultPageable() throws Exception {
        List<ProductDto> expectedProducts = generateProducts();
        ProductSearchConfig searchConfig
                = getProductSearchConfig(PageRequest.of(DEFAULT_PAGE, DEFAULT_SIZE, Sort.by(DEFAULT_PRODUCT_SORT)));
        searchConfig.setCategoryId(111);
        when(productFacade.getProducts(searchConfig)).thenReturn(expectedProducts);

        String expectedJson = objectMapper.writeValueAsString(expectedProducts);
        mockMvc.perform(get("/111/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetProductsForCategoryWithPageableConfigured() throws Exception {
        List<ProductDto> expectedProducts = generateProducts();
        ProductSearchConfig searchConfig
                = getProductSearchConfig(PageRequest.of(1, 3, Sort.by("price").descending()));
        searchConfig.setText("test");
        searchConfig.setCategoryId(111);
        searchConfig.setMinPrice(123);
        searchConfig.setMaxPrice(456);
        when(productFacade.getProducts(searchConfig)).thenReturn(expectedProducts);

        String expectedJson = objectMapper.writeValueAsString(expectedProducts);
        mockMvc.perform(get("/111/products")
                .queryParam("page", "1")
                .queryParam("size", "3")
                .queryParam("sort", "price,desc")
                .queryParam("minPrice", "123")
                .queryParam("maxPrice", "456")
                .queryParam("text", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    private ProductSearchConfig getProductSearchConfig(Pageable pageable) {
        ProductSearchConfig productSearchConfig = new ProductSearchConfig();
        productSearchConfig.setMinPrice(DEFAULT_MIN_PRICE);
        productSearchConfig.setMaxPrice(DEFAULT_MAX_PRICE);
        productSearchConfig.setText(DEFAULT_TEXT);
        productSearchConfig.setPageable(pageable);
        return productSearchConfig;
    }
}
