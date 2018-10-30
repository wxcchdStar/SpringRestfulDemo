package wxc.web.spring.demo.module.orm.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClazzAddParams {

  @NotNull
  private String name;

}
