package server.api.kiwes.domain.qna.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnaResponseDto {
    Boolean isHost;
    List<QnaDetailDto> qnas;
}
