package wxc.web.spring.demo.base.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import wxc.web.spring.demo.base.security.AuthUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * JWT工具类
 *
 * @author chenhd
 */
@Slf4j
public final class JwtUtils {

  /**
   * Token有效期7天
   */
  public static final long EXPIRATION_TIME = 1L * 24 * 3600 * 1000 * 7;

  /**
   * Token前缀
   */
  public static final String TOKEN_PREFIX = "Bearer ";

  /**
   * JWT密码
   */
  private static final String SECRET = "P@ss!w02d";

  /**
   * 存放Token的Header Key
   */
  public static final String HEADER_STRING = "Authorization";

  private static final String KEY_PLATFORM = "platform";

  private JwtUtils() {
  }

  /**
   * 生成Token
   *
   * @param userId   用户ID
   * @param username 用户名称
   * @param platform 平台
   * @return 生成的Token
   */
  public static String generateToken(String userId, String username, String platform) {
    return Jwts.builder()
        .setId(userId)
        .setSubject(username)
        .claim(KEY_PLATFORM, platform)
        // 生成时间
        .setIssuedAt(new Date())
        // 过期时间
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        // 签名算法
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  /**
   * 获取Token
   *
   * @param request 请求
   * @return token
   */
  public static String getToken(HttpServletRequest request) {
    if (request != null) {
      String token = request.getHeader(HEADER_STRING);
      if (token != null) {
        return token.replace(TOKEN_PREFIX, "");
      }
    }
    return null;
  }

  /**
   * 获取认证信息
   *
   * @param token token
   * @return 认证信息
   */
  public static AuthUserDetails getAuthentication(String token) {
    try {
      if (token != null) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
        String userId = claims.getId();
        String username = claims.getSubject();
        String platform = claims.get(KEY_PLATFORM, String.class);
        return new AuthUserDetails(userId, username, platform);
      }
    } catch (ExpiredJwtException e) {
      log.error("token已过期", e);
      throw e;
    } catch (Exception e) {
      log.error("获取认证信息错误", e);
    }
    return null;
  }
}
