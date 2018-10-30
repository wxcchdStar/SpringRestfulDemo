package wxc.web.spring.demo.module.role.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;

import java.util.List;

/**
 * @author chenhd
 */
@Data
public class RoleDTO {

  @ApiModelProperty("角色ID")
  private Long id;

  @ApiModelProperty("角色名称")
  private String name;

  @ApiModelProperty("角色描述")
  private String describes;

  @ApiModelProperty("角色状态，0-正常，1-禁用")
  private Integer status;

  @ApiModelProperty(value = "权限")
  private List<SysPermissionDTO> permissions;
}
