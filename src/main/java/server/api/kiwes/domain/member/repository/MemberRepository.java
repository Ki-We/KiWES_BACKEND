package server.api.kiwes.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.api.kiwes.domain.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    @Query("select m from Member m where m.nickname = :nickname and m.isDeleted = false")
    Optional<Member> findNotDeletedByNickname(@Param("nickname") String nickname);

    @Query("select m from Member m where m.nickname = :email and m.isDeleted = false")
    Optional<Member> findNotDeletedByEmail(@Param("email") String email);


}
