package wxc.web.spring.demo.module.orm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Clazz {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String name;

  @OneToOne(mappedBy = "clazz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Teacher teacher;

  @OneToMany(mappedBy = "clazz")
  private List<Student> studentList;

}
