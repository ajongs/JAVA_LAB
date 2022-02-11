package study.jpalab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.jpalab.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username = :username and m.age > :age")
    public List<Member> findUser(@Param("username") String username, @Param("age") int age);

    List<Member> findListByUsername(String username);
    Member findMemberByUsername(String username);
    Optional<Member> findOptionalByUsername(String username);

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);


    //modifying : executeQuery 로 쿼리 제출 후 반환, 아니면 getSingleResult 나 getResultList 로 가져와버림
    @Modifying() //영속성 컨텍스트를 clear 할 수 있는 옵션을 제공함 (직접 코드로 작성할 필요없음)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    List<Member> findByUsername(String username);
}
