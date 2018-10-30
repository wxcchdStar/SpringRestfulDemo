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
import wxc.web.spring.demo.module.orm.entity.Student;
import wxc.web.spring.demo.module.orm.model.ClazzAddParams;
import wxc.web.spring.demo.module.orm.service.ClazzService;
import wxc.web.spring.demo.module.orm.service.StudentService;

import javax.validation.Valid;

@Api(tags = "班级")
@RestController
public class ClazzController {

  private ClazzService service;

  @Autowired
  public ClazzController(ClazzService service) {
    this.service = service;
  }

  @ApiOperation("列表")
  @GetMapping("/v1/clazz/list")
  public ResponseResult<Iterable<Clazz>> getAll() {
    return ResponseResultGenerator.success("获取成功", service.getList());
  }

  @ApiOperation("添加")
  @PostMapping("/v1/clazz/add")
  public ResponseResult<Void> add(@RequestBody @Valid ClazzAddParams params) {
    service.add(params);
    return ResponseResultGenerator.success("添加成功", null);
  }

  @ApiOperation("编辑")
  @PostMapping("/v1/clazz/edit/{id}")
  public ResponseResult<Void> edit(@ApiParam("ID") @PathVariable("id") Integer id, @RequestBody @Valid ClazzAddParams params) throws ApiException {
    service.edit(id, params);
    return ResponseResultGenerator.success("编辑成功", null);
  }

  @ApiOperation("删除")
  @PostMapping("/v1/clazz/delete/{id}")
  public ResponseResult<Void> delete(@ApiParam("ID") @PathVariable("id") Integer id) throws ApiException {
    service.delete(id);
    return ResponseResultGenerator.success("删除成功", null);
  }
}
