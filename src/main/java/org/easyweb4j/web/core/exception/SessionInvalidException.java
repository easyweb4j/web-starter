package org.easyweb4j.web.core.exception;

/**
 * 无效会话错误
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/01
 * @since 1.0
 */
public class SessionInvalidException extends RuntimeException {

  protected Object user;

  public SessionInvalidException(Object user) {
    this.user = user;
  }

  public Object getUser() {
    return user;
  }
}
