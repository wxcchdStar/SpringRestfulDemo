package wxc.web.spring.demo.module.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wxc.web.spring.demo.base.model.CommonPageDTO;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.module.user.model.*;
import wxc.web.spring.demo.module.user.service.UserService;

import javax.validation.Valid;

/**
 * @author chenhd
 */
@Api(tags = "用户")
@RestController
public class UserController {

  private UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @ApiOperation(value = "登录")
  @PostMapping("/v1/login")
  public ResponseResult<LoginDTO> login(@RequestBody @Valid LoginParams params) throws ApiException {
    LoginDTO loginDTO = service.login(params.getUsername(), params.getPassword(), params.getPlatform());
    return ResponseResultGenerator.success("登录成功", loginDTO);
  }

  @ApiOperation(value = "退出登录")
  @GetMapping("/v1/logout")
  public ResponseResult<String> logout() {
    service.logout();
    return ResponseResultGenerator.success("退出登录成功", null);
  }

  @ApiOperation(value = "添加用户")
  @PostMapping("/v1/user/add")
  public ResponseResult<Void> addUser(@RequestBody @Valid UserAddParams params) throws ApiException {
    service.addUser(params);
    return ResponseResultGenerator.success("添加成功", null);
  }

  @ApiOperation(value = "编辑用户")
  @PostMapping("/v1/user/edit/{id}")
  public ResponseResult<Void> editUser(@PathVariable Long id, @RequestBody @Valid UserEditParams params) throws ApiException {
    service.editUser(id, params);
    return ResponseResultGenerator.success("编辑成功", null);
  }

  @ApiOperation(value = "删除用户")
  @GetMapping("/v1/user/delete/{id}")
  public ResponseResult<Void> deleteUser(@PathVariable Long id) throws ApiException {
    service.deleteUser(id);
    return ResponseResultGenerator.success("删除成功", null);
  }

  @ApiOperation(value = "获取所有用户")
  @GetMapping("/v1/user/list")
  public ResponseResult<CommonPageDTO<UserDTO>> findUserList(@Valid UserSearchParams params) {
    CommonPageDTO<UserDTO> userPageDTO = service.findUserList(params);
    return ResponseResultGenerator.success("查询成功", userPageDTO);
  }

  @ApiOperation(value = "修改密码")
  @PostMapping("/v1/user/modify-password")
  public ResponseResult<Void> modifyPassword(@RequestBody @Valid ModifyPasswordParams params) throws ApiException {
    service.modifyPassword(params);
    return ResponseResultGenerator.success("操作成功", null);
  }

  @ApiOperation(value = "重置密码")
  @PostMapping("/v1/user/reset-password/{id}")
  public ResponseResult<Void> resetPassword(@PathVariable Long id, @RequestBody @Valid ResetPasswordParams params) {
    service.resetPassword(id, params);
    return ResponseResultGenerator.success("操作成功", null);
  }
}
