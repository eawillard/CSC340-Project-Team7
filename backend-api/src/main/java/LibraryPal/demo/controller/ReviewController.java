
package LibraryPal.demo.controller;

import LibraryPal.demo.dto.ReviewResponse;
import LibraryPal.demo.entity.Review;
import LibraryPal.demo.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse submit(@Valid @RequestBody ReviewCreateRequest request) {
        Review review = reviewService.submit(
                request.getCustomerId(),
                request.getRating(),
                request.getComment()
        );

        return ReviewResponse.from(review);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse update(
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewUpdateRequest request) {

        Review review = reviewService.update(
                reviewId,
                request.getCustomerId(),
                request.getRating(),
                request.getComment()
        );

        return ReviewResponse.from(review);
    }

    public static class ReviewCreateRequest {
        @NotNull
        private Long customerId;

        @NotNull
        @Min(1)
        @Max(5)
        private Integer rating;

        @NotBlank
        private String comment;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }

    public static class ReviewUpdateRequest {
        @NotNull
        private Long customerId;

        @Min(1)
        @Max(5)
        private Integer rating;

        private String comment;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}
