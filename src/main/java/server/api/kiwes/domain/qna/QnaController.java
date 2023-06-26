package server.api.kiwes.domain.qna;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.service.ClubService;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.member.service.MemberService;
import server.api.kiwes.domain.qna.constant.QnaResponseType;
import server.api.kiwes.domain.qna.dto.QnaQuestionRequestDto;
import server.api.kiwes.domain.qna.service.QnaService;
import server.api.kiwes.response.ApiResponse;

@Api(tags = "Club-Q&A")
@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QnaService qnaService;
    private final MemberService memberService;
    private final ClubService clubService;
    
    @ApiOperation(value = "qna 질문 등록", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21101, message = "qna 질문 등록 성공"),
            @io.swagger.annotations.ApiResponse(code = 40101, message = "clubId와 일치하는 모임이 존재하지 않습니다. (404)"),
    })
    @PostMapping("/question/{clubId}")
    public ApiResponse<Object> postQuestion(@PathVariable Long clubId, @RequestBody QnaQuestionRequestDto requestDto){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        qnaService.postQuestion(club, member, requestDto);

        return ApiResponse.of(QnaResponseType.POST_SUCCESS);
    }
}
