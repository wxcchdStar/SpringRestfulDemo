package wxc.web.spring.demo.module.permission.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author chenhd
 */
@Data
public class PermissionAddParams {

  @ApiModelProperty(value = "权限编码", required = true)
  @NotNull
  private String code;

  @ApiModelProperty(value = "权限名称", required = true)
  @NotNull
  private String name;

  @ApiModelProperty(value = "权限地址")
  private String url;

  @ApiModelProperty(value = "是否是菜单，0=不是，1=是", required = true)
  @NotNull
  private Integer isMenu;

  @ApiModelProperty(value = "排序")
  private Integer sort = 0;

  @ApiModelProperty(value = "备注")
  private String describes;

  @ApiModelProperty(value = "父级")
  private Long parentId;

}
