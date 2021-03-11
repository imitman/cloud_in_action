package com.epam.cloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataTypeClass = String.class, paramType = "query", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataTypeClass = String.class, paramType = "query", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", dataTypeClass = String.class, paramType = "query",
                value = "Supported sorting attributes for product: name/price, for category: name/productCount.<br/>" +
                        "Can be followed by sort direction like 'price,desc'.<br/>Multiple sort criteria are supported.")})
public @interface SwaggerPageable {
}