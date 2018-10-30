package wxc.web.spring.demo.module.orm.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wxc.web.spring.demo.module.orm.dao.ClazzRepository;
import wxc.web.spring.demo.module.orm.entity.Clazz;
import wxc.web.spring.demo.module.orm.entity.Teacher;
import wxc.web.spring.demo.module.orm.model.ClazzAddParams;

import java.util.function.Consumer;

@Service
public class ClazzService {

  private ClazzRepository repository;

  @Autowired
  public ClazzService(ClazzRepository repository) {
    this.repository = repository;
  }

  public Iterable<Clazz> getList() {
    Iterable<Clazz> clazzIterable = repository.findAll();
    clazzIterable.forEach(new Consumer<Clazz>() {
      @Override
      public void accept(Clazz clazz) {
        System.out.println("clazz: " + clazz.getName());
        Teacher teacher = clazz.getTeacher();
        if (teacher != null) {
          System.out.println("teacher: " + teacher.getName());
        }
      }
    });
    return clazzIterable;
  }

  public void add(ClazzAddParams params) {
    Clazz clazz = new Clazz();
    BeanUtils.copyProperties(params, clazz);
    repository.save(clazz);
  }

  public void edit(Integer id, ClazzAddParams params) {
    Clazz clazz = repository.findOne(id);
    BeanUtils.copyProperties(params, clazz);
    repository.save(clazz);
  }

  public void delete(Integer id) {
    repository.delete(id);
  }
}
