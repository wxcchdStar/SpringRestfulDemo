package wxc.web.spring.demo.module.orm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.module.orm.dao.ClazzRepository;
import wxc.web.spring.demo.module.orm.dao.StudentRepository;
import wxc.web.spring.demo.module.orm.entity.Student;
import wxc.web.spring.demo.module.orm.model.StudentAddParams;

@Service
public class StudentService {

  private StudentRepository repository;

  private ClazzRepository clazzRepository;

  @Autowired
  public StudentService(StudentRepository repository, ClazzRepository clazzRepository) {
    this.repository = repository;
    this.clazzRepository = clazzRepository;
  }

  public Iterable<Student> getList() {
    return repository.findAll();
  }

  public void add(StudentAddParams params) {
    Student student = new Student();
    BeanUtils.copyProperties(params, student);
    student.setClazz(clazzRepository.findOne(params.getClazzId()));
    repository.save(student);
  }

  public void edit(Integer id, StudentAddParams params) {
    Student student = repository.findOne(id);
    BeanUtils.copyProperties(params, student);
    student.setClazz(clazzRepository.findOne(params.getClazzId()));
    repository.save(student);
  }

  public void delete(Integer id) {
    repository.delete(id);
  }
}
