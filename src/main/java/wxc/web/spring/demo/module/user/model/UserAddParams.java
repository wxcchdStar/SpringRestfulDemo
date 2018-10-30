package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import wxc.web.spring.demo.module.user.entity.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author chenhd
 */
@Data
public class UserAddParams {
  
  @ApiModelProperty(required = true, value = "用户名")
  @NotBlank
  private String username;

  @ApiModelProperty(required = true, value = "用户昵称")
  @NotBlank
  private String nickname;
  
  @ApiModelProperty(required = true, value = "密码")
  @NotBlank
  private String password;
  
  @ApiModelProperty(value = "0=正常，1=禁用，默认启用")
  @Min(0)
  @Max(1)
  private Integer status = User.STATUS_ENABLE;

  @ApiModelProperty(required = true, value = "角色")
  @NotNull
  private Long roleId;
}
