package study.jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.jpalab.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
