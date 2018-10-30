package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;

import java.util.List;

/**
 * @author chenhd
 */
@Data
public class LoginDTO {

  @ApiModelProperty("用户ID")
  private String id;

  @ApiModelProperty("用户名称")
  private String username;

  @ApiModelProperty("用户昵称")
  private String nickname;

  @ApiModelProperty("TOKEN")
  private String token;

  @ApiModelProperty("TOKEN过期时间，单位：秒")
  private Long expiresIn;

  @ApiModelProperty("权限")
  private List<SysPermissionDTO> permissions;
}
