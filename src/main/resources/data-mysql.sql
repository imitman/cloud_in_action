INSERT IGNORE INTO products (id, name, price) VALUES (1, 'A product', 100);
INSERT IGNORE INTO products (id, name, price) VALUES (2, 'B product', 200);
INSERT IGNORE INTO products (id, name, price) VALUES (3, 'C product', 300);
INSERT IGNORE INTO products (id, name, price) VALUES (4, 'D product', 400);
INSERT IGNORE INTO products (id, name, price) VALUES (5, 'E product', 500);
INSERT IGNORE INTO products (id, name, price) VALUES (6, 'F product', 600);
INSERT IGNORE INTO products (id, name, price) VALUES (7, 'G product', 700);
INSERT IGNORE INTO products (id, name, price) VALUES (8, 'H product', 800);
INSERT IGNORE INTO products (id, name, price) VALUES (9, 'I product', 900);
INSERT IGNORE INTO products (id, name, price) VALUES (10, 'J product', 1000);

INSERT IGNORE INTO categories (id, name, product_count) VALUES (1,'Digital', 4);
INSERT IGNORE INTO categories (id, name, product_count) VALUES (2,'Physical', 3);
INSERT IGNORE INTO categories (id, name, product_count) VALUES (3,'Useful', 2);
INSERT IGNORE INTO categories (id, name, product_count) VALUES (4,'So so', 1);

INSERT IGNORE INTO product_category (product_id, category_id) VALUES (1, 1);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (2, 2);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (3, 3);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (4, 4);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (5, 1);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (6, 2);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (7, 3);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (8, 1);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (9, 2);
INSERT IGNORE INTO product_category (product_id, category_id) VALUES (10, 1);

DROP TRIGGER IF EXISTS update_product_count;
CREATE TRIGGER update_product_count AFTER INSERT ON product_category
FOR EACH ROW UPDATE categories
    SET product_count = (SELECT count(category_id) FROM product_category WHERE category_id = NEW.category_id)
WHERE id = NEW.category_id;