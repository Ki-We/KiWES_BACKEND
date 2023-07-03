package server.api.kiwes.domain.club.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.ClubApprovalResponseDto;
import server.api.kiwes.domain.club.service.ClubApprovalService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.response.ApiResponse;

@Api(tags = "Club - 승인 대기, 승인 요청 리스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/club/approval")
public class ClubApprovalController {
    private final MemberService memberService;
    private final ClubApprovalService clubApprovalService;

    @ApiOperation(value = "승인 요청, 대기 모임 각각 상위 2개씩", notes = "승인의 첫 페이지에 개략적으로 보여 줄 두개의 모임")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 20109, message = "승인관련 리스트 리턴 성공"),
    })
    @GetMapping("/simple")
    public ApiResponse<Object> getSimpleApproval(){
        Member member = memberService.getLoggedInMember();
        ClubApprovalResponseDto response = clubApprovalService.getSimpleResponse(member);

        return ApiResponse.of(ClubResponseType.APPROVAL_LIST_GET_SUCCEED, response);
    }


}