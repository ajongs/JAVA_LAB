package study.jpalab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.jpalab.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> { //<해당 엔티티, pk 타입>

}
