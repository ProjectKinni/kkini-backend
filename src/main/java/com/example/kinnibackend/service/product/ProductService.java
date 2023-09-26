package com.example.kinnibackend.service.product;

import com.example.kinnibackend.dto.product.ProductFilterResponseDTO;
import com.example.kinnibackend.dto.product.ProductPreviewResponseDTO;
import com.example.kinnibackend.dto.product.ProductResponseWithReviewCountDTO;
import com.example.kinnibackend.entity.Product;
import com.example.kinnibackend.repository.product.ProductRepository;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    //Top 12 끼니 랭킹
    public List<ProductPreviewResponseDTO> findTopKkiniRanking(){

        List<ProductPreviewResponseDTO> responseDtoList = new ArrayList<>();
        Pageable topProducts = PageRequest.of(0, 12);
        List<Product> productList = productRepository.findTopProductsByScoreAndUpdatedAt(topProducts);

        for(Product product : productList){
            responseDtoList.add(new ProductPreviewResponseDTO(product));
        }
        return responseDtoList;
    }

    //Top12 끼니 그린
    public List<ProductPreviewResponseDTO> findTopKkiniGreen(){
        List<ProductPreviewResponseDTO> responseDtoList = new ArrayList<>();
        Pageable topProducts = PageRequest.of(0, 12);
        List<Product> productList = productRepository.findTopProductsByIsGreenIsTrueOrderByNutScoreDescUpdatedAtDescProductIdDesc(topProducts);

        for(Product product : productList){
            responseDtoList.add(new ProductPreviewResponseDTO(product));
        }
        return responseDtoList;
    }

    //필터링 적용된 끼니그린
    public List<ProductResponseWithReviewCountDTO> findAllGreenRanking(int page) {
        // Pageable
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        // isGreen이 true이고 nut_score가 높은 순서로 상품을 가져옵니다.
        List<Product> productList = productRepository.findAllByIsGreenIsTrueOrderByNutScoreDescAndCategoryNameAndFilters(pageable);

        // 각 상품에 대한 리뷰 수를 찾아서 DTO로 변환합니다.
        List<ProductResponseWithReviewCountDTO> result = new ArrayList<>();
        for (Product product : productList) {
            Long reviewCount = reviewRepository.findTotalReviewCountByProductId(product.getProductId());
            ProductResponseWithReviewCountDTO dto = new ProductResponseWithReviewCountDTO(product, reviewCount);
            result.add(dto);
        }

        return result;
    }

    //필터링 적용 된 끼니랭킹
    public List<ProductResponseWithReviewCountDTO> findAllKkiniRankingByCategoriesAndFilters(
            int page) {
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize);

        // 선택한 카테고리 및 필터에 해당하는 제품들을 모두 가져옵니다.
        List<Product> productList = productRepository.findAllByScoreAndCategoryNameAndFilters(pageable);

        // 각 상품에 대한 리뷰 수를 찾아서 DTO로 변환합니다.
        List<ProductResponseWithReviewCountDTO> result = new ArrayList<>();
        for (Product product : productList) {
            Long reviewCount = reviewRepository.findTotalReviewCountByProductId(product.getProductId());
            ProductResponseWithReviewCountDTO dto = new ProductResponseWithReviewCountDTO(product, reviewCount);
            result.add(dto);
        }

        return result;
    }
}