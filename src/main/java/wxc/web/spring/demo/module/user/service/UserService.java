package wxc.web.spring.demo.module.user.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.Const;
import wxc.web.spring.demo.base.model.CommonPageDTO;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.security.AuthUserDetails;
import wxc.web.spring.demo.base.security.token.JwtUtils;
import wxc.web.spring.demo.base.security.token.TokenService;
import wxc.web.spring.demo.base.utils.RedisKeyUtils;
import wxc.web.spring.demo.base.utils.Utils;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;
import wxc.web.spring.demo.module.role.dao.RoleRepository;
import wxc.web.spring.demo.module.role.entity.Role;
import wxc.web.spring.demo.module.user.dao.UserRepository;
import wxc.web.spring.demo.module.user.entity.User;
import wxc.web.spring.demo.module.user.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chenhd
 */
@Service
@Slf4j
public class UserService {

  private UserRepository repository;

  private ModelMapper modelMapper;

  private BCryptPasswordEncoder passwordEncoder;

  private TokenService tokenService;

  private RoleRepository roleRepository;

  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public UserService(UserRepository repository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder,
                     TokenService tokenService, RoleRepository roleRepository, RedisTemplate<String, Object> redisTemplate) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
    this.roleRepository = roleRepository;
    this.redisTemplate = redisTemplate;
  }

  /**
   * 登录
   *
   * @param username 用户名
   * @param password 密码
   * @param platform 平台
   * @return 登录信息
   */
  public LoginDTO login(String username, String password, String platform) throws ApiException {
    User user = repository.findByUsernameAndIsDeleteEquals(username, 0);
    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
      throw new ApiException("用户名或密码错误");
    }
    if (user.getStatus() == User.STATUS_DISABLE) {
      throw new ApiException("用户已被禁用");
    }
    // 生成Token
    String token = tokenService.generateToken(String.valueOf(user.getId()), username, platform);
    // 放入缓存
    tokenService.saveToken(String.valueOf(user.getId()), platform, token);
    // 返回登陆信息
    LoginDTO loginDTO = modelMapper.map(user, LoginDTO.class);
    loginDTO.setToken(token);
    loginDTO.setExpiresIn(JwtUtils.EXPIRATION_TIME / 1000);
    // 权限
    Role role = user.getRole();
    if (role != null) {
      Type targetListType = new TypeToken<List<SysPermissionDTO>>() {
      }.getType();
      List<SysPermissionDTO> permissions = modelMapper.map(role.getPermissions(), targetListType);
      loginDTO.setPermissions(permissions);
    }
    return loginDTO;
  }

  /**
   * 退出登录
   */
  public void logout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof AuthUserDetails) {
      AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
      tokenService.deleteToken(authUserDetails.getUserId(), authUserDetails.getPlatform());
    }
  }

  /**
   * 添加用户
   *
   * @param params 用户信息
   */
  public void addUser(UserAddParams params) throws ApiException {
    if (repository.findByUsernameAndIsDeleteEquals(params.getUsername(), 0) != null) {
      throw new ApiException("用户已存在");
    }
    Role role = roleRepository.findOne(params.getRoleId());
    if (role == null) {
      throw new ApiException("角色不存在");
    }
    if (role.getStatus() == User.STATUS_DISABLE) {
      throw new ApiException("角色已被禁用");
    }
    User user = new User();
    BeanUtils.copyProperties(params, user);
    // 加密密码
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    // 角色
    user.setRole(role);
    // 保存用户
    repository.save(user);
  }

  /**
   * 编辑用户
   *
   * @param id     用户ID
   * @param params 用户信息
   * @throws ApiException 异常
   */
  public void editUser(Long id, UserEditParams params) throws ApiException {
    if (Const.ADMIN == id) {
      throw new ApiException("admin不能被编辑");
    }
    User user = repository.findOne(id);
    if (user == null) {
      throw new ApiException("用户不存在");
    }
    if (!user.getUsername().equals(params.getUsername())
        && repository.findByUsernameAndIsDeleteEquals(params.getUsername(), 0) != null) {
      throw new ApiException("用户名已存在");
    }
    Role role = roleRepository.findOne(params.getRoleId());
    if (role == null) {
      throw new ApiException("角色不存在");
    }
    if (params.getStatus() == User.STATUS_ENABLE && role.getStatus() == User.STATUS_DISABLE) {
      throw new ApiException("角色已被禁用");
    }
    if (user.getStatus() != params.getStatus() && params.getStatus() == User.STATUS_DISABLE) {
      // 用户已被禁用，清空所有平台的TOKEN
      clearAllToken(user.getId());
    }
    user.setRole(role);
    BeanUtils.copyProperties(params, user);
    repository.save(user);
  }

  /**
   * 删除用户
   *
   * @param id 用户ID
   */
  public void deleteUser(Long id) throws ApiException {
    if (Const.ADMIN == id) {
      throw new ApiException("admin不能被删除");
    }
    String userIdInCtx = Utils.getUserId();
    if (userIdInCtx == null) {
      throw new ApiException("非法操作:删除用户");
    }
    if (id == Long.parseLong(userIdInCtx)) {
      throw new ApiException("无法删除自己");
    }
    User user = repository.findOne(id);
    if (user == null) {
      throw new ApiException("用户不存在");
    }
    user.setIsDelete(1);
    repository.save(user);
  }

  /**
   * 根据用户名称或昵称查询
   *
   * @param params 查询参数
   * @return 分页的用户列表
   */
  public CommonPageDTO<UserDTO> findUserList(UserSearchParams params) {
    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(params.getPage(), params.getSize(), sort);

    Page<User> userPage = repository.findAll(new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate usernameLikePre = cb.like(root.get("username"), "%" + params.getKeyword() + "%");
        Predicate nicknameLikePre = cb.like(root.get("nickname"), "%" + params.getKeyword() + "%");
        Predicate keywordPre = cb.or(usernameLikePre, nicknameLikePre);

        Predicate isDeletePre = cb.equal(root.get("isDelete"), "0");

        query.where(keywordPre, isDeletePre);
        return null;
      }
    }, pageable);

    Type targetListType = new TypeToken<List<UserDTO>>() {
    }.getType();
    List<UserDTO> userDTOList = modelMapper.map(userPage.getContent(), targetListType);

    CommonPageDTO<UserDTO> dto = new CommonPageDTO<>();
    dto.setCurrentPage(params.getPage());
    dto.setTotalCount(userPage.getTotalElements());
    dto.setTotalPage(userPage.getTotalPages());
    dto.setList(userDTOList);

    return dto;
  }

  /**
   * 修改密码
   *
   * @param params 参数
   */
  public void modifyPassword(ModifyPasswordParams params) throws ApiException {
    Long userId = Long.parseLong(Utils.getUserId());
    User user = repository.findById(userId);
    if (!passwordEncoder.matches(params.getOldPassword(), user.getPassword())) {
      throw new ApiException("原密码不正确");
    }
    user.setPassword(passwordEncoder.encode(params.getNewPassword()));
    repository.save(user);
    clearAllToken(user.getId());
  }

  /**
   * 重置密码
   * @param id 用户
   * @param params 参数
   */
  public void resetPassword(Long id, ResetPasswordParams params) {
    User user = repository.findById(id);
    user.setPassword(passwordEncoder.encode(params.getPassword()));
    repository.save(user);
    clearAllToken(user.getId());
  }

  private void clearAllToken(Long userId) {
    clearToken(userId, LoginParams.PLATFORM_APP);
    clearToken(userId, LoginParams.PLATFORM_ANDROID);
    clearToken(userId, LoginParams.PLATFORM_IOS);
    clearToken(userId, LoginParams.PLATFORM_WEB);
    clearToken(userId, LoginParams.PLATFORM_WX);
  }

  private void clearToken(Long userId, String platform) {
    redisTemplate.delete(RedisKeyUtils.getTokenKey(String.valueOf(userId), platform));
  }
}
