package com.example.kinnibackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "rating", columnDefinition = "DECIMAL(2, 1)")
    private Double rating;

    @Column(name = "content", columnDefinition = "BLOB")
    private String content;

    @Column(name = "image1", columnDefinition = "BLOB")
    private String image1;

    @Column(name = "image2", columnDefinition = "BLOB")
    private String image2;

    @Column(name = "image3", columnDefinition = "BLOB")
    private String image3;

    @Column(name = "image4", columnDefinition = "BLOB")
    private String image4;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateReview(Double rating, String content, String image1, String image2, String image3, String image4) {
        this.rating = rating;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

}
