package com.epam.cloud.controller;

import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_MAX_PRICE;
import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_MIN_PRICE;
import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_PRODUCT_SORT;
import static com.epam.cloud.constant.CloudConstants.ControllerConstants.DEFAULT_TEXT;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cloud.annotation.SwaggerPageable;
import com.epam.cloud.dto.ProductDto;
import com.epam.cloud.dto.ProductSearchConfig;
import com.epam.cloud.facade.ProductFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "ProductController")
@RestController
public class ProductController {

    @Autowired
    private ProductFacade productFacade;

    @ApiOperation(value = "Remove a product")
    @DeleteMapping("/product/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeProduct(@ApiParam(value = "Identifier of a product", example = "5", required = true)
                              @PathVariable int productId) {
        productFacade.removeProduct(productId);
    }

    @ApiOperation(value = "Create a product")
    @PostMapping(value = "/product", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDto createProduct(@ApiParam(value = "Product data")
                                    @RequestBody ProductDto productDto) {
        return productFacade.createProduct(productDto);
    }

    @ApiOperation(value = "Get a product")
    @GetMapping(value = "/product/{productId}", produces = APPLICATION_JSON_VALUE)
    public ProductDto getProduct(@ApiParam(value = "Identifier of a product", example = "5", required = true)
                                 @PathVariable int productId) {
        return productFacade.getProduct(productId);
    }

    @ApiOperation(value = "Update a product")
    @PutMapping(value = "/product", produces = APPLICATION_JSON_VALUE)
    public ProductDto updateProduct(@ApiParam(value = "Product data")
                                    @RequestBody ProductDto productDto) {
        return productFacade.updateProduct(productDto);
    }

    @ApiOperation(value = "Get products")
    @SwaggerPageable
    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    public List<ProductDto> getProducts(@PageableDefault(sort = {DEFAULT_PRODUCT_SORT}, size = DEFAULT_PAGE_SIZE)
                                        @ApiIgnore Pageable pageable,
                                        @ApiParam(value = "The text contained in a product name")
                                        @RequestParam(defaultValue = DEFAULT_TEXT) String text,
                                        @ApiParam(value = "Min price")
                                        @RequestParam(defaultValue = DEFAULT_MIN_PRICE) int minPrice,
                                        @ApiParam(value = "Max price")
                                        @RequestParam(defaultValue = DEFAULT_MAX_PRICE) int maxPrice) {
        ProductSearchConfig productSearchConfig = new ProductSearchConfig.Builder(pageable)
                .text(text)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        return productFacade.getProducts(productSearchConfig);
    }

    @ApiOperation(value = "Get products for given category")
    @SwaggerPageable
    @GetMapping(value = "/{categoryId}/products", produces = APPLICATION_JSON_VALUE)
    public List<ProductDto> getProductsForCategory(@PageableDefault(sort = {DEFAULT_PRODUCT_SORT}, size = DEFAULT_PAGE_SIZE)
                                                   @ApiIgnore Pageable pageable,
                                                   @ApiParam(value = "The text contained in a product name")
                                                   @RequestParam(defaultValue = DEFAULT_TEXT) String text,
                                                   @ApiParam(value = "Min price")
                                                   @RequestParam(defaultValue = DEFAULT_MIN_PRICE) int minPrice,
                                                   @ApiParam(value = "Max price")
                                                   @RequestParam(defaultValue = DEFAULT_MAX_PRICE) int maxPrice,
                                                   @PathVariable int categoryId) {
        ProductSearchConfig productSearchConfig = new ProductSearchConfig.Builder(pageable)
                .categoryId(categoryId)
                .text(text)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        return productFacade.getProducts(productSearchConfig);
    }
}
