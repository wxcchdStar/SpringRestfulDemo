package wxc.web.spring.demo.module.orm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.module.orm.dao.ClazzRepository;
import wxc.web.spring.demo.module.orm.dao.TeacherRepository;
import wxc.web.spring.demo.module.orm.entity.Teacher;
import wxc.web.spring.demo.module.orm.model.TeacherAddParams;

@Service
public class TeacherService {

  private TeacherRepository repository;

  private ClazzRepository clazzRepository;

  @Autowired
  public TeacherService(TeacherRepository repository, ClazzRepository clazzRepository) {
    this.repository = repository;
    this.clazzRepository = clazzRepository;
  }

  public Iterable<Teacher> getList() {
    return repository.findAll();
  }

  public void add(TeacherAddParams params) {
    Teacher teacher = new Teacher();
    teacher.setName(params.getName());
    teacher.setClazz(clazzRepository.findOne(params.getClazzId()));
    repository.save(teacher);
  }

  public void edit(Integer id, TeacherAddParams params) {
    Teacher teacher = repository.findOne(id);
    teacher.setName(params.getName());
    teacher.setClazz(clazzRepository.findOne(params.getClazzId()));
    repository.save(teacher);
  }

  public void delete(Integer id) {
    repository.delete(id);
  }
}
