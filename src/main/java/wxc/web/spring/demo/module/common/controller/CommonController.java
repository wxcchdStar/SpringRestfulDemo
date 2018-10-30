package wxc.web.spring.demo.module.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wxc.web.spring.demo.base.response.ApiException;
import wxc.web.spring.demo.base.response.ResponseResult;
import wxc.web.spring.demo.base.response.ResponseResultGenerator;
import wxc.web.spring.demo.module.common.service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenhd
 */
@Api(tags = "通用接口")
@RestController
public class CommonController {

  private CommonService service;

  @Autowired
  public CommonController(CommonService service) {
    this.service = service;
  }

  @ApiOperation("上传文件")
  @PostMapping("/v1/upload")
  public ResponseResult<String> upload(@RequestParam("file") MultipartFile file) throws ApiException {
    String url = service.upload(file);
    return ResponseResultGenerator.success("上传成功", url);
  }

  @ApiOperation("预览图片")
  @GetMapping("/v1/preview/**")
  public void preview(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String path = request.getRequestURI().substring("/v1/preview/".length());
    service.preview(path, response);
  }

  @ApiOperation("下载文件")
  @GetMapping("/v1/download/**")
  public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String path = request.getRequestURI().substring("/v1/download/".length());
    service.download(path, response);
  }
}
