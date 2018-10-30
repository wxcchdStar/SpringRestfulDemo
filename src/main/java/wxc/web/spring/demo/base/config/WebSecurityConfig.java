package wxc.web.spring.demo.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import wxc.web.spring.demo.base.security.token.AuthenticationTokenFilter;

/**
 * 安全配置
 *
 * @author chenhd
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private AuthenticationTokenFilter authenticationTokenFilter;

  @Autowired
  public WebSecurityConfig(AuthenticationTokenFilter authenticationTokenFilter) {
    super(false);
    this.authenticationTokenFilter = authenticationTokenFilter;
  }

  /**
   * 配置接口访问权限
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // 跨域
        .cors().and()
        // 禁用CSRF保护，不需要
        .csrf().disable()
        // 不需要鉴权认证的接口
        .authorizeRequests()
        .antMatchers(
            // swagger
            "/swagger-ui.html",
            "/webjars/**",
            "/v2/api-docs",
            "/swagger-resources/**",
            "/swagger-resources/configuration/ui",
            // actuator
            "/actuator/**",
            // druid
            "/druid/**",
            // 在此处填写不需要token认证的接口
            "/*/login",
            "/*/sign-in",
            "/*/sign-up",
            "/v1/preview/**/*",
            "/v1/download/**/*"
        )
        .permitAll()
        // 除上面外的所有请求全部需要鉴权认证
        .antMatchers("/**").authenticated()
        .and()
        // 不需要Session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    // 添加鉴权过滤器
    http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * 无论客户端有没有加密密码，在服务端加密密码是必须的！
   *
   * @return 密码加密器
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
