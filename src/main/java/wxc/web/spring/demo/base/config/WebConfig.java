package wxc.web.spring.demo.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Web配置
 *
 * @author chenhd
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
  
  /**
   * 跨域
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }
  
  /**
   * 请求日志过滤器
   *
   * @return 过滤器
   */
  @Bean
  public CommonsRequestLoggingFilter requestLogFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeClientInfo(true);
    filter.setIncludeHeaders(true);
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    return filter;
  }
  
}