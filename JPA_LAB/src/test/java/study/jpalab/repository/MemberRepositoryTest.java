package study.jpalab.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.jpalab.dto.MemberDto;
import study.jpalab.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.plaf.metal.MetalMenuBarUI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @PersistenceContext
    EntityManager entityManager;

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
    @Test
    public void pageTest(){
        memberRepository.save(new Member("aaa", 10));
        memberRepository.save(new Member("bbb", 10));
        memberRepository.save(new Member("ccc", 10));
        memberRepository.save(new Member("ddd", 10));
        memberRepository.save(new Member("eee", 10));
        memberRepository.save(new Member("fff", 10));
        memberRepository.save(new Member("ggg", 10));

        //페이징 관련 반환 타입 3가지
        //Page, Slice, List

        PageRequest pageRequest = PageRequest.of(2, 3, Sort.by(Sort.Direction.ASC, "username"));
        Page<Member> page = memberRepository.findByAge(10, pageRequest);
        //Page 반환타입은 count 쿼리까지 같이 날린다
        //Page 의 시작인덱스는 0이다. 조심하자
        //Page 를 잘안쓰려는 이유 : TotalCount 를 사용하므로 데이터베이스 전체를 훑어야해서 성능이 안나올수 있음
        //count 쿼리를 날릴때는 굳이 조인쿼리가 필요없음 ex) Member 토탈 카운트 체크시 team 과 조인할 필요없음
        //따라서 count 쿼리는 데이터 쿼리와 다르게 분리를 해줄 필요가 있다.
        //@Query 를 사용해서 분리 가능 --> MemberRepository 참고

        //Member 객체를 그대로 api 로 반환하면 안된다.
        //entity 는 외부에 노출하면 안되고 dto 로 변환해 사용해야함.
        //entity 는 자주 변경될 수 있으니 entity 를 그대로 반환 했을 때 api스펙이 바뀔 수 있음
        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        //Slice<Member> page = memberRepository.findByAge(10, pageRequest);
        //Slice는 Page의 상위 인터페이스
        //totalElement(count)를 가져오지 않고 내가 설정한 사이즈에 1을 더해서 뒤 페이지가 더 존재하는지만 체크


        //List<Member> page = memberRepository.findByAge(10, pageRequest);
        //List는 다음 페이지에 대한 정보가 필요없고 limit 기능만 사용할때 사용

        List<Member> content = page.getContent();
        long count = page.getTotalElements();

        assertThat(count).isEqualTo(7); // 총 요소 개수   getTotalElements --> Slice에는 없는기능
        assertThat(content.size()).isEqualTo(1); //페이지 안의 컨텐트 개수
        assertThat(page.getNumber()).isEqualTo(2); //페이지 번호구하기
        assertThat(page.getTotalPages()).isEqualTo(3); // Slice에는 없는 기능
        assertThat(page.isFirst()).isEqualTo(false); //첫번째 페이지 인가?
        assertThat(page.hasNext()).isEqualTo(false); //다음페이지가 있는가?

    }
    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("aaa", 10));
        memberRepository.save(new Member("bbb", 20));
        memberRepository.save(new Member("ccc", 30));
        memberRepository.save(new Member("ddd", 40));
        memberRepository.save(new Member("eee", 50));

        int result = memberRepository.bulkAgePlus(20);
        List<Member> members = memberRepository.findByUsername("eee");
        Member m5 = members.get(0);

        assertThat(result).isEqualTo(4);

        // 벌크성 쿼리는 1차캐시(영속성컨텍스트)가 알지못함
        assertThat(m5.getAge()).isEqualTo(50);

        entityManager.clear(); //@Modifying(
        assertThat(m5.getAge()).isEqualTo(51);

    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        //given
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);
        member.setUsername("member2");

        entityManager.flush(); //@PreUpdate
        entityManager.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();
        //원래는 바로 get 해서 엔티티 객체를 꺼내면 안댐, 공부편의성을 위해서만 사용하도록하자

        //then
        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getLastModifiedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreateBy() = " + findMember.getCreateBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }
}