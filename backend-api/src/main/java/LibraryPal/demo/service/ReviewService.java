
package LibraryPal.demo.service;

import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.entity.Review;
import LibraryPal.demo.repository.CustomerRepository;
import LibraryPal.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            CustomerRepository customerRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Review submit(Long customerId, Integer rating, String comment) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);
        review.setCustomer(customer);

        return reviewRepository.save(review);
    }

    @Transactional
    public Review update(
            Long reviewId,
            Long customerId,
            Integer rating,
            String comment) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Review not found"));

        if (!review.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException(
                    "You can only update your own reviews");
        }

        if (rating != null) {
            review.setRating(rating);
        }

        if (comment != null && !comment.isBlank()) {
            review.setComment(comment);
        }

        return reviewRepository.save(review);
    }
}
