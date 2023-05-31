package server.api.kiwes.domain.heart.entity;

import lombok.Getter;
import lombok.Setter;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Heart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HEART_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

}
