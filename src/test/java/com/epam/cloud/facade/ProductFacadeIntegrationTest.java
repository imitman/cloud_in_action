package com.epam.cloud.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.dto.ProductSearchConfig;

public class ProductFacadeIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateProduct() {
        ProductDto expectedProduct = generateProductDtoWithSingleCategory();

        ProductDto actual = productFacade.createProduct(expectedProduct);

        assertThat(actual.getId()).isEqualTo(expectedProduct.getId());
        assertThat(actual.getName()).isEqualTo(expectedProduct.getName());
        assertThat(actual.getPrice()).isEqualTo(expectedProduct.getPrice());
        assertThat(actual.getCategories()).isEqualTo(expectedProduct.getCategories());
    }

    @Test
    public void shouldGetProduct() {
        ProductDto expectedProduct = generateAndStoreProductWithSingleCategory();

        ProductDto actual = productFacade.getProduct(expectedProduct.getId());

        assertThat(actual.getId()).isEqualTo(expectedProduct.getId());
        assertThat(actual.getName()).isEqualTo(expectedProduct.getName());
        assertThat(actual.getPrice()).isEqualTo(expectedProduct.getPrice());
        assertThat(actual.getCategories()).isEqualTo(expectedProduct.getCategories());
    }

    @Test
    public void shouldUpdateProduct() {
        ProductDto productDto = generateAndStoreProductWithSingleCategory();
        int generatedId = productDto.getId();
        ProductDto updatedProduct = generateProductDtoWithSingleCategory();
        updatedProduct.setId(generatedId);

        ProductDto actual = productFacade.updateProduct(updatedProduct);

        assertThat(actual.getId()).isEqualTo(generatedId);
        assertThat(actual.getName()).isEqualTo(updatedProduct.getName());
        assertThat(actual.getPrice()).isEqualTo(updatedProduct.getPrice());
        assertThat(actual.getCategories()).isEqualTo(updatedProduct.getCategories());
    }

    @Test()
    public void shouldDeleteProduct() {
        ProductDto productDto = generateAndStoreProductWithSingleCategory();

        productFacade.removeProduct(productDto.getId());

        assertThrows(EntityNotFoundException.class, () -> productFacade.getProduct(productDto.getId()));
    }

    @Test
    public void shouldGetProductsSortByNameAsc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .sorted(Comparator.comparing(ProductDto::getName))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("name")));

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsSortByNameDesc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .sorted(Comparator.comparing(ProductDto::getName).reversed())
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("name").descending()));

        ProductDto[] actualProducts = productFacade.getProducts(productSearchConfig).toArray(new ProductDto[0]);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsSortByPriceAsc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .sorted(Comparator.comparing(ProductDto::getPrice))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));

        ProductDto[] actualProducts = productFacade.getProducts(productSearchConfig).toArray(new ProductDto[0]);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsSortByPriceDesc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .sorted(Comparator.comparing(ProductDto::getPrice).reversed())
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price").descending()));

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsForCategorySortByNameAsc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .sorted(Comparator.comparing(ProductDto::getName))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("name")));
        productSearchConfig.setCategoryId(firstCategoryId);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }


    @Test
    public void shouldGetProductsForCategorySortByNameDesc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .sorted(Comparator.comparing(ProductDto::getName).reversed())
                .toArray(ProductDto[]::new);

        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("name").descending()));
        productSearchConfig.setCategoryId(firstCategoryId);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsForCategorySortByPriceAsc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .sorted(Comparator.comparing(ProductDto::getPrice))
                .toArray(ProductDto[]::new);

        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));
        productSearchConfig.setCategoryId(firstCategoryId);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsForCategorySortByPriceDesc() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .sorted(Comparator.comparing(ProductDto::getPrice).reversed())
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price").descending()));
        productSearchConfig.setCategoryId(firstCategoryId);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactly(expectedProducts);
    }

    @Test
    public void shouldGetProductsInPriceRange() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int expectedMaxPrice = generatedProducts.get(0).getPrice();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> productDto.getPrice() <= expectedMaxPrice)
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));
        productSearchConfig.setMaxPrice(expectedMaxPrice);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactlyInAnyOrder(expectedProducts);
    }

    @Test
    public void shouldGetProductsForCategoryInPriceRange() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        int expectedMinPrice = generatedProducts.get(0).getPrice();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .filter(productDto -> productDto.getPrice() >= expectedMinPrice)
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));
        productSearchConfig.setCategoryId(firstCategoryId);
        productSearchConfig.setMinPrice(expectedMinPrice);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactlyInAnyOrder(expectedProducts);
    }

    @Test
    public void shouldGetProductsWithNameContainingText() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        String expectedText = generatedProducts.get(0).getName();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> productDto.getName().contains(expectedText))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));
        productSearchConfig.setText(expectedText);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactlyInAnyOrder(expectedProducts);
    }

    @Test
    public void shouldGetProductsForCategoryWithNameContainingText() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        String expectedText = generatedProducts.get(0).getName();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .filter(productDto -> productDto.getName().contains(expectedText))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price")));
        productSearchConfig.setCategoryId(firstCategoryId);
        productSearchConfig.setText(expectedText);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactlyInAnyOrder(expectedProducts);
    }

    @Test
    public void shouldGetProductsComplexCondition() {
        List<ProductDto> generatedProducts = generateAndStoreProductsWithCategories();
        int firstCategoryId = generatedProducts.get(0).getCategories().get(0).getId();
        int expectedMaxPrice = generatedProducts.get(0).getPrice();
        String expectedText = generatedProducts.get(0).getName();
        ProductDto[] expectedProducts = generatedProducts.stream()
                .filter(productDto -> hasCategory(firstCategoryId, productDto))
                .filter(productDto -> productDto.getPrice() <= expectedMaxPrice)
                .filter(productDto -> productDto.getName().contains(expectedText))
                .sorted(Comparator.comparing(ProductDto::getPrice).thenComparing((ProductDto::getName)))
                .toArray(ProductDto[]::new);
        ProductSearchConfig productSearchConfig
                = getProductSearchConfig(PageRequest.of(0, BOUND, Sort.by("price").and(Sort.by("name"))));
        productSearchConfig.setCategoryId(firstCategoryId);
        productSearchConfig.setMaxPrice(expectedMaxPrice);
        productSearchConfig.setText(expectedText);

        List<ProductDto> actualProducts = productFacade.getProducts(productSearchConfig);

        assertThat(actualProducts).containsExactlyInAnyOrder(expectedProducts);
    }

    private boolean hasCategory(int firstCategoryId, ProductDto productDto) {
        return productDto.getCategories().stream().anyMatch(categoryDto -> categoryDto.getId() == firstCategoryId);
    }

    private ProductSearchConfig getProductSearchConfig(Pageable pageable) {
        ProductSearchConfig productSearchConfig = new ProductSearchConfig();
        productSearchConfig.setMinPrice(0);
        productSearchConfig.setMaxPrice(BOUND);
        productSearchConfig.setText("");
        productSearchConfig.setPageable(pageable);
        return productSearchConfig;
    }
}