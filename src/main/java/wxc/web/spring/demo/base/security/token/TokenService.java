package wxc.web.spring.demo.base.security.token;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wxc.web.spring.demo.Const;
import wxc.web.spring.demo.base.security.AuthUserDetails;
import wxc.web.spring.demo.base.utils.RedisKeyUtils;
import wxc.web.spring.demo.module.permission.dao.SysPermissionRepository;
import wxc.web.spring.demo.module.permission.entity.SysPermission;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;
import wxc.web.spring.demo.module.role.entity.Role;
import wxc.web.spring.demo.module.user.dao.UserRepository;
import wxc.web.spring.demo.module.user.entity.User;
import wxc.web.spring.demo.module.user.model.LoginParams;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhd
 */
@Service
public class TokenService {

  private RedisTemplate<String, Object> redisTemplate;

  private UserRepository userRepository;

  private SysPermissionRepository permissionRepository;

  @Autowired
  public TokenService(RedisTemplate<String, Object> redisTemplate, UserRepository userRepository, SysPermissionRepository permissionRepository) {
    this.redisTemplate = redisTemplate;
    this.userRepository = userRepository;
    this.permissionRepository = permissionRepository;
  }

  /**
   * 生成Token
   *
   * @param userId   用户ID
   * @param username 用户名称
   * @param platform 平台
   * @return 生成的Token
   */
  public String generateToken(String userId, String username, String platform) {
    return JwtUtils.generateToken(userId, username, platform);
  }

  /**
   * 根据token获取认证信息
   *
   * @param token token
   * @return 认证信息
   */
  public AuthUserDetails getAuthentication(String token) {
    AuthUserDetails userDetails = JwtUtils.getAuthentication(token);
    if (userDetails == null) {
      return null;
    }
    if (LoginParams.PLATFORM_APP.equals(userDetails.getPlatform())) {
      // APP
      return userDetails;
    } else if (LoginParams.PLATFORM_WX.equals(userDetails.getPlatform())) {
      // 微信
      return userDetails;
    } else if (LoginParams.PLATFORM_WEB.equals(userDetails.getPlatform())) {
      // 后台
      User user = userRepository.findById(Long.parseLong(userDetails.getUserId()));
      if (user == null || user.getIsDelete() == 1) {
        return null;
      }
      userDetails.setEnabled(user.getStatus() == User.STATUS_ENABLE);
      if (Const.ADMIN == user.getId()) {
        Iterable<SysPermission> allPermissions = permissionRepository.findAll();
        List<SysPermissionDTO> permissions = new ModelMapper().map(allPermissions, new TypeToken<List<SysPermissionDTO>>() {
        }.getType());
        userDetails.setAuthorities(permissions);
      } else {
        Role role = user.getRole();
        if (role != null) {
          List<SysPermission> permissionList = role.getPermissions();
          List<SysPermissionDTO> permissions = new ModelMapper().map(permissionList, new TypeToken<List<SysPermissionDTO>>() {
          }.getType());
          userDetails.setAuthorities(permissions);
        }
      }
    }
    return userDetails;
  }

  /**
   * 校验Token
   *
   * @param userId   用户ID
   * @param platform 平台
   * @param token    token
   * @return true=Token有效，false=Token无效
   */
  public boolean checkToken(String userId, String platform, String token) {
    String cachedToken = (String) redisTemplate.opsForValue().get(RedisKeyUtils.getTokenKey(userId, platform));
    return !StringUtils.isEmpty(cachedToken) && cachedToken.equals(token);
  }

  /**
   * 保存Token
   *
   * @param userId   用户ID
   * @param platform 平台
   * @param token    token
   */
  public void saveToken(String userId, String platform, String token) {
    String key = RedisKeyUtils.getTokenKey(userId, platform);
    redisTemplate.opsForValue().set(key, token);
    redisTemplate.expire(key, JwtUtils.EXPIRATION_TIME, TimeUnit.MILLISECONDS);
  }

  /**
   * 清除Token
   *
   * @param userId   登录用户的id
   * @param platform 平台
   */
  public void deleteToken(String userId, String platform) {
    redisTemplate.delete(RedisKeyUtils.getTokenKey(userId, platform));
  }

}
