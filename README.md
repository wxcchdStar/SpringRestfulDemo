## 基础功能
1. REST（`@RestController`，`SpringMVC`）
2. 数据绑定和校验（`@RequestBody`，`@Valid`，`@PathVariable`）
4. 访问MySQL数据库（`JPA`）
5. DTO、DO等POJO的相互转换（`ModelMapper`）
5. 统一结果处理（`ResponseResult`）
6. 统一错误处理（`@ControllerAdvice`，`@ExceptionHandler`）
7. 日志（`logback`）
7. 跨域（`WebMvcConfigurerAdapter`）
8. 统一认证、鉴权（`JWT`、`SpringSecurity`）
9. 集成Swagger2（API文档管理）（`Springfox`）
11. `加盐`、`随机化Hash`保存密码（`Bcrypt`）
11. 请求日志
13. HTTPS（`letsencrypt`）

## 高级功能
1. 缓存：Redis（`spring-data-redis`、`@EnableCaching`）
2. 数据库设计（`MySQLWorkbench`）
3. 根据数据库生成JavaBean（`IDEA的生成脚本`）
4. 数据库升级（`flyway`）
5. 数据库备份（`cron`）
6. 多环境配置（`profile`），外部化环境配置，对开发透明（`docker环境变量`）
7. 集成Jenkins（自动测试、自动部署） （`gitlab-ci`）
8. MySQL数据读写分离
9. HTTP缓存
10. 权限拦截，[参考](http://elim.iteye.com/blog/2247073#_Toc431483139)
11. 单元测试

## 注意
1. 禁止数据库服务访问本地文件系统
2. SQL语句注入问题

## Libraries
### [Spring全家桶](https://spring.io)
主要有：
1. Spring Boot
2. Spring Web
3. Spring Data JPA
4. Spring Security

### [Springfox](https://github.com/springfox/springfox)
API文档管理

### 其他
1. [Lombok](https://projectlombok.org)
消除冗余的Java代码，如：setter、getter方法
2. [ModelMapper](http://modelmapper.org)
可以转换DO、DTO等POJOs
3. [Flyway](https://flywaydb.org/)
数据库迁移工具

## IDEA插件
1. [JRebel](https://zeroturnaround.com/software/jrebel/)，热加载（必须）
2. Alibaba Java Config Guidelines（必须）
3. Lombok（必须）
3. FindBugs
4. QAPlug-PMD
5. SonarLint

## 项目分层
按模块+职责分包分层。
每个模块均有：`Controller`、`Service`、`DAO`三层

## [部署](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html)
1. [Jar包](http://blog.didispace.com/spring-boot-run-backend/)
2. Docker: `docker-compose up -d mysql redis`、`docker-compose down`

## 项目规范
1. 所有API在路由上区分移动端、后台和微信端。如：`/web/v1/user`、`/app/v1/wx/user`、`/v1/user`
2. 所有API改动需仔细想想是否会影响旧版本
3. 查询参数，如果多个，禁止使用诸如`keyword`等此类宽泛的用词
4. 若适当，可将不同端的接口拆分成不同的`Controller`，但`Service`可共用一个
5. **出API时先想想以后发生改动怎么办**