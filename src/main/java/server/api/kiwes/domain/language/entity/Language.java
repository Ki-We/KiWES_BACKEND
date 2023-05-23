package server.api.kiwes.domain.language.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.member.entity.Member;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Language {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "LANGUAGE_ID")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "languages")
    private List<Club> clubs;

    @ManyToMany(mappedBy = "languages")
    private List<Member> members;
}
