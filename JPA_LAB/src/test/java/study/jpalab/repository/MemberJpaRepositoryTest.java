package study.jpalab.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpalab.entity.Member;

import static org.assertj.core.api.Assertions.*;

@Transactional //jpa의 모든 번경은 트랜잭션 안에서 이루어져야한다.
@Rollback(false) //스프링 테스트가 transactional 어노테이션이 있으면 끝날때 롤백을 시켜버림 그래서 false 해주어야 결과를 뽈수있음
@SpringBootTest //스프링 컨테이너 사용하겠다
    //runwith 어노테이션은 junit5부터 없어도 괜찮음
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        //member.setUsername("memberA"); //세터보다는 생성자에서 하는게 더 나은 방법
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member); //findMember == member
        // 한트랜잭션안에서 영속성 컨텍스트의 동률성을 보장함(같은 데이터에 대해 같은 인스턴스 보장)
        // 이는 1차캐시 기능이라고도 부른다, 당연히 트랜잭션 다르면 다른 인스턴스
    }
}