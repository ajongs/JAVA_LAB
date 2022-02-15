package study.jpalab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.jpalab.entity.Member;
import study.jpalab.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init(){
        memberRepository.save(new Member("userA"));
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
}

