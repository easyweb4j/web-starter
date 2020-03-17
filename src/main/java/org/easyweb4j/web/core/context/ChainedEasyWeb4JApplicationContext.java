package org.easyweb4j.web.core.context;

/**
 * 串联的上下文，如当前类找不到值，则找父类
 *
 * @param <String> key
 * @param <T>      value class
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/16
 * @since 1.0
 */
public interface ChainedEasyWeb4JApplicationContext<String, T extends Object> extends
  EasyWeb4JApplicationContext<String, T> {

  boolean setParent(EasyWeb4JApplicationContext<String, T> parentContext);
}
