package study.jpalab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // auditing 기능을 사용하려면 (@CreatedDate, @LastModifiedDate 등) 이 어노테이션이 필요함
@SpringBootApplication
public class JpaLabApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaLabApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorProvider(){
        return () -> Optional.of(UUID.randomUUID().toString());
        // 보통은 세션정보를 가져와서 getUserId를 꺼내온다.
        // @CreatedBy, @LastModifiedBy 에 String 값을 여기서 설정해준 값으로 들어간다.
    }
    //람다 복습 : 함수형 인터페이스(메소드 한개만 가지고 있는 인터페이스)는 람다로 표현가능
    //람다로 메소드를 정의하면 정의 된 메소드를 담은 인터페이스의 인스턴스를 만들어서 return 한다.
}
