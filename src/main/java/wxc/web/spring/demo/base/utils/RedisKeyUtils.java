package wxc.web.spring.demo.base.utils;

/**
 * Redis缓存中存储的Keys
 * @author chenhd
 */
public final class RedisKeyUtils {

  /**
   * Token+Platform+UserId
   */
  private static final String TOKEN = "Token%s%s";

  private RedisKeyUtils() {
  }

  /**
   * 根据userId获取Token key
   * @param userId 用户ID
   * @param platform 平台
   * @return key
   */
  public static String getTokenKey(String userId, String platform) {
    return String.format(RedisKeyUtils.TOKEN, platform, userId);
  }
}
