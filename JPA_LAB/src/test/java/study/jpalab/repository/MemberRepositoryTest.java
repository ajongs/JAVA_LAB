package study.jpalab.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpalab.entity.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);
        Optional<Member> byId = memberRepository.findById(savedMember.getId()); // 여기서 이제 없으면 어떻게 할껀지 다 체크해줘야
        Member findMember = byId.get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 쿼리어노테이션테스트(){
        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMember = memberRepository.findUser("bbb", 15);
        assertThat(findMember.get(0)).isEqualTo(member2);

    }
    @Test
    public void returnType(){
        //spring data jpa 는 다양한 반환타입을 제공한다.
        //Member (단건 조회), List<Member> (컬렉션 조회) , Optional<Member> (옵서녈조회)
        //없으면 각각 null 반환, 빈 컬렉션 반환, Optional.empty 반환

        //만약 데이터가 있을지 없을 지 모르겠다면 Optional 을 쓰는게 맞다.

        Member member1 = new Member("aaa", 10);
        Member member2 = new Member("bbb", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember = memberRepository.findMemberByUsername("aaa");
        assertThat(findMember).isEqualTo(member1);

        List<Member> list = memberRepository.findListByUsername("aaa");
        assertThat(list.get(0)).isEqualTo(member1);

        Optional<Member> optional = memberRepository.findOptionalByUsername("ccc");
        assertThat(optional.orElse(error())).isEqualTo(null);

    }
    public static Member error(){
        System.out.println("값이 없습니다.");
        return null;
    }
}