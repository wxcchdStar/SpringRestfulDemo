package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author chenhd
 */
@Data
public class LoginParams {

  public static final String PLATFORM_APP = "app";

  public static final String PLATFORM_ANDROID = "android";

  public static final String PLATFORM_IOS = "ios";

  public static final String PLATFORM_WEB = "web";

  public static final String PLATFORM_WX = "wx";

  @ApiModelProperty(required = true, value = "用户名")
  @NotBlank
  private String username;
  
  @ApiModelProperty(required = true, value = "密码")
  @NotBlank
  private String password;

  @ApiModelProperty(required = true, value = "平台，（app、android、ios、web、wx）")
  @NotBlank
  private String platform;
}
