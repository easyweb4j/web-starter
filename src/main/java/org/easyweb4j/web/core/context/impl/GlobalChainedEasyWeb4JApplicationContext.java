package org.easyweb4j.web.core.context.impl;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.easyweb4j.web.core.context.ChainedEasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;

public class GlobalChainedEasyWeb4JApplicationContext<K extends Object, V extends Object> implements
  ChainedEasyWeb4JApplicationContext<K, V> {

  private final static ConcurrentHashMap<Object, Object> INTERNAL_STORAGE = new ConcurrentHashMap<>();
  private EasyWeb4JApplicationContext<K, V> parent;

  @Override
  public boolean setParent(
    EasyWeb4JApplicationContext<K, V> parentContext) {
    if (null != parentContext) {
      this.parent = parentContext;
      return true;
    }
    return false;
  }

  @Override
  public Optional<V> get(K key) {
    if (null == key) {
      return Optional.empty();
    }

    Object v = INTERNAL_STORAGE.get(key);
    return null != v ? Optional.of((V) v) : (
      null == parent ? Optional.empty() : parent.get(key)
    );
  }

  @Override
  public boolean set(K key, V value) {
    if (null == key || null == value) {
      return false;
    }

    INTERNAL_STORAGE.put(key, value);
    return true;
  }

  @Override
  public void clear() {
    INTERNAL_STORAGE.clear();
  }
}
