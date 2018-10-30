package wxc.web.spring.demo.base.response;

import lombok.Data;

/**
 * 统一响应结果
 *
 * @author chenhd
 */
@Data
public class ResponseResult<T> {

  /**
   * 错误码，详见{@link Code}
   */
  private int code;

  /**
   * 错误信息，客户端可使用此信息提示用户
   */
  private String message;

  /**
   * 当请求成功时，返回的数据
   */
  private T data;

  private ResponseResult() {
  }

  /**
   * 创建实例
   *
   * @param <T> 数据类型
   * @return 统一响应结果Bean
   */
  public static <T> ResponseResult<T> newInstance() {
    return new ResponseResult<>();
  }

}
