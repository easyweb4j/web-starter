package org.easyweb4j.web.core.context;

import java.util.Optional;

/**
 * 上下文接口, 线程安全
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/16
 * @since 1.0
 */
public interface EasyWeb4JApplicationContext<K extends Object, V extends Object> {

  Optional<V> get(K key);

  /**
   * set context value
   *
   * @param key   key to set
   * @param value value must not bu null
   * @return set true or false
   */
  boolean set(K key, V value);


  /**
   * clear all context data
   */
  void clear();
}
