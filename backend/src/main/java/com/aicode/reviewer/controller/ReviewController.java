package com.aicode.reviewer.controller;

import com.aicode.reviewer.dto.response.ApiResponse;
import com.aicode.reviewer.dto.response.ReviewResponse;
import com.aicode.reviewer.entity.Review;
import com.aicode.reviewer.repository.ReviewRepository;
import com.aicode.reviewer.security.CustomUserDetails;
import com.aicode.reviewer.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for review history and details.
 *
 * @author AI Code Reviewer Team
 */
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getUserReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Review> reviewPage = reviewRepository
                .findByUserIdOrderByCreatedAtDesc(userDetails.getId(), PageRequest.of(page, size));
        List<ReviewResponse> responses = reviewPage.getContent().stream()
                .map(projectService::toReviewResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id) {
        Review review = reviewRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body(ApiResponse.error("Access denied"));
        }
        return ResponseEntity.ok(ApiResponse.success(projectService.toReviewResponse(review)));
    }
}