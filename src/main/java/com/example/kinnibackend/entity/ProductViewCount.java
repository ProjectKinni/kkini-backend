package com.example.kinnibackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@IdClass(ProductViewCount.ProductViewCountId.class)
public class ProductViewCount {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    @Column(name = "view_count", nullable = false, columnDefinition = "Long default 0")
    private Long viewCount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductViewCountId implements Serializable {
        private Long product;
        private Long users;
    }
    public void incrementViewCount(){
        this.viewCount += 1;
    }
}