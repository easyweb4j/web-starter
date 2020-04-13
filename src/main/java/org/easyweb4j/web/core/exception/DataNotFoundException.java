package org.easyweb4j.web.core.exception;

/**
 * 数据未找到异常
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/01
 * @since 1.0
 */
public class DataNotFoundException extends RuntimeException {

  public DataNotFoundException(String message) {
    super(message);
  }
}
