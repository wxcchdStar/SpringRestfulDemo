package wxc.web.spring.demo.base.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import wxc.web.spring.demo.base.security.AuthUserDetails;

/**
 * @author chenhd
 */
public final class Utils {

  private Utils() {
  }

  /**
   * 从上下文中获取用户ID
   *
   * @return 用户ID
   */
  public static String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof AuthUserDetails) {
      return ((AuthUserDetails) authentication.getPrincipal()).getUserId();
    }
    return null;
  }
}
