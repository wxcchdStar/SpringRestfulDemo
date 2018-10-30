package wxc.web.spring.demo.module.orm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String name;

  @ManyToMany(mappedBy = "courses")
  private List<Student> students;
}
