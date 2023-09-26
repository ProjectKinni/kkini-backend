package com.example.kinnibackend.entity.like;

import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikeId.class)
public class ProductLike {

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
}