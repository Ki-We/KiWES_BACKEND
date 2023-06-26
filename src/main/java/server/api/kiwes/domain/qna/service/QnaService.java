package server.api.kiwes.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.qna.constant.QnaAnsweredStatus;
import server.api.kiwes.domain.qna.constant.QnaDeletedStatus;
import server.api.kiwes.domain.qna.constant.QnaResponseType;
import server.api.kiwes.domain.qna.dto.QnaRequestDto;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.qna.repository.QnaRepository;
import server.api.kiwes.response.BizException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    /**
     * qnaID를 통해 QnA 객체 반환
     */
    public Qna findById(Long id){
        return qnaRepository.findById(id)
                .orElseThrow(() -> new BizException(QnaResponseType.QNA_NOT_EXIST));
    }

    /**
     * qna에 문의 등록
     */
    public void postQuestion(Club club, Member member, QnaRequestDto requestDto) {
        qnaRepository.save(Qna.builder()
                .questionContent(requestDto.getContent())
                .questioner(member)
                .club(club)
                .qDate(getDateTime())
                .build());
    }


    /**
     * 기존재하는 qna에 답변 등록
     * 사용자가 모임의 호스트인지 여부는 컨트롤러에서 이미 검사를 마침
     */
    public void postAnswer(Member member, Qna qna, QnaRequestDto requestDto) {
        if (qna.getIsDeleted() == QnaDeletedStatus.YES){
            throw new BizException(QnaResponseType.QUESTION_DELETED);
        }

        // qna가 답변이 된 상태라면, 예외 발생. PUT 요청을 하게끔
        if(qna.getIsAnswered() == QnaAnsweredStatus.YES){
            throw new BizException(QnaResponseType.ALREADY_ANSWERED);
        }

        qna.setAnswerContent(requestDto.getContent());
        qna.setADate(getDateTime());
        qna.setRespondent(member);
        qna.setIsAnswered(QnaAnsweredStatus.YES);

    }

    /**
     * 질문 작성자와 API 요청자가 일치할 경우,
     * 질문 내용을 "삭제된 질문입니다" 로 변경한다.
     */
    public void deleteQuestion(Member member, Qna qna) {
        if (qna.getIsDeleted() == QnaDeletedStatus.YES){
            throw new BizException(QnaResponseType.QUESTION_DELETED);
        }

        if(!Objects.equals(member.getId(), qna.getQuestioner().getId())){
            throw new BizException(QnaResponseType.UNAUTHORIZED);
        }

        qna.setQuestionContent("삭제된 질문입니다.");
        qna.setIsDeleted(QnaDeletedStatus.YES);
    }


    /**
     * 명시된 포맷으로 현재 시간을 문자열로 리턴하는 함수
     */
    private String getDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
    }

}
