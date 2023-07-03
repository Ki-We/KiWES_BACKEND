package server.api.kiwes.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.dto.*;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club_category.entity.ClubCategory;
import server.api.kiwes.domain.club_language.entity.ClubLanguage;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.heart.service.HeartService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.entity.Nationality;
import server.api.kiwes.domain.qna.constant.QnaDeletedStatus;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.review.entity.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubDetailService {
    private final HeartService heartService;

    /**
     * 모임 상세정보 페이지에 필요한 정보들 리턴
     */
    public ClubArticleResponseDto getClubDetail(Member member, Club club) {
        ClubArticleBaseInfoDto baseInfoDto;
        ClubArticleMemberInfoDto memberInfoDto;
        List<ClubArticleQnaDto> qnas = new ArrayList<>();
        List<ClubArticleReviewDto> reviews = new ArrayList<>();

        Member host = getHostFrom(club);
        Map<Nationality, Integer> memberNationCountMap = getNationCountFrom(club);

        baseInfoDto = ClubArticleBaseInfoDto.builder()
                .title(club.getTitle())
                .thumbnailImageUrl(club.getThumbnailUrl())
                .heartCount(club.getHearts().size())
                .tags(getTagList(club))
                .date(formateDate(club.getDate()))
                .dueTo(formateDate(club.getDueTo()))
                .cost(club.getCost())
                .gender(club.getGender().getName())
                .locationsKeyword(club.getLocationsKeyword())
                .content(club.getContent())
                .location(club.getLocation())
                .build();

        memberInfoDto = ClubArticleMemberInfoDto.builder()
                .hostThumbnailImage(host.getProfileImg())
                .hostNickname(host.getNickname())
                .koreanCount(memberNationCountMap.get(Nationality.KOREA))
                .foreignerCount(memberNationCountMap.get(Nationality.FOREIGN))
                .maxPeople(club.getMaxPeople())
                .build();


        // qnaDtos 채우기 - 최대 2개
        for(Qna qna : club.getQnas()){
            if(qna.getIsDeleted().equals(QnaDeletedStatus.YES)) continue;
            ClubArticleQnaDto clubArticleQnaDto = buildClubArticleDtoFrom(qna);
            qnas.add(clubArticleQnaDto);

            if(qnas.size() > 2) break;
        }

        // reviews 채우기 - 최대 2개
        for(Review review : club.getReviews()){
            ClubArticleReviewDto clubArticleReviewDto = buildClubArticleReviewDto(review);
            reviews.add(clubArticleReviewDto);

            if(reviews.size() > 2) break;
        }


        return ClubArticleResponseDto.builder()
                .baseInfo(baseInfoDto)
                .memberInfo(memberInfoDto)
                .qnas(qnas)
                .reviews(reviews)
                .isHost(host.getId().equals(member.getId()))
                .isHeart(heartService.getHearted(club, member))
                .build();
    }

    /**
     * 모임 상세정보에 들어갈 tag 리스트 구성
     */
    private List<String> getTagList(Club club){
        List<String> tags = new ArrayList<>();

        for(ClubCategory clubCategory : club.getCategories()){
            tags.add(clubCategory.getCategory().getName().getName());
        }

        for(ClubLanguage clubLanguage : club.getLanguages()){
            tags.add(clubLanguage.getLanguage().getName().getName());
        }

        return tags;
    }

    /**
     * 인자로 넘겨받은 Club의 호스트를 리턴한다.
     */
    private Member getHostFrom(Club club){
        List<ClubMember> members = club.getMembers();
        for(ClubMember clubMember : members){
            if(clubMember.getIsHost()){
                return clubMember.getMember();
            }
        }

        return null;
    }

    /**
     * 승인된 클럽 구성원의 국적 숫자 리턴. (한국인, 외국인)
     */
    private Map<Nationality, Integer> getNationCountFrom(Club club){
        Integer korean = 0;
        Integer foreigner = 0;
        List<ClubMember> clubMembers = club.getMembers();
        for(ClubMember clubMember : clubMembers){
            Nationality nationality = clubMember.getMember().getNationality();
            if(nationality == Nationality.KOREA) korean++;
            else if(nationality == Nationality.FOREIGN) foreigner++;
        }

        Map<Nationality, Integer> result = new HashMap<>();
        result.put(Nationality.KOREA, korean);
        result.put(Nationality.FOREIGN, foreigner);

        return result;
    }

    /**
     * qna 객체를 가지고 ClubArticleQnaDto 빌드하기
     */
    private ClubArticleQnaDto buildClubArticleDtoFrom(Qna qna){
        Member questioner = qna.getQuestioner();
        return ClubArticleQnaDto.builder()
                .questionerImageUrl(questioner.getProfileImg())
                .questionerNickname(questioner.getNickname())
                .questionContent(qna.getQuestionContent())
                .questionDate(qna.getQDate())
                .build();
    }

    /**
     * Review 객체를 가지고 ClubAtricleReviewDto 빌드하기
     */
    private ClubArticleReviewDto buildClubArticleReviewDto(Review review){
        Member reviewer = review.getMember();
        return ClubArticleReviewDto.builder()
                .reviewerImageUrl(reviewer.getProfileImg())
                .reviewerNickname(reviewer.getNickname())
                .content(review.getContent())
                .reviewDate(formatDateTime(review.getCreatedDate()))
                .build();
    }

    /**
     * 명시된 포맷으로 인자로 들어온 시간을 문자열로 리턴하는 함수
     */
    private String formatDateTime(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
    }

    /**
     * 날짜를 Mar 2 의 포맷으로 변경
     */
    private String formateDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        Month month = date.getMonth();
        String monthString;
        switch (month){
            case JANUARY:
                monthString = "Jan";
                break;
            case FEBRUARY:
                monthString = "Feb";
                break;
            case MARCH:
                monthString = "Mar";
                break;
            case APRIL:
                monthString = "Apr";
                break;
            case MAY:
                monthString = "May";
                break;
            case JUNE:
                monthString = "Jun";
                break;
            case JULY:
                monthString = "Jul";
                break;
            case AUGUST:
                monthString = "Aug";
                break;
            case SEPTEMBER:
                monthString = "Sep";
                break;
            case OCTOBER:
                monthString = "Oct";
                break;
            case NOVEMBER:
                monthString = "Nov";
                break;
            case DECEMBER:
                monthString = "Dec";
                break;
            default:
                monthString = "null";
        }

        return monthString + " " + date.getDayOfMonth();
    }
}
