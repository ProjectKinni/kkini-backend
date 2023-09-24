package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.dto.product.ProductResponseWithReviewCountDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final ReviewRepository reviewRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductViewCountRepository viewCountRepository,
                          ReviewRepository reviewRepository){
        this.productRepository=productRepository;
        this.viewCountRepository=viewCountRepository;
        this.reviewRepository = reviewRepository;
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

    @Scheduled(cron = "0 0 0 * * ?") //매일 0시에 실행
    public void updateAllProductScores(){
        List<Product> productList = productRepository.findAll();
        for(Product product : productList){
            updateProductScore(product.getProductId());
        }
    }

    public List<ProductResponseWithReviewCountDTO> findAllKkiniRanking(){
        List<ProductResponseWithReviewCountDTO> responseDtoList = new ArrayList<>();
        //Score 순대로 product 값 얻어오기
        List<Product> productList = productRepository.findAllByScoreAndUpdatedAt();
        for(Product product : productList){
            responseDtoList.add(new ProductResponseWithReviewCountDTO(product, reviewRepository.findTotalReviewCountByProductId(product.getProductId())));
        }
        return responseDtoList;
    }

    public List<ProductResponseWithReviewCountDTO> findAllGreenRanking(){
        List<ProductResponseWithReviewCountDTO> responseDTOList = new ArrayList<>();
        //isGreen = true 인 상품 중에 nut_score 높은 순으로 product 얻어오기
        List<Product> productList = productRepository.findAllByIsGreenIsTrueOrderByNutScoreDescUpdatedAtDescProductIdDesc();
        for(Product product : productList){
            responseDTOList.add(new ProductResponseWithReviewCountDTO(product, reviewRepository.findTotalReviewCountByProductId(product.getProductId())));
        }
        return responseDTOList;
    }
}