package wxc.web.spring.demo.base.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import wxc.web.spring.demo.base.utils.Utils;

/**
 * JPA配置
 *
 * @author chenhd
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "jpaConfig")
public class JpaConfig implements AuditorAware<Long> {
  
  /**
   * 获取当前操作人ID
   *
   * @return 操作人ID
   */
  @Override
  public Long getCurrentAuditor() {
    String userId = Utils.getUserId();
    return userId == null ? 0 : Long.parseLong(userId);
  }
}
