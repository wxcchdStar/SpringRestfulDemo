package wxc.web.spring.demo.base.response;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一异常处理器
 *
 * @author chenhd
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

  private static final String COMMON_ERROR_MESSAGE = "请求参数错误";
  
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseResult handleException(Exception e) {
    if (e instanceof MethodArgumentNotValidException) {
      // 请求参数校验失败
      return ResponseResultGenerator.error(COMMON_ERROR_MESSAGE, getParamsValidMessage((MethodArgumentNotValidException) e));
    } else if (e instanceof HttpMessageNotReadableException) {
      // 没有请求参数
      return ResponseResultGenerator.error(COMMON_ERROR_MESSAGE, "没有请求参数");
    } else if (e instanceof IllegalArgumentException
        || e instanceof MissingServletRequestParameterException) {
      // 其他请求参数错误
      return ResponseResultGenerator.error(COMMON_ERROR_MESSAGE, e.getMessage());
    } else if (e instanceof ExpiredJwtException) {
      // 登录过期
      return ResponseResultGenerator.create(Code.LOGIN_EXPIRED, "登录过期", e.getMessage());
    } else if (e instanceof AccessDeniedException) {
      // 用户无权限
      return ResponseResultGenerator.create(Code.NO_PERMISSIONS, "无权限", e.getMessage());
    } else if (e instanceof ApiException) {
      // 逻辑错误
      ApiException apiException = (ApiException) e;
      return ResponseResultGenerator.create(apiException.getCode(), apiException.getMessage(), null);
    }
    log.warn("API未知异常", e);
    return ResponseResultGenerator.error("未知错误", e.getMessage());
  }
  
  private static List<Map<String, String>> getParamsValidMessage(MethodArgumentNotValidException e) {
    BindingResult result = e.getBindingResult();
    List<ObjectError> errors = result.getAllErrors();
    List<Map<String, String>> list = new ArrayList<>();
    for (ObjectError error : errors) {
      Map<String, String> map = new HashMap<>(1);
      map.put("rule", error.getCodes()[0]);
      map.put("message", error.getDefaultMessage());
      list.add(map);
    }
    return list;
  }
}
