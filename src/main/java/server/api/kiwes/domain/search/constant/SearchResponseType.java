package server.api.kiwes.domain.search.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import server.api.kiwes.response.BaseResponseType;

@Getter
@RequiredArgsConstructor
public enum SearchResponseType implements BaseResponseType {
    SEARCH_SUCCESS(21301, "검색 결과 응답 성공", HttpStatus.OK),
    ;

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
