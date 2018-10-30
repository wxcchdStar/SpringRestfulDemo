package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author chenhd
 */
@Data
public class ResetPasswordParams {

  @ApiModelProperty("新密码")
  @NotBlank
  private String password;
}
