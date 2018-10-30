package wxc.web.spring.demo.module.permission.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 系统权限
 *
 * @author chenhd
 */
@Data
public class SysPermissionDTO implements GrantedAuthority {

  @ApiModelProperty("权限ID")
  private Long id;

  @ApiModelProperty("权限编码")
  private String code;

  @ApiModelProperty("权限名称")
  private String name;

  @ApiModelProperty("权限地址")
  private String url;

  @ApiModelProperty("是否是菜单，0=不是，1=是")
  private Integer isMenu;

  @ApiModelProperty("排序")
  private Integer sort;

  @ApiModelProperty("备注")
  private String describes;

  @ApiModelProperty("父级ID")
  private Long parentId;

  @Override
  public String getAuthority() {
    return code;
  }

}
