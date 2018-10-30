package wxc.web.spring.demo.module.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wxc.web.spring.demo.base.jpa.Auditable;
import wxc.web.spring.demo.module.role.entity.Role;

import javax.persistence.*;

/**
 * 用户信息
 *
 * @author chenhd
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends Auditable<Long> {
  
  /**
   * 用户状态，0=启用
   */
  public static final int STATUS_ENABLE = 0;
  
  /**
   * 用户状态，1=禁用
   */
  public static final int STATUS_DISABLE = 1;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @Column(nullable = false, unique = true)
  private String username;
  
  @Column(nullable = false)
  private String nickname;
  
  @Column(nullable = false)
  private String password;
  
  @Column(nullable = false)
  private int status = STATUS_ENABLE;

  @Column(nullable = false)
  private int isDelete;

  @OneToOne
  @JoinColumn(name = "roleId")
  private Role role;
}
