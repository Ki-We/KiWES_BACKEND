package server.api.kiwes.domain.qna.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@RequiredArgsConstructor
public enum QnaResponseType implements BaseResponseType {

    Q_POST_SUCCESS(21101, "qna 질문 등록 성공", HttpStatus.OK),
    A_POST_SUCCESS(21102, "qna 답변 등록 성공", HttpStatus.OK),

    QNA_NOT_EXIST(41101, "qnaID와 일치하는 QnA가 존재하지 않음", HttpStatus.NOT_FOUND),
    ALREADY_ANSWERED(41102, "기답변된 QnA입니다.", HttpStatus.BAD_REQUEST),
    NOT_HOST(41103, "로그인한 사용자가 호스트가 아닙니다.", HttpStatus.BAD_REQUEST),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
