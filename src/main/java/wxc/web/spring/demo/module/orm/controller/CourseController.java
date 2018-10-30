package wxc.web.spring.demo.module.orm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.module.orm.entity.Clazz;
import wxc.web.spring.demo.module.orm.entity.Course;
import wxc.web.spring.demo.module.orm.service.ClazzService;
import wxc.web.spring.demo.module.orm.service.CourseService;

import javax.validation.Valid;

@Api(tags = "课程")
@RestController
public class CourseController {

  private CourseService service;

  @Autowired
  public CourseController(CourseService service) {
    this.service = service;
  }

  @ApiOperation("列表")
  @GetMapping("/v1/course/list")
  public ResponseResult<Iterable<Course>> getAll() {
    return ResponseResultGenerator.success("获取成功", service.getList());
  }

  @ApiOperation("添加")
  @PostMapping("/v1/course/add")
  public ResponseResult<Void> add(@RequestBody @Valid Course params) {
    service.add(params);
    return ResponseResultGenerator.success("添加成功", null);
  }

  @ApiOperation("编辑")
  @PostMapping("/v1/course/edit/{id}")
  public ResponseResult<Void> edit(@ApiParam("ID") @PathVariable("id") Integer id, @RequestBody @Valid Course params) throws ApiException {
    service.edit(id, params);
    return ResponseResultGenerator.success("编辑成功", null);
  }

  @ApiOperation("删除")
  @PostMapping("/v1/course/delete/{id}")
  public ResponseResult<Void> delete(@ApiParam("ID") @PathVariable("id") Integer id) throws ApiException {
    service.delete(id);
    return ResponseResultGenerator.success("删除成功", null);
  }
}
