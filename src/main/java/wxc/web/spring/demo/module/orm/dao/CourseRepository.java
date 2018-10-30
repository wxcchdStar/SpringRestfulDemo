package wxc.web.spring.demo.module.orm.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import wxc.web.spring.demo.module.orm.entity.Course;

public interface CourseRepository extends CrudRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

}
