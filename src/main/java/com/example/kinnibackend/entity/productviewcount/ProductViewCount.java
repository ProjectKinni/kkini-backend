package com.example.kinnibackend.entity.productviewcount;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@IdClass(ProductViewCountId.class)
public class ProductViewCount {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L; // 기본값을 0으로 초기화

    public void incrementViewCount(){
        this.viewCount += 1;
    }
}