package server.api.kiwes.domain.qna.controller;

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
import server.api.kiwes.domain.qna.constant.QnaResponseType;
import server.api.kiwes.domain.qna.dto.QnaRequestDto;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.qna.service.QnaService;
import server.api.kiwes.response.ApiResponse;
import server.api.kiwes.response.BizException;

@Api(tags = "Club - Q&A")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qna")
public class QnaController {
    private final QnaService qnaService;
    private final MemberService memberService;
    private final ClubService clubService;
    private final ClubMemberService clubMemberService;
    
    @ApiOperation(value = "qna 질문 등록", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21101, message = "qna 질문 등록 성공"),
            @io.swagger.annotations.ApiResponse(code = 40101, message = "clubId와 일치하는 모임이 존재하지 않습니다. (404)"),
    })
    @PostMapping("/question/{clubId}")
    public ApiResponse<Object> postQuestion(@PathVariable Long clubId, @RequestBody QnaRequestDto requestDto){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);
        qnaService.postQuestion(club, member, requestDto);

        return ApiResponse.of(QnaResponseType.Q_POST_SUCCESS);
    }

    @ApiOperation(value = "qna 답변 등록", notes = "")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21102, message = "qna 답변 등록 성공"),
            @io.swagger.annotations.ApiResponse(code = 40101, message = "clubId와 일치하는 모임이 존재하지 않습니다. (404)"),
            @io.swagger.annotations.ApiResponse(code = 41101, message = "qnaID와 일치하는 QnA가 존재하지 않습니다. (404)"),
            @io.swagger.annotations.ApiResponse(code = 41102, message = "기답변된 QnA입니다. (400)"),
            @io.swagger.annotations.ApiResponse(code = 41103, message = "로그인한 사용자가 호스트가 아닙니다. (400)"),
            @io.swagger.annotations.ApiResponse(code = 41105, message = "삭제된 질문입니다. (400)"),
    })
    @PostMapping("/answer/{clubId}/{qnaId}")
    public ApiResponse<Object> postAnswer(@PathVariable Long clubId, @PathVariable Long qnaId, @RequestBody QnaRequestDto requestDto){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);

        Boolean isHost = clubMemberService.getIsHost(club, member);
        if(!isHost){
            throw new BizException(QnaResponseType.NOT_HOST);
        }

        Qna qna = qnaService.findById(qnaId);
        qnaService.postAnswer(member, qna, requestDto);
        return ApiResponse.of(QnaResponseType.A_POST_SUCCESS);
    }
    
    @ApiOperation(value = "qna 질문 삭제", notes = "삭제된 질문입니다 텍스트로 변환")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21103, message = "qna 질문 삭제 성공"),
            @io.swagger.annotations.ApiResponse(code = 41104, message = "작성자가 아닙니다. (401)"),
    })
    @DeleteMapping("/question/{clubId}/{qnaId}")
    public ApiResponse<Object> deleteQusetion(@PathVariable Long clubId, @PathVariable Long qnaId){
        Member member = memberService.getLoggedInMember();
        Qna qna = qnaService.findById(qnaId);

        qnaService.deleteQuestion(member, qna);
        return ApiResponse.of(QnaResponseType.Q_DELETE_SUCCESS);

    }

    @ApiOperation(value = "qna 답변 삭제", notes = "답변 삭제")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 21104, message = "qna 답변 삭제 성공"),
            @io.swagger.annotations.ApiResponse(code = 41103, message = "로그인한 사용자가 호스트가 아닙니다. (400)"),
    })
    @DeleteMapping("/answer/{clubId}/{qnaId}")
    public ApiResponse<Object> deleteAnswer(@PathVariable Long clubId, @PathVariable Long qnaId){
        Member member = memberService.getLoggedInMember();
        Club club = clubService.findById(clubId);

        Boolean isHost = clubMemberService.getIsHost(club, member);
        if(!isHost){
            throw new BizException(QnaResponseType.NOT_HOST);
        }

        Qna qna = qnaService.findById(qnaId);
        qnaService.deleteAnswer(member, qna);

        return ApiResponse.of(QnaResponseType.A_DELETE_SUCCESS);

    }


}
