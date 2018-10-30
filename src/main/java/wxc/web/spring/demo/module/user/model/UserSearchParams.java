package wxc.web.spring.demo.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import wxc.web.spring.demo.base.model.CommonPageParams;

import javax.validation.constraints.NotNull;

/**
 * @author chenhd
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchParams extends CommonPageParams {
  
  @ApiModelProperty(value = "用户名称或昵称")
  @NotNull
  private String keyword = "";
  
}
