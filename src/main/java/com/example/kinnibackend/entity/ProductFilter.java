package com.example.kinnibackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ProductFilter {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToOne
    @JoinColumn(name = "product_id")
    @MapsId
    private Product product;

    @Column(name = "is_green", columnDefinition = "boolean default false")
    private Boolean isGreen;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "is_low_calorie")
    private Boolean isLowCalorie;

    @Column(name = "is_high_calorie")
    private Boolean isHighCalorie;

    @Column(name = "is_sugar_free")
    private Boolean isSugarFree;

    @Column(name = "is_low_sugar")
    private Boolean isLowSugar;

    @Column(name = "is_low_carb")
    private Boolean isLowCarb;

    @Column(name = "is_high_carb")
    private Boolean isHighCarb;

    @Column(name = "is_keto")
    private Boolean isKeto;

    @Column(name = "is_low_trans_fat")
    private Boolean isLowTransFat;

    @Column(name = "is_high_protein")
    private Boolean isHighProtein;

    @Column(name = "is_low_sodium")
    private Boolean isLowSodium;

    @Column(name = "is_low_cholesterol")
    private Boolean isLowCholesterol;

    @Column(name = "is_low_saturated_fat")
    private Boolean isLowSaturatedFat;

    @Column(name = "is_low_fat")
    private Boolean isLowFat;

    @Column(name = "is_high_fat")
    private Boolean isHighFat;
}
