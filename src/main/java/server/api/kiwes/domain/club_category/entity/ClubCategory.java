package server.api.kiwes.domain.club_category.entity;

import lombok.Getter;
import lombok.Setter;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.club.entity.Club;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ClubCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLUB_CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

}
