package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductViewCountRepository viewCountRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductViewCountRepository viewCountRepository){
        this.productRepository=productRepository;
        this.viewCountRepository=viewCountRepository;
    }

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

    //끼니랭킹

    //Score update
    public void updateProductScore(Long productId){

        Long totalViewCount = viewCountRepository.findTotalViewCountByProductId(productId);

        ProductPreviewResponseDTO responseDTO = new ProductPreviewResponseDTO(
                productRepository.findById(productId).orElseThrow(()-> new RuntimeException("상품을 찾을 수 없습니다."))
        );

        double newScore = (double) (totalViewCount + responseDTO.getAverageRating());

        //소숫점 두자리까지 반올림한 값으로 반영
//        BigDecimal roundedScore = new BigDecimal(newScore).setScale(2, RoundingMode.HALF_UP);
//        newScore = roundedScore.doubleValue();

        responseDTO.setScore(newScore);
        productRepository.save(responseDTO.toEntity());
    }

    public List<ProductPreviewResponseDTO> findAllKkiniRanking(){
         //끼니 랭킹 조회 할 때 score 값 업데이트 하는 것으로 로직을 짜겠음.
        List<ProductPreviewResponseDTO> responseDtoList = new ArrayList<>();
        //Score 순대로 product 값 얻어오기
        List<Product> productList = productRepository.findAllByScoreAndUpdatedAt();
        for(Product product : productList){
            updateProductScore(product.getProductId());
            responseDtoList.add(new ProductPreviewResponseDTO(product));
        }
        return responseDtoList;
    }
}