package server.api.kiwes.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.repository.ClubRepository;
import server.api.kiwes.domain.member.entity.Member;
import server.api.kiwes.domain.qna.dto.QnaQuestionRequestDto;
import server.api.kiwes.domain.qna.entity.Qna;
import server.api.kiwes.domain.qna.repository.QnaRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    /**
     * qna에 문의 등록
     */
    public void postQuestion(Club club, Member member, QnaQuestionRequestDto requestDto) {
        qnaRepository.save(Qna.builder()
                .questionContent(requestDto.getContent())
                .questioner(member)
                .club(club)
                .qDate(getDateTime())
                .build());
    }

    /**
     * 명시된 포맷으로 현재 시간을 문자열로 리턴하는 함수
     */
    private String getDateTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
    }
}
