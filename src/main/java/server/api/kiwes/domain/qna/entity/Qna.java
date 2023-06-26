package server.api.kiwes.domain.qna.entity;

import lombok.*;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.qna.constant.QnaAnsweredStatus;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Qna extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNA_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "QUESTIONER_ID")
    private Member questioner;

    @ManyToOne
    @JoinColumn(name = "RESPONDENT_ID")
    private Member respondent;

    private String questionContent;
    private String answerContent;
    private String qDate;                 // 질문 등록 시각
    private String aDate;                 // 답변 등록 시각

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private QnaAnsweredStatus isAnswered = QnaAnsweredStatus.NO; // 답변의 존재 여부

}
