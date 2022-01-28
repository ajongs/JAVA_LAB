package study.jpalab.repository;

import org.springframework.stereotype.Repository;
import study.jpalab.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberJpaRepository {

    @PersistenceContext  //스프링컨테이너가 영속성 컨텍스트에서 em 주입
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }
    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
