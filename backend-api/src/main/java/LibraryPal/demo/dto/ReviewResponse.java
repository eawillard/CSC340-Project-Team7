
package LibraryPal.demo.dto;

import LibraryPal.demo.entity.Review;

public class ReviewResponse {

    private Long id;
    private Integer rating;
    private String comment;
    private String replyText;
    private Long customerId;

    public ReviewResponse() {}

    public ReviewResponse(
            Long id,
            Integer rating,
            String comment,
            String replyText,
            Long customerId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.replyText = replyText;
        this.customerId = customerId;
    }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getReplyText(),
                review.getCustomer().getId()
        );
    }

    public Long getId() { return id; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public String getReplyText() { return replyText; }
    public Long getCustomerId() { return customerId; }
}
