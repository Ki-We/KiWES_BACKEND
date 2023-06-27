package server.api.kiwes.domain.review.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.club_member.service.ClubMemberService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.domain.review.constant.ReviewResponseType;
import server.api.kiwes.domain.review.dto.ReviewRegisterDto;
import server.api.kiwes.domain.review.entity.Review;
import server.api.kiwes.domain.review.service.ReviewService;
import server.api.kiwes.response.ApiResponse;
import server.api.kiwes.response.BizException;

@Api(tags = "Review")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final ClubMemberService clubMemberService;
    private final ClubService clubService;

    @ApiOperation(value = "후기 등록", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21201, message = "후기 등록 완료"),
            @io.swagger.annotations.ApiResponse(code = 41201, message = "해당 모임에 승인된 멤버가 아님 (400)"),
    })
    @PostMapping("/{clubId}")
    public ApiResponse<Object> postReview(@RequestBody ReviewRegisterDto registerDto, @PathVariable Long clubId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        Boolean isApproved = clubMemberService.getIsApproved(club, member);
        if(!isApproved){
            throw new BizException(ReviewResponseType.NOT_CLUB_MEMBER);
        }

        reviewService.postReview(club, member, registerDto);

        return ApiResponse.of(ReviewResponseType.POST_SUCCESS);
    }
}
