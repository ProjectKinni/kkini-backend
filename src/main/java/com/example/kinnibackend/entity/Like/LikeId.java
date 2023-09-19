package com.example.kinnibackend.entity.Like;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeId implements Serializable {
    private Long  product;
    private Long  users;
}