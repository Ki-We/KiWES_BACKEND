package server.api.kiwes.domain.club.dto;

import lombok.*;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.review.entity.Review;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubArticleReviewDto {
    Long reviewId;
    String reviewerImageUrl;
    String reviewerNickname;
    String content;
    String reviewDate;
    Boolean isModified;

    public static ClubArticleReviewDto of(Review review){
        Member reviewer = review.getMember();
        return ClubArticleReviewDto.builder()
                .reviewId(review.getId())
                .reviewerImageUrl(reviewer.getProfileImg())
                .reviewerNickname(reviewer.getNickname())
                .content(review.getContent())
                .reviewDate(review.getModifiedDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")))
                .isModified(review.getIsModified())
                .build();
    }

}
