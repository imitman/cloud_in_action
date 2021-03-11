package com.epam.cloud.service;

public interface CategoryAssignmentService {

    void assignProductToCategory(int productId, int categoryId);

    void unAssignProductFromCategory(int productId, int categoryId);
}
