package com.example.kinnibackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "is_green", nullable = false)
    private Boolean isGreen;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "average_rating", nullable = false)
    private Float averageRating;

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "maker_name", nullable = false)
    private String makerName;

    @Column(name = "serving_size")
    private Integer servingSize;

    @Column(name = "kcal")
    private Double kcal;

    @Column(name = "carbohydrate")
    private Double carbohydrate; // 탄수화물

    @Column(name = "protein")
    private Double protein; // 단백질

    @Column(name = "fat")
    private Double fat; // 지방

    @Column(name = "sodium")
    private Double sodium; // 나트륨

    @Column(name = "cholesterol")
    private Double cholesterol; // 콜레스테롤

    @Column(name = "saturated_fat")
    private Double saturatedFat; // 포화지방

    @Column(name = "trans_fat")
    private Double transFat;

    @Column(name = "sugar")
    private Double sugar;

    @Column(name = "score")
    private Double score;

    @Column(name = "image")
    private String image;

    @Column(name = "nut_image")
    private String nutImage;

    @Column(name = "nut_score")
    private Double nutScore;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
