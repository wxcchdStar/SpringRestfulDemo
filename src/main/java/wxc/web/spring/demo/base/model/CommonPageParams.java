package wxc.web.spring.demo.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 通用分页请求参数
 * @author wangxd
 */
@Data
public class CommonPageParams {

  @ApiModelProperty(value = "第几页，从0开始，默认为0", required = true)
  @NotNull
  @Min(0)
  private Integer page = 0;

  @ApiModelProperty(value = "每页数量，默认为20", required = true)
  @NotNull
  @Min(1)
  private Integer size = 20;
}
