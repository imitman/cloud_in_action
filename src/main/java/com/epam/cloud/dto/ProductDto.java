package com.epam.cloud.dto;

import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProductDto")
public class ProductDto {
    @ApiModelProperty(value = "Unique identifier, integer")
    private int id;
    @ApiModelProperty(value = "Name", example = "Custom name")
    private String name;
    @ApiModelProperty(value = "Price", example = "500")
    private int price;
    @ApiModelProperty(value = "Category list")
    private List<CategoryDto> categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return id == that.id && price == that.price && Objects.equals(name, that.name) && Objects.equals(categories, that.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, categories);
    }
}
