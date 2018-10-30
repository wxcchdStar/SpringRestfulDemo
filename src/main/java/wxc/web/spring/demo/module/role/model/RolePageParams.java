package wxc.web.spring.demo.module.role.model;

import wxc.web.spring.demo.base.model.CommonPageParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author chenhd
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageParams extends CommonPageParams {

  @ApiModelProperty(value = "角色名称")
  @NotNull
  private String keyword = "";

}
