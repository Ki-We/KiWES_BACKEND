package server.api.kiwes.domain.club.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClubStatus {
    YES("YES"),
    NO("N");

    private final String status;
}
