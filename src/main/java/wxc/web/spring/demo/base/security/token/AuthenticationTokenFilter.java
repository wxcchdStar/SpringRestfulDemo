package wxc.web.spring.demo.base.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wxc.web.spring.demo.base.doc.Swagger2Config;
import wxc.web.spring.demo.base.response.Code;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.base.security.AuthUserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token认证过滤器
 *
 * @author chenhd
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      String token = JwtUtils.getToken(request);
      AuthUserDetails authUserDetails = tokenService.getAuthentication(token);
      if (authUserDetails != null) {
        if (authUserDetails.isEnabled()) {
          String userId = authUserDetails.getUserId();
          String platform = authUserDetails.getPlatform();
          if (Swagger2Config.TOKEN.equals(token) || tokenService.checkToken(userId, platform, token)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authUserDetails, null, null));
          }
          chain.doFilter(request, response);
        } else {
          handleAccountDisabled(response);
        }
        return;
      }
    } catch (ExpiredJwtException e) {
      handleTokenExpired(response);
      return;
    }

    chain.doFilter(request, response);
  }

  /**
   * 处理账号已被禁用
   *
   * @param response response
   * @throws IOException 异常
   */
  private static void handleAccountDisabled(HttpServletResponse response) throws IOException {
    ResponseResult<Void> responseResult = ResponseResultGenerator.create(Code.ACCOUNT_DISABLED, "该账号已被禁用", null);
    String responseStr = new ObjectMapper().writeValueAsString(responseResult);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.getOutputStream().write(responseStr.getBytes("utf-8"));
    response.setStatus(HttpStatus.OK.value());
  }

  /**
   * 处理登录过期
   *
   * @param response response
   * @throws IOException 异常
   */
  private static void handleTokenExpired(HttpServletResponse response) throws IOException {
    ResponseResult<Void> responseResult = ResponseResultGenerator.create(Code.LOGIN_EXPIRED, "登录已过期", null);
    String responseStr = new ObjectMapper().writeValueAsString(responseResult);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.getOutputStream().write(responseStr.getBytes("utf-8"));
    response.setStatus(HttpStatus.OK.value());
  }
}
