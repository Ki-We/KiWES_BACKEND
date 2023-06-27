package server.api.kiwes.domain.category.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.api.kiwes.domain.club_category.entity.ClubCategory;
import server.api.kiwes.domain.member_category.entity.MemberCategory;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Category {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<ClubCategory> clubCategories;

    @OneToMany(mappedBy = "category")
    private List<MemberCategory> memberCategories;


}
