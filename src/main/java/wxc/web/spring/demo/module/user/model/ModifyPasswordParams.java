package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author chenhd
 */
@Data
public class ModifyPasswordParams {

  @ApiModelProperty("原密码")
  @NotBlank
  private String oldPassword;

  @ApiModelProperty("新密码")
  @NotBlank
  private String newPassword;
}
