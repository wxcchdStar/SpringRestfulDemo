package wxc.web.spring.demo.module.role.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wxc.web.spring.demo.base.jpa.Auditable;
import wxc.web.spring.demo.module.permission.entity.SysPermission;

import javax.persistence.*;
import java.util.List;

/**
 * @author chenhd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Role extends Auditable<Long> {

  public static final int STATUS_ENABLE = 0;

  public static final int STATUS_DISABLE = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column
  private String describes;

  @Column
  private int status = STATUS_ENABLE;

  @Column
  private int isDelete;

  @OneToMany
  @JoinTable(name = "role_permission",
      joinColumns = {
          @JoinColumn(name = "role_id")
      },
      inverseJoinColumns = {
          @JoinColumn(name = "permission_id")
      }
  )
  private List<SysPermission> permissions;
}
