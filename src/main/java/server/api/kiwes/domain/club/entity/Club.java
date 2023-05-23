package server.api.kiwes.domain.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.BaseTimeEntity;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.language.entity.Language;
import server.api.kiwes.domain.member.entity.Member;
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

    @ManyToMany
    @JoinTable(name = "CLUB_LANGUAGE",
            joinColumns = @JoinColumn(name = "CLUB_ID"),
            inverseJoinColumns = @JoinColumn(name = "LANGUAGE_ID"))
    private List<Language> languages = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<ClubMember> clubMembers = new ArrayList<>();

    @ManyToMany(mappedBy = "likeClubs")
    private List<Member> likedMembers;

    @OneToMany(mappedBy = "club")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Qna> qnas = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "CLUB_CATEGORY",
            joinColumns = @JoinColumn(name = "CLUB_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID"))
    private List<Category> categories = new ArrayList<>();
}
