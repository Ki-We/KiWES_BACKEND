package server.api.kiwes.domain.club.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubArticleReviewDto {
    String reviewerImageUrl;
    String reviewerNickname;
    String content;
    String reviewDate;
}
