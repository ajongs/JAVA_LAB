package study.jpalab.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter //엔티티에 세터는 없는게 국룰
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;

    protected Member() {  //아무데서나 호출되지 못하게
    }//jpa 엔티티는 기본생성자 필요로함 왜??? 알아보자
    //프록시 기술을 사용하는데 그러기 위해서는 상속이 되어야지 그래서 private는 사용못함

    public Member(String username) {
        this.username = username;
    }
}
