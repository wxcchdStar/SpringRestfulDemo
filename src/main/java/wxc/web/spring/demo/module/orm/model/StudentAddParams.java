package wxc.web.spring.demo.module.orm.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class StudentAddParams {

  private Integer clazzId;

  @Column
  private String name;

  @Column
  private Integer age;
}
