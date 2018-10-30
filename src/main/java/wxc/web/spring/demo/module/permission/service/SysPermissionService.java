package wxc.web.spring.demo.module.permission.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.module.permission.dao.SysPermissionRepository;
import wxc.web.spring.demo.module.permission.entity.SysPermission;
import wxc.web.spring.demo.module.permission.model.PermissionAddParams;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chenhd
 */
@Service
public class SysPermissionService {

  private SysPermissionRepository repository;

  private ModelMapper modelMapper;

  @Autowired
  public SysPermissionService(SysPermissionRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  /**
   * 获取所有权限
   *
   * @return 权限列表
   */
  public List<SysPermissionDTO> getAll() {
    Iterable<SysPermission> permissions = repository.findAll();

    Type targetListType = new TypeToken<List<SysPermissionDTO>>() {
    }.getType();

    return modelMapper.map(permissions, targetListType);
  }

  /**
   * 添加权限
   *
   * @param params 权限信息
   */
  public void addPermission(PermissionAddParams params) {
    SysPermission permission = new SysPermission();
    BeanUtils.copyProperties(params, permission);
    repository.save(permission);
  }

  /**
   * 编辑权限
   * @param id 权限ID
   * @param params 权限信息
   */
  public void editPermission(Long id, PermissionAddParams params) throws ApiException {
    SysPermission permission = repository.findOne(id);
    if (permission == null) {
      throw new ApiException("没有此权限");
    }
    BeanUtils.copyProperties(params, permission);
    repository.save(permission);
  }

  /**
   * 删除权限
   * @param id 权限ID
   */
  public void deletePermission(Long id) throws ApiException {
    List<SysPermission> permissions = repository.findByParentId(id);
    if (permissions != null && !permissions.isEmpty()) {
      throw new ApiException("无法删除有子权限的权限");
    }
    repository.delete(id);
  }
}
