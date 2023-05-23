package server.api.kiwes.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.language.entity.Language;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.review.entity.Review;
import server.api.kiwes.global.entity.Gender;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String profileImg;              // 프로필 이미지 주소
    private String nickname;                // 닉네임
    private Gender gender;                  // 성별 (남, 여)
    private String birth;                   // 생년월일
    private String introduction;            // 자기소개
    private Nationality nationality;        // 국적 (한국, 외국)

    @ManyToMany
    @JoinTable(name = "MEMBER_LANGUAGE",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "LANGUAGE_ID"))
    private List<Language> languages = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "HEART",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CLUB_ID")
    )
    private List<Club> likeClubs = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "questioner")
    private List<Qna> questions = new ArrayList<>();

    @OneToMany(mappedBy = "respondent")
    private List<Qna> answers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "MEMBER_CATEGORY",
            joinColumns = @JoinColumn(name = "MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private List<Category> categories = new ArrayList<>();

}
