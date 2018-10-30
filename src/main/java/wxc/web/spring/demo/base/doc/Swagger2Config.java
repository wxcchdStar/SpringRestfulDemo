package wxc.web.spring.demo.base.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import wxc.web.spring.demo.SpringRestfulDemoApplication;
import wxc.web.spring.demo.base.security.token.JwtUtils;

/**
 * API文档配置
 *
 * @author chenhd
 */
@Profile(value = {"swagger"})
@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
    SpringRestfulDemoApplication.class
})
public class Swagger2Config {

  /**
   * TODO:文档使用发请求所使用的Token
   */
  public static final String TOKEN = "xxx";

  @Bean
  public Docket petApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        // 指定包下的接口
        .apis(RequestHandlerSelectors.basePackage(SpringRestfulDemoApplication.class.getPackage().getName()))
        .paths(PathSelectors.any())
        .build()
        .pathMapping("/");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("API文档")
        .description("")
        .build();
  }

  @Bean
  public SecurityConfiguration security() {
    return new SecurityConfiguration(null,
        null,
        null,
        null,
        JwtUtils.TOKEN_PREFIX + TOKEN,
        ApiKeyVehicle.HEADER,
        JwtUtils.HEADER_STRING,
        ",");
  }

}
