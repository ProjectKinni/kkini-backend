package com.example.kinnibackend.dto.product;

import com.example.kinnibackend.entity.Product;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductRequestDTO {
    private Long productId;
    private Double score;

    public ProductRequestDTO(Product entity){
        this.productId = entity.getProductId();
        this.score = entity.getScore();
    }

    public Product toEntity(){
        return Product.builder()
                .productId(productId)
                .score(score)
                .build();
    }
}