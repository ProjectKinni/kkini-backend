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

    @Column(name = "is_kkini", nullable = false)
    private Boolean isKkini;

    @Column(name = "hashtag_id")
    private Long hashtagId;

    @Column(name = "vendor_id", nullable = false)
    private Integer vendorId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Column(name = "average_rating", nullable = false)
    private Float averageRating;

    @Column(name = "total_amount")
    private Double totalAmount; // 변수명 변경 예정

    @Column(name = "calorie")
    private Double calorie;

    @Column(name = "sugar")
    private Double sugar;

    @Column(name = "trans_fat")
    private Double transFat;

    @Column(name = "carb")
    private Double carb; // 탄수화물

    @Column(name = "protein")
    private Double protein; // 단백질

    @Column(name = "sodium")
    private Double sodium; // 나트륨

    @Column(name = "saturated_fat")
    private Double saturatedFat; // 포화지방

    @Column(name = "fat")
    private Double fat; // 지방

    @CreationTimestamp
    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
