package wxc.web.spring.demo.module.permission.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.module.permission.model.PermissionAddParams;
import wxc.web.spring.demo.module.permission.model.SysPermissionDTO;
import wxc.web.spring.demo.module.permission.service.SysPermissionService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chenhd
 */
@Api(tags = "权限")
@RestController
public class SysPermissionController {

  private SysPermissionService service;

  @Autowired
  public SysPermissionController(SysPermissionService service) {
    this.service = service;
  }

  @ApiOperation("获取所有权限")
  @GetMapping("/v1/permission/all")
  public ResponseResult<List<SysPermissionDTO>> getAll() {
    return ResponseResultGenerator.success("获取成功", service.getAll());
  }

  @ApiOperation("添加权限")
  @PostMapping("/v1/permission/add")
  public ResponseResult<Void> addPermission(@RequestBody @Valid PermissionAddParams params) {
    service.addPermission(params);
    return ResponseResultGenerator.success("添加成功", null);
  }

  @ApiOperation("编辑权限")
  @PostMapping("/v1/permission/edit/{id}")
  public ResponseResult<Void> editPermission(@ApiParam("权限ID") @PathVariable("id") Long id, @RequestBody @Valid PermissionAddParams params) throws ApiException {
    service.editPermission(id, params);
    return ResponseResultGenerator.success("编辑成功", null);
  }

  @ApiOperation("删除权限")
  @PostMapping("/v1/permission/delete/{id}")
  public ResponseResult<Void> deletePermission(@ApiParam("权限ID") @PathVariable("id") Long id) throws ApiException {
    service.deletePermission(id);
    return ResponseResultGenerator.success("删除成功", null);
  }

}
