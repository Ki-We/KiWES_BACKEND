package server.api.kiwes.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.heart.entity.Heart;
import server.api.kiwes.domain.member.constant.Role;
import server.api.kiwes.domain.member_category.entity.MemberCategory;
import server.api.kiwes.domain.member_language.entity.MemberLanguage;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.review.entity.Review;
import server.api.kiwes.global.entity.Gender;


import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Lob
    private String profileImg;              // 프로필 이미지 주소
    private String nickname;                // 닉네임
    @Enumerated(EnumType.STRING)
    private Gender gender;                  // 성별 (남, 여)
    private String birth;                   // 생년월일
    private String introduction;            // 자기소개
    @Enumerated(EnumType.STRING)
    private Nationality nationality;        // 국적 (한국, 외국)
    private String email;                    // 이메일

    @Enumerated(EnumType.STRING)
    private Role role;                      // security
    private Boolean isDeleted;

    @OneToMany(mappedBy = "member")
    private List<MemberLanguage> languages;

    @OneToMany(mappedBy = "member")
    private List<Heart> hearts;

    @OneToMany(mappedBy = "member")
    private List<Review> reviews;

    @OneToMany(mappedBy = "questioner")
    private List<Qna> questions;

    @OneToMany(mappedBy = "respondent")
    private List<Qna> answers;

    @OneToMany(mappedBy = "member")
    private List<MemberCategory> categories;

    public Member(String email, String profileImg, Gender gender) {
        this.email = email;
        this.profileImg = profileImg;
        this.gender = gender;
        this.isDeleted = false;
    }

    public void setMember(String nickname, String birth, String introduction,String nationality) {
        this.nickname = nickname;
        this.birth = birth;
        this.introduction = introduction;
        this.nationality = Nationality.valueOf(nationality);
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

