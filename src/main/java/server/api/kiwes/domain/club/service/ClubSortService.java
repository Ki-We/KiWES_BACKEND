package server.api.kiwes.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.category.repository.CategoryRepository;
import server.api.kiwes.domain.category.type.CategoryType;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.entity.Club;
import server.api.kiwes.domain.club.repository.ClubRepository;
import server.api.kiwes.domain.club_category.entity.ClubCategory;
import server.api.kiwes.domain.club_category.repository.ClubCategoryRepository;
import server.api.kiwes.response.BizException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubSortService {

    private final ClubRepository clubRepository;
    private final ClubCategoryRepository clubCategoryRepository;
    private final CategoryRepository categoryRepository;

    /**
     * club id를 통해 club 정보 불러오기
     */
    public Club findById(Long id){
        return clubRepository.findById(id)
                .orElseThrow(() -> new BizException(ClubResponseType.CLUB_NOT_EXIST));
    }

    /**
     * 카테고리별 모임
     * @param categories
     */

    public List<Club> getClubByCategory(List<String> categories){

        List<Club> club = new ArrayList<>();

        for (String categoryString : categories) {
//        String categoryString = categories.get(0);
            System.out.println("categoryString = " + categoryString);
            CategoryType type = CategoryType.valueOf(categoryString);
            Category category = categoryRepository.findByName(type);

            club.addAll(clubCategoryRepository.findAllByCategoryId(category.getId()));
        }

        return club;

    }


}
