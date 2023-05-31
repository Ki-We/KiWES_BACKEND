package server.api.kiwes.domain.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.club_category.entity.ClubCategory;
import server.api.kiwes.domain.club_language.entity.ClubLanguage;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.heart.entity.Heart;
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
public class Club extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CLUB_ID")
    private Long id;

    private String date;            // 만나는 날짜
    private String dueTo;           // 모집 마감 날짜
    private Integer cost;           // 인당 예상 비용
    private Integer maxPeople;      // 모집 정원
    private Integer currentPeople;  // 현재 인원
    private Gender gender;          // 모집 성별 (남, 여, 누구나)
    private String title;           // 제목
    private String thumbnailUrl;    // 썸네일 이미지 주소
    private String content;         // 모임 소개
    private String locationsKeyword;// 위치 키워드
    private String location;        // 위치
    private Boolean isActivated;    // 활성화, 비활성화 여부

    @OneToMany(mappedBy = "club")
    private List<ClubLanguage> languages;

    @OneToMany(mappedBy = "club")
    private List<ClubMember> members;

    @OneToMany(mappedBy = "club")
    private List<Heart> hearts;

    @OneToMany(mappedBy = "club")
    private List<Review> reviews;

    @OneToMany(mappedBy = "club")
    private List<Qna> qnas;

    @OneToMany(mappedBy = "club")
    private List<ClubCategory> categories;
}
