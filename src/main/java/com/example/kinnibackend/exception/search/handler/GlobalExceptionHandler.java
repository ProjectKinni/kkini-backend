package com.example.kinnibackend.exception.search.handler;

import com.example.kinnibackend.exception.search.CategoryNotFoundException;
import com.example.kinnibackend.exception.search.InvalidSearchTermException;
import com.example.kinnibackend.exception.search.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({InvalidSearchTermException.class})
    public ResponseEntity<String> handleInvalidSearchTermException(InvalidSearchTermException ex) {
        logger.error("Invalid search term: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 검색어입니다.");
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        logger.error("Product not found: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품을 찾을 수 없습니다.");
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        logger.error("Category not found: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("카테고리를 찾을 수 없습니다.");
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleGenericRuntimeException(RuntimeException ex) {
        logger.error("Internal server error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("NullPointerException!!");
    }
}
