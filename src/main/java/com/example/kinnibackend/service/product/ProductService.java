package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public void updateProductAverageRating(Long productId) {
        Optional<Double> averageRatingOpt = productRepository.findAverageRatingByProductId(productId);

        averageRatingOpt.ifPresent(averageRating -> {
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found"));

            float roundedAverageRating = (float) (Math.round(averageRating.floatValue() * 100.0) / 100.0);
            product.updateAverageRating(roundedAverageRating);  // 평균 평점 업데이트

            productRepository.save(product);
        });
    }

    //Front 테스트 위해 임시로 쓸 로직
    public List<ProductPreviewResponseDTO> findAll(){

        List<ProductPreviewResponseDTO> responseDtoList = new ArrayList<>();
        List<Product> productList = productRepository.findAllByDesc();

        for(Product product : productList){
            ProductPreviewResponseDTO responseDTO = new ProductPreviewResponseDTO(product);
            responseDtoList.add(responseDTO);
        }

        return responseDtoList;
    }
}