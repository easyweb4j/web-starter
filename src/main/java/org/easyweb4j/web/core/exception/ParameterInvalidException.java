package org.easyweb4j.web.core.exception;

/**
 * 无效参数错误
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/01
 * @since 1.0
 */
public class ParameterInvalidException extends RuntimeException {

  protected Class<?> paramClz;
  protected String paramName;
  protected Object value;

  public ParameterInvalidException(Class<?> paramClz, String paramName, Object value) {
    this.paramClz = paramClz;
    this.paramName = paramName;
    this.value = value;
  }

  public Class<?> getParamClz() {
    return paramClz;
  }

  public String getParamName() {
    return paramName;
  }

  public Object getValue() {
    return value;
  }
}
