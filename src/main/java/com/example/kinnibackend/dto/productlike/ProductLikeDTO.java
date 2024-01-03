package com.example.kinnibackend.dto.productlike;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Users;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductLikeDTO {
    private Long productId;
    private Long userId;

    public static ProductLikeDTO fromEntity(Product product, Users users) {
        return ProductLikeDTO.builder()
                .productId(product.getProductId())
                .userId(users.getUserId())
                .build();
    }
}