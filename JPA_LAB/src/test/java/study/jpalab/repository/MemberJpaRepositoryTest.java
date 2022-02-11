package study.jpalab.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpalab.entity.Member;

import java.util.List;

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

    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        List<Member> all = memberJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberJpaRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }
    @Test
    public void bulkUpdate(){
        memberJpaRepository.save(new Member("aaa", 10));
        memberJpaRepository.save(new Member("bbb", 20));
        memberJpaRepository.save(new Member("ccc", 30));
        memberJpaRepository.save(new Member("ddd", 40));
        Member m5 = new Member("eee", 50);
        memberJpaRepository.save(m5);

        int result = memberJpaRepository.bulkAgePlus(20);

        assertThat(result).isEqualTo(4);

        // 벌크성 쿼리는 1차캐시(영속성컨텍스트)가 알지못함
        assertThat(m5.getAge()).isEqualTo(50);
    }
}