package study.jpalab.dto;

import lombok.Data;
import study.jpalab.entity.Member;
import study.jpalab.entity.Team;

@Data
public class MemberDto {
    private long id;
    private String username;
    private Team team;

    public MemberDto(long id, String username, Team team) {
        this.id = id;
        this.username = username;
        this.team = team;
    }

    //Member 타입이 필드로 추가되면 안되지만
    //파라미터로 받는 것은 괜찮음
    //하지만 Member(entity)는 MemberDto 를 바라보고 있으면 안됨.
    public MemberDto(Member member){
        this.id = member.getId();
        this.username = member.getUsername();
    }
}
