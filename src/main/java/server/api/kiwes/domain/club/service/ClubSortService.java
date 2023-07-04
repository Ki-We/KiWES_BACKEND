package server.api.kiwes.domain.club.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.api.kiwes.domain.category.entity.Category;
import server.api.kiwes.domain.category.repository.CategoryRepository;
import server.api.kiwes.domain.category.type.CategoryType;
import server.api.kiwes.domain.club.constant.ClubResponseType;
import server.api.kiwes.domain.club.dto.ClubSortResponseDto;
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

    public List<ClubSortResponseDto> getClubByCategory(List<String> categories){

        List<ClubSortResponseDto> club = new ArrayList<>();
        List<Long> clubIds = new ArrayList<>();

        for (String categoryString : categories) {
            CategoryType type = CategoryType.valueOf(categoryString);
            Category category = categoryRepository.findByName(type);
            clubIds.add(category.getId());

        }

        club.addAll(clubCategoryRepository.findAllByCategoryIds(clubIds));


        return club;

    }


}
