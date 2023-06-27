package server.api.kiwes.domain.club.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.ClubArticleRequestDto;
import server.api.kiwes.domain.club.dto.ClubIdResponseDto;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.club_member.entity.ClubMember;
import server.api.kiwes.domain.club_member.service.ClubMemberService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.domain.qna.constant.QnaResponseType;
import server.api.kiwes.response.ApiResponse;
import server.api.kiwes.response.BizException;

@Api(tags = "Club")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {
    private final ClubService clubService;
    private final MemberService memberService;
    private final ClubMemberService clubMemberService;

    @ApiOperation(value = "모임 글 작성", notes = "날짜 요청 형식 : YYYYMMDD\n location : 위도, 경도\nlocationsKeyword : 짧은 위치 키워드\nGender: MALE, FEMALE, ALL")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20101, message = "모임 모집 작성글 업로드 성공"),
    })
    @PostMapping("/article")
    public ApiResponse<ClubIdResponseDto> postClubRecruitmentArticles(@RequestBody ClubArticleRequestDto requestDto){
        Member member = memberService.getLoggedInMember();
        Long clubId = clubService.saveNewClub(requestDto, member);

        return ApiResponse.of(ClubResponseType.POST_SUCCESS, new ClubIdResponseDto(clubId));
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
    public ApiResponse<Object> approveMember(@PathVariable Long clubId, @PathVariable Long memberId){
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

        clubService.approveMember(clubMember);

        return ApiResponse.of(ClubResponseType.APPROVE_SUCCESS);
    }

    @ApiOperation(value = "모임 신청 거절(삭제)", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20106, message = "신청자 거절(삭제) 성공"),
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
}
