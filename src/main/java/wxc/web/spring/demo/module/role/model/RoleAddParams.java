package wxc.web.spring.demo.module.role.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import wxc.web.spring.demo.module.role.entity.Role;

import javax.validation.constraints.NotNull;

/**
 * @author chenhd
 */
@Data
public class RoleAddParams {

  @ApiModelProperty(value = "角色名称", required = true)
  @NotBlank
  private String name;

  @ApiModelProperty("角色描述")
  private String describes;

  @ApiModelProperty(value = "角色状态，0-正常，1-禁用", required = true)
  @NotNull
  private Integer status = Role.STATUS_ENABLE;

}
