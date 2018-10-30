package wxc.web.spring.demo.module.role.controller;

import wxc.web.spring.demo.base.model.CommonPageDTO;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.module.role.model.RoleAddParams;
import wxc.web.spring.demo.module.role.model.RoleDTO;
import wxc.web.spring.demo.module.role.model.RolePageParams;
import wxc.web.spring.demo.module.role.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author chenhd
 */
@Api(tags = "角色")
@RestController
public class RoleController {

  private RoleService service;

  @Autowired
  public RoleController(RoleService service) {
    this.service = service;
  }

  @ApiOperation("获取角色列表")
  @GetMapping("/v1/role/list")
  public ResponseResult<CommonPageDTO<RoleDTO>> getRoleList(@Valid RolePageParams params) {
    CommonPageDTO<RoleDTO> pageDTO = service.getRoleList(params);
    return ResponseResultGenerator.success("获取成功", pageDTO);
  }

  @ApiOperation("添加角色")
  @PostMapping("/v1/role/add")
  public ResponseResult<Void> addRole(@RequestBody @Valid RoleAddParams params) throws ApiException {
    service.addRole(params);
    return ResponseResultGenerator.success("添加成功", null);
  }

  @ApiOperation("编辑角色")
  @PostMapping("/v1/role/edit/{id}")
  public ResponseResult<Void> editRole(@ApiParam("角色ID") @PathVariable("id") Long id, @RequestBody @Valid RoleAddParams params) throws ApiException {
    service.editRole(id, params);
    return ResponseResultGenerator.success("编辑成功", null);
  }

  @ApiOperation("删除角色")
  @PostMapping("/v1/role/delete/{id}")
  public ResponseResult<Void> deleteRole(@ApiParam("角色ID") @PathVariable("id") Long id) throws ApiException {
    service.deleteRole(id);
    return ResponseResultGenerator.success("删除成功", null);
  }

  @ApiOperation("角色授权")
  @PostMapping("/v1/role/grant/{id}")
  public ResponseResult<Void> grantPermission(@ApiParam("角色ID") @PathVariable("id") Long id,
                                              @ApiParam("权限IDs") @RequestBody @Valid @NotEmpty List<Long> permissionIds) throws ApiException {
    service.grantPermission(id, permissionIds);
    return ResponseResultGenerator.success("授权成功", null);
  }
}
