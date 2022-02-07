package study.jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.jpalab.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.username = :username and m.age > :age")
    public List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
