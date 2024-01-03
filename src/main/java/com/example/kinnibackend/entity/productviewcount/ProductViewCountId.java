package com.example.kinnibackend.entity.productviewcount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewCountId implements Serializable {
    private Long product;
    private Long users;
}
