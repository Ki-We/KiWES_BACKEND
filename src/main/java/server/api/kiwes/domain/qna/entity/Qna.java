package server.api.kiwes.domain.qna.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private String qDate;           // 질문 등록 시각
    private String aDate;           // 답변 등록 시각

}
