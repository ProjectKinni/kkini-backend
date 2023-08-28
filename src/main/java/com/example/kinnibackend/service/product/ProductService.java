package com.example.kinnibackend.service.product;

import com.example.kinnibackend.repository.product.ProductJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJPARepository productJPARepository;

}