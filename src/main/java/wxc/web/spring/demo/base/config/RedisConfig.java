package wxc.web.spring.demo.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置
 *
 * @author chenhd
 */
@Configuration
@EnableCaching
public class RedisConfig {

  @Bean
  public CacheManager cacheManager(RedisTemplate redisTemplate) {
    return new RedisCacheManager(redisTemplate);
  }
  
  @Bean
  public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(factory);
    template.setKeySerializer(new StringRedisSerializer());
    return template;
  }
  
}
