package org.easyweb4j.web.core.mvc.entity;

/**
 * 默认的响应类
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/08
 * @since 1.0
 */
public interface RestResponse<T extends Object> {

  /**
   * 错误代码，采用namespace的方式，以.分割; 如没有则代表成功
   *
   * @return
   */
  String getErrorCode();

  /**
   * 可读的错误信息
   *
   * @return
   */
  String getErrorMessage();

  /**
   * 具体数据内容
   *
   * @return
   */
  T getRoot();
}
