package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import wxc.web.spring.demo.module.role.model.RoleDTO;

/**
 * @author chenhd
 */
@Data
public class UserDTO {

  @ApiModelProperty("用户ID")
  private String id;

  @ApiModelProperty("用户名")
  private String username;

  @ApiModelProperty("用户昵称")
  private String nickname;

  @ApiModelProperty("状态，0=正常，1=禁用")
  private Integer status;

  @ApiModelProperty("角色")
  private RoleDTO role;

}
