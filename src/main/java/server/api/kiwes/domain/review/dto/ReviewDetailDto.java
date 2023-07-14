package server.api.kiwes.domain.review.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDetailDto {
    String reviewerProfileImg;
    String reviewerNickname;
    String reviewContent;
}
