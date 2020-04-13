package org.easyweb4j.web.core.mvc.entity;

/**
 * 响应错误代码,内置
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/13
 * @since 1.0
 */
public enum ResponseErrorCode {
  ERR_RES_SERVER_DEFAULT, ERR_RES_PARAM_INVALID, ERR_RES_DATA_NOTFOUND;

  public String getErrorCode() {
    return name().toLowerCase().replace("_", ".");
  }
}
