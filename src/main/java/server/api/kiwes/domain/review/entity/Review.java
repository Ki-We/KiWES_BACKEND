package server.api.kiwes.domain.review.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    private String content;         // 후기 내용

}
