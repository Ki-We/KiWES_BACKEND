package server.api.kiwes.global.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    ALL("ALL"),
    ;

    private final String name;
}
