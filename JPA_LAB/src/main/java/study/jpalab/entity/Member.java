package study.jpalab.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter //엔티티에 세터는 없는게 국룰
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자 만드는 롬복 어노테이션
@ToString(of = {"id", "username", "age"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    /*
    protected Member() {  //아무데서나 호출되지 못하게
    }//jpa 엔티티는 기본생성자 필요로함 왜??? 알아보자
    //프록시 기술을 사용하는데 그러기 위해서는 상속이 되어야지 그래서 private는 사용못함
    */

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if(team!=null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
