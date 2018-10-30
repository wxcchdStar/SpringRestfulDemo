package wxc.web.spring.demo.module.user.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import wxc.web.spring.demo.module.user.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenhd
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
  
  /**
   * 根据用户名称查找用户信息
   *
   * @param username 用户名称
   * @param isDelete 是否删除
   * @return 用户信息
   */
  User findByUsernameAndIsDeleteEquals(String username, Integer isDelete);

  /**
   * 根据用户ID查找用户信息<br/>
   * 问：为什么不用{@link CrudRepository#findOne(Serializable)}?<br/>
   * 答：在OneToOne关系中，如果给用户赋一个不存在的角色ID，那么findOne会返回null。
   * @param id 用户ID
   * @return 用户信息
   */
  User findById(Long id);

  /**
   * 获取某一角色下的所有用户
   * @param id 角色ID
   * @param isDelete 是否删除
   * @return 用户列表
   */
  List<User> findAllByRoleIdAndIsDeleteEquals(Long id, Integer isDelete);
}
