package wxc.web.spring.demo.base.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;

import java.util.Collection;
import java.util.List;

/**
 * @author chenhd
 */
public class AuthUserDetails implements UserDetails {
  
  @Getter
  private String userId;
  
  @Getter
  private String username;

  @Getter
  private String platform;

  @Setter
  private List<SysPermissionDTO> authorities;

  @Setter
  private boolean isEnabled;

  public AuthUserDetails(String userId, String username, String platform) {
    this.userId = userId;
    this.username = username;
    this.platform = platform;
    this.isEnabled = true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
  
  @Override
  public String getPassword() {
    return null;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
