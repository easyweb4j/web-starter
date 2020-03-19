package org.easyweb4j.web.core.exception;

/**
 * 内部系统错误
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/17
 * @since 1.0
 */
public class SystemInternalException extends RuntimeException {

  public SystemInternalException(String message) {
    super(message);
  }
}
