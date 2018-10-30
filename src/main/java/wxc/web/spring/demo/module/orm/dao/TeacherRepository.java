package wxc.web.spring.demo.module.orm.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import wxc.web.spring.demo.module.orm.entity.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {

}
