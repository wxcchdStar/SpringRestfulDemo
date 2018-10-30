package wxc.web.spring.demo.module.role.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.base.model.CommonPageDTO;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.module.permission.entity.SysPermission;
import wxc.web.spring.demo.module.role.dao.RoleRepository;
import wxc.web.spring.demo.module.role.entity.Role;
import wxc.web.spring.demo.module.role.model.RoleAddParams;
import wxc.web.spring.demo.module.role.model.RoleDTO;
import wxc.web.spring.demo.module.role.model.RolePageParams;
import wxc.web.spring.demo.module.user.dao.UserRepository;
import wxc.web.spring.demo.module.user.entity.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenhd
 */
@Service
public class RoleService {

  private static final String ROLE_NOT_EXIST = "角色不存在";

  private RoleRepository repository;

  private ModelMapper modelMapper;

  private UserRepository userRepository;

  @Autowired
  public RoleService(RoleRepository repository, ModelMapper modelMapper, UserRepository userRepository) {
    this.repository = repository;
    this.modelMapper = modelMapper;
    this.userRepository = userRepository;
  }

  /**
   * 获取角色列表
   *
   * @param params 参数
   * @return 分页列表
   */
  public CommonPageDTO<RoleDTO> getRoleList(RolePageParams params) {
    Sort sort = new Sort(Sort.Direction.DESC, "createAt");
    Pageable pageable = new PageRequest(params.getPage(), params.getSize(), sort);

    Page<Role> page = repository.findByNameContainingAndIsDeleteEquals(params.getKeyword(), 0, pageable);

    Type targetListType = new TypeToken<List<RoleDTO>>() {
    }.getType();
    List<RoleDTO> list = modelMapper.map(page.getContent(), targetListType);

    CommonPageDTO<RoleDTO> dto = new CommonPageDTO<>();
    dto.setCurrentPage(params.getPage());
    dto.setTotalCount(page.getTotalElements());
    dto.setTotalPage(page.getTotalPages());
    dto.setList(list);
    return dto;
  }

  /**
   * 添加角色
   *
   * @param params 角色信息
   */
  public void addRole(RoleAddParams params) throws ApiException {
    if (repository.findByNameAndIsDeleteEquals(params.getName(), 0) != null) {
      throw new ApiException(ROLE_NOT_EXIST);
    }
    Role role = new Role();
    BeanUtils.copyProperties(params, role);
    repository.save(role);
  }

  /**
   * 编辑角色
   *
   * @param id     角色ID
   * @param params 角色信息
   */
  public void editRole(Long id, RoleAddParams params) throws ApiException {
    Role role = repository.findOne(id);
    if (role == null) {
      throw new ApiException(ROLE_NOT_EXIST);
    }
    if (!role.getName().equals(params.getName()) && repository.findByNameAndIsDeleteEquals(params.getName(), 0) != null) {
      throw new ApiException("角色名已存在");
    }
    List<User> userList = userRepository.findAllByRoleIdAndIsDeleteEquals(id, 0);
    if (params.getStatus() == User.STATUS_DISABLE && userList != null && !userList.isEmpty()) {
      throw new ApiException("该角色下存在用户，不能禁用");
    }
    BeanUtils.copyProperties(params, role);
    repository.save(role);
  }

  /**
   * 删除角色
   *
   * @param id 角色ID
   */
  public void deleteRole(Long id) throws ApiException {
    Role role = repository.findOne(id);
    if (role == null) {
      throw new ApiException(ROLE_NOT_EXIST);
    }
    role.setIsDelete(1);
    repository.save(role);
  }

  /**
   * 角色授权
   *
   * @param id            角色ID
   * @param permissionIds 权限ID
   * @throws ApiException 角色不存在
   */
  public void grantPermission(Long id, List<Long> permissionIds) throws ApiException {
    Role role = repository.findOne(id);
    if (role == null) {
      throw new ApiException(ROLE_NOT_EXIST);
    }
    List<SysPermission> permissions = new ArrayList<>();
    for (Long permissionId : permissionIds) {
      SysPermission permission = new SysPermission();
      permission.setId(permissionId);
      permissions.add(permission);
    }
    role.setPermissions(permissions);
    repository.save(role);
  }
}
