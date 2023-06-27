package server.api.kiwes.domain.club_language.entity;

import lombok.Getter;
import lombok.Setter;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.language.entity.Language;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ClubLanguage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLUB_LANGUAGE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "LANGUAGE_ID")
    private Language language;

}
