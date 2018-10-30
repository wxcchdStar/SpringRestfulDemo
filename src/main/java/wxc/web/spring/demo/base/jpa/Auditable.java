package wxc.web.spring.demo.base.jpa;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 通用数据库操作统计
 *
 * @author chenhd
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<T> {
  
  @Column(nullable = false, updatable = false)
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createAt;
  
  @Column(nullable = false, updatable = false)
  @CreatedBy
  private T createBy;
  
  @Column(nullable = false)
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateAt;
  
  @Column(nullable = false)
  @LastModifiedBy
  private T updateBy;
  
}
