package com.example.kinnibackend.product.entity;

import com.example.kinnibackend.product.dto.ProductCardListResponseDTO;
import com.example.kinnibackend.product.dto.ProductMainCardResponseDTO;
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

    @Column(name = "is_kkini", nullable = false)
    private boolean isKkini;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "hashtag_id")
    private Long hashtagId;

    @Column(name = "vendor_id", nullable = false)
    private int vendorId;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Column(name = "average_rating", nullable = false)
    private float averageRating;

    @Column(name = "product_price", nullable = false)
    private int productPrice;

    @Column(name = "total_amount")
    private Double totalAmount;

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

    public ProductCardListResponseDTO toProductCardListResponseDTO() {
        return ProductCardListResponseDTO.builder()
                .productId(this.productId)
                .isKkini(this.isKkini)
                .categoryName(this.categoryName)
                .vendorId(this.vendorId)
                .vendorName(this.vendorName)
                .productName(this.productName)
                .productImage(productImage)
                .averageRating(this.averageRating)
                .totalAmount(this.totalAmount)
                .calorie(this.calorie)
                .sugar(this.sugar)
                .transFat(this.transFat)
                .carb(this.carb)
                .protein(this.protein)
                .sodium(this.sodium)
                .saturatedFat(this.saturatedFat)
                .fat(this.fat)
                .build();
    }
    public ProductMainCardResponseDTO toProductMainCardResponseDTO() {
        return ProductMainCardResponseDTO.builder()
                .productId(this.productId)
                .isKkini(this.isKkini)
                .categoryName(this.categoryName)
                .hashtagId(this.hashtagId)
                .vendorId(this.vendorId)
                .vendorName(this.vendorName)
                .productName(this.productName)
                .productImage(productImage)
                .averageRating(this.averageRating)
                .build();
    }
}
