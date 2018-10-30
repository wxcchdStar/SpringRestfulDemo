package wxc.web.spring.demo.module.permission.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wxc.web.spring.demo.base.jpa.Auditable;

import javax.persistence.*;

/**
 * 权限表
 *
 * @author chenhd
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class SysPermission extends Auditable<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String name;

  @Column
  private int isMenu;

  @Column
  private String url;

  @Column
  private int sort;

  @Column
  private String describes;

  @Column
  private long parentId;

}
