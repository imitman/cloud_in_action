package com.epam.cloud.constant;

public class CloudConstants {

    public static class ControllerConstants {
        public static final int DEFAULT_PAGE_SIZE = 5;
        public static final String DEFAULT_MIN_PRICE = "0";
        public static final String DEFAULT_MAX_PRICE = "999999999";
        public static final String DEFAULT_PRODUCT_SORT = "name";
        public static final String DEFAULT_CATEGORY_SORT = "productCount";
        public static final String DEFAULT_TEXT = "";
    }

    public static class SqlQuery {
        public static final String SELECT_PRODUCTS_BY_CATEGORY_TEXT_PRICE =
                "SELECT pro FROM ProductEntity pro " +
                        "LEFT JOIN pro.categories cat " +
                        "WHERE cat.id = ?2 " +
                        "AND pro.price >= ?3 " +
                        "AND pro.price <= ?4 " +
                        "AND pro.name LIKE %?1%";
    }

    private CloudConstants() {
        System.exit(0);
    }
}
