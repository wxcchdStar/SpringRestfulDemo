package wxc.web.spring.demo.base.response;

/**
 * 统一响应结果生成器
 *
 * @author chenhd
 */
public class ResponseResultGenerator {

  private ResponseResultGenerator() {
  }

  /**
   * 创建统一响应结果Bean
   *
   * @param code    错误码
   * @param message 提示信息
   * @param data    数据
   * @param <T>     数据类型
   * @return 统一响应结果Bean
   */
  public static <T> ResponseResult<T> create(int code, String message, T data) {
    ResponseResult<T> result = ResponseResult.newInstance();
    result.setCode(code);
    result.setMessage(message);
    result.setData(data);
    return result;
  }
  
  /**
   * 成功
   *
   * @param message 成功提示信息
   * @param data    数据
   * @param <T>     数据类型
   * @return 统一响应结果Bean
   */
  public static <T> ResponseResult<T> success(String message, T data) {
    return create(Code.SUCCESS, message, data);
  }
  
  /**
   * 失败
   *
   * @param message 失败提示信息
   * @param data    数据
   * @param <T>     数据类型
   * @return 统一响应结果Bean
   */
  public static <T> ResponseResult<T> error(String message, T data) {
    return create(Code.ERROR, message, data);
  }
}
