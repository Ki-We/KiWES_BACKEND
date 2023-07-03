package server.api.kiwes.domain.club.dto;

import lombok.*;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.qna.entity.Qna;

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

    public static ClubArticleQnaDto of(Qna qna){
        Member questioner = qna.getQuestioner();
        return ClubArticleQnaDto.builder()
                .questionerImageUrl(questioner.getProfileImg())
                .questionerNickname(questioner.getNickname())
                .questionContent(qna.getQuestionContent())
                .questionDate(qna.getQDate())
                .build();
    }
}
