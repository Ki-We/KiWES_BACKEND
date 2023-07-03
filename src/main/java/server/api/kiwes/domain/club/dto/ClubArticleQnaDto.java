package server.api.kiwes.domain.club.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubArticleQnaDto {
    String questionerImageUrl;
    String questionerNickname;
    String questionContent;
    String questionDate;
}
