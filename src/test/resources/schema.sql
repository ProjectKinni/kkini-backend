CREATE TABLE IF NOT EXISTS PUBLIC.product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255),
    is_green BOOLEAN DEFAULT FALSE,
    product_name VARCHAR(255),
    detail VARCHAR(255),
    average_rating FLOAT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    maker_name VARCHAR(255),
    serving_size DOUBLE,
    kcal DOUBLE,
    carbohydrate DOUBLE,
    protein DOUBLE,
    fat DOUBLE,
    sodium DOUBLE,
    cholesterol DOUBLE,
    saturated_fat DOUBLE,
    trans_fat DOUBLE,
    sugar DOUBLE,
    score DOUBLE,
    image VARCHAR(255),
    nut_image VARCHAR(255),
    nut_score DOUBLE
    );

INSERT INTO product (
    category_name, product_name, is_green, image, nut_image, serving_size, kcal, sugar,
    trans_fat, carbohydrate, protein, sodium, cholesterol, saturated_fat, fat,
                     score
) VALUES (
             '간식', '토종효모로 만든로만밀통밀식빵', true,
             'https://sitem.ssgcdn.com/67/15/48/item/1000017481567_i1_1100.jpg',
             'https://sitem.ssgcdn.com/67/15/48/qlty/1000017481567_q1.jpg',
             100.0, 271.0, 5.0, 0.0, 46.0, 12.0, 380.0, 2.0, 1.5, 6.0, 4
         );

INSERT INTO product (
    category_name, product_name, is_green, image, serving_size, kcal, sugar,
    trans_fat, carbohydrate, protein, sodium, cholesterol, saturated_fat, fat,
    score
) VALUES (
             '간식', '스위트 쵸코 마카롱', false,
             'https://sitem.ssgcdn.com/99/99/19/item/1000023199999_i1_1100.jpg',
             null, 30.0, 135.0, 10.0, 0.5, 12.0, 2.0, 20.0, 5.0, 2.0, 3
         );