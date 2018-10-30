package wxc.web.spring.demo.base.response;

/**
 * API状态码
 *
 * @author chenhd
 */
public final class Code {
  
  /**
   * 通用成功码
   */
  public static final int SUCCESS = 1000;
  
  /**
   * 通用错误码
   */
  public static final int ERROR = 1001;
  
  /**
   * 错误码：登录过期
   */
  public static final int LOGIN_EXPIRED = 1002;

  /**
   * 错误码：无权限
   */
  public static final int NO_PERMISSIONS = 1003;

  /**
   * 错误码：账号被禁用
   */
  public static final int ACCOUNT_DISABLED = 1004;

  private Code() {
  }
}
