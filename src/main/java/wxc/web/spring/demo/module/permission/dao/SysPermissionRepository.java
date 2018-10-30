package wxc.web.spring.demo.module.permission.dao;

import org.springframework.data.repository.CrudRepository;
import wxc.web.spring.demo.module.permission.entity.SysPermission;

import java.util.List;

/**
 * @author chenhd
 */
public interface SysPermissionRepository extends CrudRepository<SysPermission, Long> {

  /**
   * 查找子权限
   * @param parentId 父级权限ID
   * @return 子权限列表
   */
  List<SysPermission> findByParentId(Long parentId);
}
