package study.jpalab.repository;

import lombok.RequiredArgsConstructor;
import study.jpalab.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor  //private final 생성자 의존성 주입 자동으로 됨
public class MemberRepositoryImpl implements MemberRepositoryCustom{
    // 커스텀 리파지토리를 구현한 클래스는 jpa 리파지토리 인터페이스를 구현한 인터페이스 이름 + "impl" 을 넣어주어야함.

    // 이렇게 커스텀을 사용하는 경우는 보통 QueryDsl 을 사용하거나, 순수 jpa, jdbcTemplate 등을 사용할 때 주로 사용된다.
    // 히지만 핵심 비지니스 로직에서 사용하는 쿼리와 단순하게 화면하게 맞춘 쿼리랑(dto 뽑아서 복잡한 통계성 쿼리) 분리할 필요가 있을때도있다. (항상 커스텀 하면안댐)
    // 왜냐면 커스텀은 결국 MemberRepository 인터페이스에 다 포함되는것이라 분리되는 것이 아님 -> 인터페이스가 더 크고 복잡해짐

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
