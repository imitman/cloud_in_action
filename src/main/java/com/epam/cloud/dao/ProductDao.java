package com.epam.cloud.dao;

import static com.epam.cloud.constant.CloudConstants.SqlQuery.SELECT_PRODUCTS_BY_CATEGORY_TEXT_PRICE;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.epam.cloud.entity.ProductEntity;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, Integer> {

    @Query(value = SELECT_PRODUCTS_BY_CATEGORY_TEXT_PRICE)
    Page<ProductEntity> findAllByNameAndPriceForGivenCategory(String text, int categoryId, int minPrice, int maxPrice, Pageable pageable);

    Page<ProductEntity> findAllByNameContainingAndPriceBetween(String text, int minPrice, int maxPrice, Pageable pageable);
}
