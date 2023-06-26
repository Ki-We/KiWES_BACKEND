package server.api.kiwes.domain.club.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.ClubArticleRequestDto;
import server.api.kiwes.domain.club.dto.ClubIdResponseDto;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.response.ApiResponse;

@Api(tags = "Club")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club")
public class ClubController {
    private final ClubService clubService;
    private final MemberService memberService;

    @ApiOperation(value = "모임 글 작성", notes = "날짜 요청 형식 : YYYYMMDD\n location : 위도, 경도\nlocationsKeyword : 짧은 위치 키워드")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20101, message = "모임 모집 작성글 업로드 성공"),
    })
    @PostMapping("/article")
    public ApiResponse<ClubIdResponseDto> postClubRecruitmentArticles(@RequestBody ClubArticleRequestDto requestDto){
        Member member = memberService.getLoggedInMember();
        Long clubId = clubService.saveNewClub(requestDto, member);

        return ApiResponse.of(ClubResponseType.POST_SUCCESS, new ClubIdResponseDto(clubId));
    }
}
