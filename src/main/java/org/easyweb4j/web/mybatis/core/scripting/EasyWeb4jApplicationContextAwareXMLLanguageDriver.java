package org.easyweb4j.web.mybatis.core.scripting;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.GlobalChainedEasyWeb4JApplicationContext;

/**
 * 自动注入上下文内容
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/19
 * @since 1.0
 */
public class EasyWeb4jApplicationContextAwareXMLLanguageDriver extends XMLLanguageDriver {

  private final EasyWeb4JApplicationContext<String, Object> easyWeb4JApplicationContext =
    new GlobalChainedEasyWeb4JApplicationContext<>();

  @SuppressWarnings("unchecked")
  @Override
  public ParameterHandler createParameterHandler(
    MappedStatement mappedStatement, Object parameterObject,
    BoundSql boundSql) {
    Object value =
      easyWeb4JApplicationContext.get(this.getClass().getName()).orElse(null);

    return new EasyWeb4jApplicationContextAwareParameterHandler(
      mappedStatement, parameterObject, boundSql,
      ((EasyWeb4JApplicationContext<String, Object>) value)
    );
  }

}
