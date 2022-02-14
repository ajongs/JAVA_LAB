package study.jpalab.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) //이벤트 기반으로 동작한다는 뜻
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    /*
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    */


    //등록자 수정자는 스프링 어플리케이션에 빈으로 등록 설정을 해주어야함
    // 문자열 같은 경우는 어딘선가 받아서 초기화를 시켜주어야함 -> 보통은 세션얻어와서 넣어줌

    @CreatedBy
    @Column(updatable = false)
    private String createBy;
    @LastModifiedBy
    private String lastModifiedBy;
}
