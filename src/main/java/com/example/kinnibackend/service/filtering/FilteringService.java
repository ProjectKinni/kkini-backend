package com.example.kinnibackend.service.filtering;

import com.example.kinnibackend.dto.product.ProductCardListResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilteringService {

    @Autowired
    private final ProductRepository productRepository;

    // 상품 리스트에서 필터링 체크시, 해당 필터링으로 상품 반환
    public List<ProductCardListResponseDTO> filterProductsByCriteria(String criteria) {
        List<Product> rawProducts = productRepository.findAll();

        List<ProductCardListResponseDTO> products = new ArrayList<>();
        for (Product product : rawProducts) {
            products.add(ProductCardListResponseDTO.fromEntity(product));
        }

        switch(criteria) {
            case "저칼로리":
                products = filterByLowCalorie(products);
                break;
            case "슈가프리":
                products = filterBySugarFree(products);
                break;
            case "로우슈가":
                products = filterByLowSugar(products);
                break;
            case "저탄수화물":
                products = filterByLowCarb(products);
                break;
            case "키토":
                products = filterByKeto(products);
                break;
            case "트랜스 지방":
                products = filterByTransFat(products);
                break;
            case "고단백":
                products = filterByHighProtein(products);
                break;
            case "저나트륨":
                products = filterByLowSodium(products);
                break;
            case "콜레스테롤":
                products = filterByCholesterol(products);
                break;
            case "포화지방":
                products = filterBySaturatedFat(products);
                break;
            case "저지방":
                products = filterByLowFat(products);
                break;
        }

        return products;
    }

    //필터들..
    // stream = 리스트에 들어있는 각 제품에 대한 연속적인 연산 시작
    // filter연산은 조건에 맞는 제품만을 스트림에 남기기
    // collect연산은 최종적으로 필터링된 제품들을 리스트로 모아서 반환
    private List<ProductCardListResponseDTO> filterByLowCalorie(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCalorie() <= product.getTotalAmount() * 2)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterBySugarFree(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSugar() <= 1)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowSugar(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSugar() <= product.getTotalAmount() * 0.05) // 5%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowCarb(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCarb() >= product.getTotalAmount() * 0.11
                        && product.getCarb() <= product.getTotalAmount() * 0.20) // 11~20%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByKeto(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getCarb() <= product.getTotalAmount() * 0.10) // 10%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByTransFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getTransFat() <= 1)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByHighProtein(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getProtein() >= product.getTotalAmount() * 0.20) // 20% 이상의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowSodium(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSodium() <= product.getTotalAmount() * 2)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByCholesterol(List<ProductCardListResponseDTO> products){
        return products.stream()
                .filter(product -> product.getCholesterol() < 300)
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterBySaturatedFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getSaturatedFat() <= product.getTotalAmount() * 0.02) // 2%의 기준값
                .collect(Collectors.toList());
    }

    private List<ProductCardListResponseDTO> filterByLowFat(List<ProductCardListResponseDTO> products) {
        return products.stream()
                .filter(product -> product.getFat() <= product.getTotalAmount() * 0.04) // 4%의 기준값
                .collect(Collectors.toList());
    }
}
