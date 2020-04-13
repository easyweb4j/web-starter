package org.easyweb4j.web.core.mvc;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.easyweb4j.web.core.exception.DataNotFoundException;
import org.easyweb4j.web.core.exception.ParameterInvalidException;
import org.easyweb4j.web.core.exception.SessionInvalidException;
import org.easyweb4j.web.core.exception.SystemInternalException;
import org.easyweb4j.web.core.mvc.entity.ResponseErrorCode;
import org.easyweb4j.web.core.mvc.entity.RestResponse;
import org.easyweb4j.web.core.mvc.entity.impl.DefaultRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用的@RestController配置
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/08
 * @since 1.0
 */
@ControllerAdvice(annotations = {RestController.class})
public class CommonRestControllerAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommonRestControllerAdvice.class);

  @Autowired(required = false)
  private MessageSource messageSource;

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public RestResponse<Void> defaultExceptionHandler(Throwable throwable) {
    LOGGER.error("default exception handler", throwable);
    return buildRestResponse(null, ResponseErrorCode.ERR_RES_SERVER_DEFAULT.getErrorCode());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public RestResponse<Void> systemInternalException(SystemInternalException ex) {
    LOGGER.error("systemInternalException exception handler", ex);
    return buildRestResponse(null, ResponseErrorCode.ERR_RES_SERVER_DEFAULT.getErrorCode());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestResponse<String> parameterInvalidException(ParameterInvalidException ex) {
    LOGGER.error("parameterInvalidException exception handler: name={},value={},clz={}",
      ex.getParamName(), ex.getValue(), ex.getParamClz(), ex);
    return buildRestResponse(ex.getParamName(),
      ResponseErrorCode.ERR_RES_PARAM_INVALID.getErrorCode());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public RestResponse<Void> sessionInvalidException(SessionInvalidException ex) {
    LOGGER.error("sessionInvalidException exception handler: user={}", ex.getUser(), ex);
    return buildRestResponse(null, ResponseErrorCode.ERR_RES_PARAM_INVALID.getErrorCode());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public RestResponse<Void> dataNotFoundException(DataNotFoundException ex) {
    LOGGER.error("dataNotFoundException exception handler", ex);
    return buildRestResponse(null, ResponseErrorCode.ERR_RES_DATA_NOTFOUND.getErrorCode());
  }

  private <T extends Object> RestResponse<T> buildRestResponse(T root, String errorCode) {
    String errorMsg =
      StringUtils.isBlank(errorCode) ? ResponseErrorCode.ERR_RES_SERVER_DEFAULT.getErrorCode() :
        (null == messageSource ? ResponseErrorCode.ERR_RES_SERVER_DEFAULT.getErrorCode() :
          messageSource.getMessage(errorCode, null, Locale.getDefault()));
    return DefaultRestResponse.builder()
      .errorCode(errorCode)
      .errorMessage(errorMsg)
      .build();
  }
}
