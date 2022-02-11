package study.jpalab.dto;

import lombok.Data;
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
}
