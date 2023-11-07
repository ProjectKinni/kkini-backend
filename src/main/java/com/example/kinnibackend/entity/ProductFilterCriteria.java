package com.example.kinnibackend.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ProductFilterCriteria implements Serializable {

    private String searchTerm;
    private String category;
    private Boolean isGreen;
    private Boolean isLowCalorie;
    private Boolean isHighCalorie;
    private Boolean isSugarFree;
    private Boolean isLowSugar;
    private Boolean isLowCarb;
    private Boolean isHighCarb;
    private Boolean isKeto;
    private Boolean isLowTransFat;
    private Boolean isHighProtein;
    private Boolean isLowSodium;
    private Boolean isLowCholesterol;
    private Boolean isLowSaturatedFat;
    private Boolean isLowFat;
    private Boolean isHighFat;

//    public Boolean matches(ProductFilter productFilter){
//        if (searchTerm != null && !productFilter.getProductName().contains(searchTerm)) {
//            return false;
//        }
//        if (category != null && !productFilter.getCategory().equals(category)) {
//            return false;
//        }
//        if (isGreen != null && !isGreen.equals(productFilter.getIsGreen())) {
//            return false;
//        }
//        if (isLowCalorie != null && !isLowCalorie.equals(productFilter.getIsLowCalorie())) {
//            return false;
//        }
//        if (isHighCalorie != null && !isHighCalorie.equals(productFilter.getIsHighCalorie())) {
//            return false;
//        }
//        if (isSugarFree != null && !isSugarFree.equals(productFilter.getIsSugarFree())) {
//            return false;
//        }
//        if (isLowSugar != null && !isLowSugar.equals(productFilter.getIsLowSugar())) {
//            return false;
//        }
//        if (isLowCarb != null && !isLowCarb.equals(productFilter.getIsLowCarb())) {
//            return false;
//        }
//        if (isHighCarb != null && !isHighCarb.equals(productFilter.getIsHighCarb())) {
//            return false;
//        }
//        if (isKeto != null && !isKeto.equals(productFilter.getIsKeto())) {
//            return false;
//        }
//        if (isLowTransFat != null && !isLowTransFat.equals(productFilter.getIsLowTransFat())) {
//            return false;
//        }
//        if (isHighProtein != null && !isHighProtein.equals(productFilter.getIsHighProtein())) {
//            return false;
//        }
//        if (isLowSodium != null && !isLowSodium.equals(productFilter.getIsLowSodium())) {
//            return false;
//        }
//        if (isLowCholesterol != null && !isLowCholesterol.equals(productFilter.getIsLowCholesterol())) {
//            return false;
//        }
//        if (isLowSaturatedFat != null && !isLowSaturatedFat.equals(productFilter.getIsLowSaturatedFat())) {
//            return false;
//        }
//        if (isLowFat != null && !isLowFat.equals(productFilter.getIsLowFat())) {
//            return false;
//        }
//        if (isHighFat != null && !isHighFat.equals(productFilter.getIsHighFat())) {
//            return false;
//        }
//        return true;
//    }
}
