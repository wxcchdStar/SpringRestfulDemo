package wxc.web.spring.demo.module.role.dao;

import wxc.web.spring.demo.module.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author chenhd
 */
public interface RoleRepository extends CrudRepository<Role, Long>, JpaSpecificationExecutor {

  /**
   * 根据名称模糊查询
   *
   * @param name     名称
   * @param isDelete 是否已删除
   * @param pageable 分页
   * @return 分页信息
   */
  Page<Role> findByNameContainingAndIsDeleteEquals(String name, Integer isDelete, Pageable pageable);

  /**
   * 查找角色
   *
   * @param name     名称
   * @param isDelete 是否已删除
   * @return 角色
   */
  Role findByNameAndIsDeleteEquals(String name, Integer isDelete);
}
