package server.api.kiwes.domain.club.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.*;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.club.service.ClubSortService;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.club_member.service.ClubMemberService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.domain.qna.constant.QnaResponseType;
import server.api.kiwes.response.ApiResponse;
import server.api.kiwes.response.BizException;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Api(tags = "Club - C/U/D 관련", value = "모암 참여, 취소, 승인, 생성 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {
    private final ClubService clubService;
    private final MemberService memberService;
    private final ClubMemberService clubMemberService;
    private final ClubSortService clubSortService;

    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public static boolean isValidDateFormat(String dateStr) {
        return Pattern.matches(DATE_REGEX, dateStr);
    }

    @ApiOperation(value = "모임 글 작성", notes = "날짜 요청 형식 : YYYY-MM-DD\n location : 위도, 경도\nlocationsKeyword : 짧은 위치 키워드\nGender: MALE, FEMALE, ALL")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20101, message = "모임 모집 작성글 업로드 성공"),
            @io.swagger.annotations.ApiResponse(code = 40108, message = "날짜 요청 형식이 잘못 되었습니다. (400)"),
    })
    @PostMapping("/article")
    public ApiResponse<ClubCreatedResponseDto> postClubRecruitmentArticles(@RequestBody ClubArticleRequestDto requestDto){
        if(!isValidDateFormat(requestDto.getDate()) || !isValidDateFormat(requestDto.getDueTo()))
            throw new BizException(ClubResponseType.INVALID_DATE_FORMAT);

        Member member = memberService.getLoggedInMember();
        ClubCreatedResponseDto response =  clubService.saveNewClub(requestDto, member);

        return ApiResponse.of(ClubResponseType.POST_SUCCESS, response);
    }

    @ApiOperation(value = "모임 글 삭제", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20102, message = "모임 모집 글 삭제 성공"),
    })
    @DeleteMapping("/article/{clubId}")
    public ApiResponse<Object> deleteClubRecruitmentArticles(@PathVariable Long clubId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);

        Boolean isHost = clubMemberService.getIsHost(club, member);
        if(!isHost){
            throw new BizException(QnaResponseType.NOT_HOST);
        }

        clubService.deleteClub(club);

        return ApiResponse.of(ClubResponseType.DELETE_SUCCESS);
    }
    
    @ApiOperation(value = "모임 참여 하기", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20103, message = "참여 신청 성공"),
            @io.swagger.annotations.ApiResponse(code = 40102, message = "호스트이거나 이미 참여 신청함"),
    })
    @PostMapping("/application/{clubId}")
    public ApiResponse<Object> signUpClub(@PathVariable Long clubId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        ClubMember clubMember = clubMemberService.findByClubAndMember(club, member);
        if(clubMember != null){
            throw new BizException(ClubResponseType.ALREADY_APPLIED);
        }

        clubService.applyClub(member, club);

        return ApiResponse.of(ClubResponseType.APPLICATION_SUCCESS);
    }

    @ApiOperation(value = "모임 신청자 승인", notes = "호스트만이 가능")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20105, message = "모임 참여 승인 성공"),
            @io.swagger.annotations.ApiResponse(code = 40103, message = "호스트가 아니므로 권한 없음 (401)"),
            @io.swagger.annotations.ApiResponse(code = 40104, message = "모임에 지원한 사용자가 아님 (400)"),
    })
    @PutMapping("/application/{clubId}/{memberId}")
    public ApiResponse<ClubJoinedResponseDto> approveMember(@PathVariable Long clubId, @PathVariable Long memberId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        if(!clubMemberService.findByClubAndMember(club, member).getIsHost()){
            throw new BizException(ClubResponseType.NOT_HOST);
        }

        Member applicant = memberService.findById(memberId);
        ClubMember clubMember = clubMemberService.findByClubAndMember(club, applicant);
        if(clubMember == null){
            throw new BizException(ClubResponseType.NOT_APPLIED);
        }

        ClubJoinedResponseDto response = clubService.approveMember(clubMember, club);

        return ApiResponse.of(ClubResponseType.APPROVE_SUCCESS, response);
    }

    @ApiOperation(value = "모임 신청 거절(삭제)", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20106, message = "신청자 거절(삭제) 성공"),
            @io.swagger.annotations.ApiResponse(code = 40103, message = "호스트가 아니므로 권한 없음 (401)"),
            @io.swagger.annotations.ApiResponse(code = 40104, message = "모임에 지원한 사용자가 아님 (400)"),
    })
    @DeleteMapping("/application/{clubId}/{memberId}")
    public ApiResponse<Object> denyMember(@PathVariable Long clubId, @PathVariable Long memberId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        if(!clubMemberService.findByClubAndMember(club, member).getIsHost()){
            throw new BizException(ClubResponseType.NOT_HOST);
        }

        Member applicant = memberService.findById(memberId);
        ClubMember clubMember = clubMemberService.findByClubAndMember(club, applicant);
        if(clubMember == null){
            throw new BizException(ClubResponseType.NOT_APPLIED);
        }

        clubService.denyMember(clubMember);

        return ApiResponse.of(ClubResponseType.DENY_SUCCESS);
    }

    @ApiOperation(value = "참여 취소 (지원자)", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20104, message = "참여취소 성공"),
            @io.swagger.annotations.ApiResponse(code = 40107, message = "호스트는 참여 취소를 할 수 없습니다 (400)"),
            @io.swagger.annotations.ApiResponse(code = 40104, message = "모임에 지원한 사용자가 아님 (400)"),
    })
    @DeleteMapping("/application/{clubId}")
    public ApiResponse<Object> cancelApplication(@PathVariable Long clubId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        ClubMember clubMember = clubMemberService.findByClubAndMember(club, member);
        if(clubMember == null){
            throw new BizException(ClubResponseType.NOT_APPLIED);
        }

        if(clubMember.getIsHost()){
            throw new BizException(ClubResponseType.HOST_CANNOT_CANCEL);
        }

        clubService.cancelApplication(clubMember);

        return ApiResponse.of(ClubResponseType.WITHDRAWAL_SUCCESS);
    }

    //호스트가 승인된 지원자 강퇴
    @ApiOperation(value = "승인된 사용자 모임에서 강퇴 (호스트)", notes = "호스트만 요청 가능")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20107, message = "멤버 강퇴 성공"),
            @io.swagger.annotations.ApiResponse(code = 40103, message = "호스트가 아니므로 권한 없음 (401)"),
            @io.swagger.annotations.ApiResponse(code = 40104, message = "모임에 지원한 사용자가 아님 (400)"),
    })
    @DeleteMapping("/kick/{clubId}/{memberId}")
    public ApiResponse<Object> kickMember(@PathVariable Long clubId, @PathVariable Long memberId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        Member applicant = memberService.findById(memberId);

        if(Objects.equals(member.getId(), memberId)){
            throw new BizException(ClubResponseType.HOST_CANNOT_CANCEL);
        }

        ClubMember clubApplicant = clubMemberService.findByClubAndMember(club, applicant);
        if(clubApplicant == null){
            throw new BizException(ClubResponseType.NOT_APPLIED);
        }

        ClubMember clubHost = clubMemberService.findByClubAndMember(club, member);
        if(!clubHost.getIsHost()){
            throw new BizException(ClubResponseType.NOT_HOST);
        }

        clubService.kickMember(clubApplicant, club);
        return ApiResponse.of(ClubResponseType.KICK_OUT_SUCCESS);
    }

    @ApiOperation(value = "카테고리별 모임", notes = "카테고리별 모임 조회")
    @PostMapping("/category")
    public ApiResponse<List<ClubSortResponseDto>> sortByCategories(@RequestBody ClubSortRequestDto clubSortRequestDto ) {
        return ApiResponse.of(ClubResponseType.CLUB_SORT_BY_CATEGORY_SUCCESS,
                clubSortService.getClubByCategory(clubSortRequestDto.getSortedBy()));
    }

    @ApiOperation(value = "언어별 모임", notes = "언어별 모임 조회")
    @PostMapping("/language")
    public ApiResponse<List<ClubSortResponseDto>> sortByLanguages(@RequestBody ClubSortRequestDto clubSortRequestDto ) {
        return ApiResponse.of(ClubResponseType.CLUB_SORT_BY_LANGUAGE_SUCCESS,
                clubSortService.getClubByLanguages(clubSortRequestDto.getSortedBy()));
    }

    @ApiOperation(value = "추천 모임", notes = "추천 모임 조회")
    @PostMapping("/recommend")
    public ApiResponse<List<ClubSortResponseDto>> sortByRecommend(@RequestBody ClubSortRequestDto clubSortRequestDto ) {
        return ApiResponse.of(ClubResponseType.CLUB_SORT_BY_RECOMMEND_SUCCESS,
                clubSortService.getClubByRecommend());
    }

}
