package server.api.kiwes.domain.club.dto;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubArticleResponseDto {
    ClubArticleBaseInfoDto baseInfo;
    ClubArticleMemberInfoDto memberInfo;
    List<ClubArticleQnaDto> qnas;
    List<ClubArticleReviewDto> reviews;

    @Builder.Default
    Boolean isHost = false;

    Boolean isHeart;
}
