package study.jpalab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.jpalab.dto.MemberDto;
import study.jpalab.entity.Member;
import study.jpalab.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){ //테스트 데이터 용도 메소드
        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i, i));
        }
    }

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get(); //get 으로 받으면 안댐..
        return member.getUsername();
    }

    @GetMapping("/converter/members/{id}")
    public String findMemberByConverter(@PathVariable("id") Member member){
        return member.getUsername();
    }
    // 도메인 클래스 컨버터는 pk를 파라미터로 받아올때 내부적으로 member 을 바로 찾아올 수 있음.

    //http://localhost:8080/members?page=0&size=3&sort=id,desc
    // 위의 형식으로 해당 api 요청을 할 수 있음
    // spring 이 내부적으로 메소드가 파라미터로 Pageable 인터페이스를 사용하면
    // PageRequest 클래스 객체를 만들어 주입해준다.
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size=5, sort="username",
    direction = Sort.Direction.DESC) Pageable pageable){
        // return memberRepository.findAll(pageable).map(member -> new MemberDto(member.getId(), member.getUsername(), null));
        // return memberRepository.findAll(pageable).map(member->new MemberDto(member));
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }

    //접두사
    //한 페이지에서 여러개 항목으로 페이징 되는 경우
    //@Qualifier 에 적혀져 있는 접두사를 api 요청 시 적어 준다. " (접두사명)_page=1 "
    // ex) /members?=member_page=0&order_page=1
    public String list(@Qualifier("member") Pageable memberPageable,
                       @Qualifier("order") Pageable orderPageable){
        return null;
    }

    //위의 api 는 인덱스가 0부터 시작하는데
    // 이것을 바꾸러면 1. Page 와 Pageable 을 직접 커스텀하게 구현하는 방법
    //             2. yml 에서 one-indexed-parameters : true 로 설정
    // 2번 방법시 주의할 점 : page 인덱스를 단순히 -1만 해주고, page 와 pageable 은 기존 -1하기전의 인덱스를 이용하여 표현을 함
    // 따라서 실제 page 요청하는 인덱스와 내부적으로 계산되는 인덱스가 차이가 나서 오류를 낼 수 있음.
    // 가장 좋은 방법은 index 를 그냥 0부터 사용하는 것 (어차피 서버의 규약은 정하기 나름이니)

}

