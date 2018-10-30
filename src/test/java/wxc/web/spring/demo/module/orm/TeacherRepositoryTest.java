package wxc.web.spring.demo.module.orm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import wxc.web.spring.demo.module.orm.dao.ClazzRepository;
import wxc.web.spring.demo.module.orm.dao.TeacherRepository;
import wxc.web.spring.demo.module.orm.entity.Clazz;
import wxc.web.spring.demo.module.orm.entity.Teacher;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TeacherRepositoryTest {

  @Autowired
  private TeacherRepository repository;

  @Test
  public void addOrEditTest() throws Exception {
    Clazz clazz = new Clazz();
    clazz.setId(1);
    clazz.setName("一年一班");

    Teacher teacher = new Teacher();
    teacher.setId(1);
    teacher.setName("王老师");

    repository.save(teacher);
  }

  @Test
  public void deleteTest() throws Exception {
    repository.delete(1);
  }
}
