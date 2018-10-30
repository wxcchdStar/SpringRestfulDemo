package wxc.web.spring.demo.module.orm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.module.orm.dao.CourseRepository;
import wxc.web.spring.demo.module.orm.entity.Course;

@Service
public class CourseService {

  private CourseRepository repository;

  @Autowired
  public CourseService(CourseRepository repository) {
    this.repository = repository;
  }

  public Iterable<Course> getList() {
    return repository.findAll();
  }

  public void add(Course params) {
    repository.save(params);
  }

  public void edit(Integer id, Course params) {
    params.setId(id);
    repository.save(params);
  }

  public void delete(Integer id) {
    repository.delete(id);
  }
}
