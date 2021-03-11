CREATE TRIGGER update_product_count AFTER INSERT ON product_category
REFERENCING NEW ROW AS  NEWROW
FOR EACH ROW UPDATE categories
  SET product_count = (SELECT count(category_id) FROM product_category WHERE category_id =  NEWROW.category_id)
WHERE id =  NEWROW.category_id;