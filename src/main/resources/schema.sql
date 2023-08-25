DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
     `product_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `category_name` VARCHAR(255),
     `is_kkini` BOOLEAN NOT NULL,
     `hashtag_id` BIGINT,
     `vendor_id` INT NOT NULL,
     `vendor_name` VARCHAR(255) NOT NULL,
     `product_name` VARCHAR(255) NOT NULL,
     `product_image` VARCHAR(255) NOT NULL,
     `average_rating` FLOAT NOT NULL,
     `total_amount` DOUBLE,
     `calorie` DOUBLE,
     `sugar` DOUBLE,
     `trans_fat` DOUBLE,
     `carb` DOUBLE,
     `protein` DOUBLE,
     `sodium` DOUBLE,
     `saturated_fat` DOUBLE,
     `fat` DOUBLE,
     `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
