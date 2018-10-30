package wxc.web.spring.demo.module.orm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Teacher {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String name;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "clazz_id")
  private Clazz clazz;

  @Override
  public String toString() {
    return "Teacher{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
