package server.api.kiwes.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.review.entity.Review;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageReviewResponseDto {
    private Long reviewId;
    private Long clubId;
    private String clubTitle;
    private String reviewContent;
    private String reviewDate;
    private Boolean isAuthor;

    public static MypageReviewResponseDto of(Review review, Member member){
        return MypageReviewResponseDto.builder()
                .reviewId(review.getId())
                .clubId(review.getClub().getId())
                .clubTitle(review.getClub().getTitle())
                .reviewContent(review.getReviewContent())
                .reviewDate(review.getReviewDate())
                .isAuthor(review.getReviewer().equals(member))
                .build();
    }
}
