package server.api.kiwes.domain.club.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.ClubArticleResponseDto;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.service.ClubDetailService;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.club_member.service.ClubMemberService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.response.ApiResponse;

@Api(tags = "Club - Read 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club/info")
public class ClubDetailController {
    private final ClubService clubService;
    private final MemberService memberService;
    private final ClubDetailService clubDetailService;
    
    @ApiOperation(value = "모임 상세 정보 불러오기", notes = "모임 정보 페이지에서 활용\n, qna, review는 최대 2개까지")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20108, message = "모임 정보 불러오기 성공")
    })
    @GetMapping("/{clubId}")
    public ApiResponse<ClubArticleResponseDto> getClubDetail(@PathVariable Long clubId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        ClubArticleResponseDto response = clubDetailService.getClubDetail(member, club);

        return ApiResponse.of(ClubResponseType.GET_INFO_SUCCESS, response);
    }
}
