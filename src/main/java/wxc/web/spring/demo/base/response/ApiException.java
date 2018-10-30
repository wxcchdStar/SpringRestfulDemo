package wxc.web.spring.demo.base.response;

import lombok.Getter;

/**
 * 逻辑异常，通常在 Service 层抛出
 *
 * @author chenhd
 */
public class ApiException extends Exception {
  
  @Getter
  private final int code;
  
  public ApiException(String message) {
    this(Code.ERROR, message);
  }
  
  public ApiException(int code, String message) {
    super(message);
    this.code = code;
  }
}
