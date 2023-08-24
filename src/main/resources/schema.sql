DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Category;

CREATE TABLE `Category`
(
    `category_id` bigint PRIMARY KEY AUTO_INCREMENT,
    `category_name` VARCHAR(255) NOT NULL
);

CREATE TABLE Product (
     `product_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     `is_kkini` BOOLEAN NOT NULL,
     `category_id` BIGINT,
     `hashtag_id` BIGINT,
     `vendor_id` INT NOT NULL,
     `vendor_name` VARCHAR(255) NOT NULL,
     `product_name` VARCHAR(255) NOT NULL,
     `product_image` VARCHAR(255) NOT NULL,
     `average_rating` FLOAT NOT NULL,
     `product_price` INT NOT NULL,
     `total_amount` DOUBLE,
     `calorie` DOUBLE,
     `sugar` DOUBLE,
     `trans_fat` DOUBLE,
     `carb` DOUBLE,
     `keto` DOUBLE,
     `protein` DOUBLE,
     `sodium` DOUBLE,
     `saturated_fat` DOUBLE,
     `fat` DOUBLE,
     `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     FOREIGN KEY (`category_id`) REFERENCES Category(`category_id`)
);
