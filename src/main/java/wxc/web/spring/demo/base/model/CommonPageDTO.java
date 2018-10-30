package wxc.web.spring.demo.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 通用分页DTO
 * @param <T>
 * @author wangxd
 */
@Data
public class CommonPageDTO<T> {
  @ApiModelProperty(notes = "总条数")
  public long totalCount;

  @ApiModelProperty(notes = "总页数")
  public int totalPage;

  @ApiModelProperty(notes = "当前页数")
  public int currentPage;

  @ApiModelProperty(notes = "列表")
  public List<T> list;
}
