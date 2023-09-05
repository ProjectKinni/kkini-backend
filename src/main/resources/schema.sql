DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
     `product_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `category_name` VARCHAR(255),
     `is_green` BOOLEAN NOT NULL,
     `product_name` VARCHAR(255) NOT NULL,
     `detail` TEXT NOT NULL,
     `average_rating` DECIMAL(2, 1) NOT NULL,
     `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `maker_name` VARCHAR(255) NOT NULL,
     `serving_size` INT,
     `kcal` DOUBLE,
     `carbohydrate` DOUBLE,
     `protein` DOUBLE,
     `fat` DOUBLE,
     `sodium` DOUBLE,
     `cholesterol` INT,
     `saturated_fat` DOUBLE,
     `trans_fat` DOUBLE,
     `sugar` DOUBLE,
     `score` DOUBLE,
     `image` TEXT NOT NULL,
     `nut_image` TEXT NOT NULL,
     `nut_score` DOUBLE
);
