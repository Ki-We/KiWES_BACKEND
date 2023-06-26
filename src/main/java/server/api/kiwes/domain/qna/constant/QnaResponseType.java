package server.api.kiwes.domain.qna.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@RequiredArgsConstructor
public enum QnaResponseType implements BaseResponseType {

    POST_SUCCESS(21101, "qna 질문 등록 성공", HttpStatus.OK),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
