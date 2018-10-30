package wxc.web.spring.demo.base.security.permission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author chenhd
 */
public class GlobalPermissionEvaluator implements PermissionEvaluator {
  
  @Override
  public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    return false;
  }
  
  @Override
  public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
    return false;
  }
}
