package org.easyweb4j.web.mybatis.core.scripting;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * refactor {@see org.apache.ibatis.scripting.defaults.DefaultParameterHandler}, make code clean and
 * add context aware support
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/19
 * @since 1.0
 */
public class EasyWeb4jApplicationContextAwareParameterHandler implements ParameterHandler {

  private static final Logger LOGGER = LoggerFactory
    .getLogger(EasyWeb4jApplicationContextAwareParameterHandler.class);

  private final MappedStatement mappedStatement;
  private final Object parameterObject;
  private final BoundSql boundSql;
  private final Configuration configuration;
  private final EasyWeb4JApplicationContext<String, Object> easyWeb4JApplicationContext;

  public EasyWeb4jApplicationContextAwareParameterHandler(
    MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql,
    EasyWeb4JApplicationContext<String, Object> easyWeb4JApplicationContext
  ) {
    this.mappedStatement = mappedStatement;
    this.configuration = mappedStatement.getConfiguration();
    this.parameterObject = parameterObject;
    this.boundSql = boundSql;
    this.easyWeb4JApplicationContext = easyWeb4JApplicationContext;
  }


  @Override
  public Object getParameterObject() {
    return parameterObject;
  }

  @Override
  public void setParameters(PreparedStatement ps) {
    ErrorContext.instance().activity("setting parameters")
      .object(mappedStatement.getParameterMap().getId());
    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

    if (null == parameterMappings) {
      return;
    }

    for (int i = 0; i < parameterMappings.size(); i++) {
      ParameterMapping parameterMapping = parameterMappings.get(i);
      if (parameterMapping.getMode() == ParameterMode.OUT) {
        continue;
      }

      String propertyName = parameterMapping.getProperty();

      Object value = getValue(configuration, boundSql, parameterObject, propertyName);
      TypeHandler typeHandler = parameterMapping.getTypeHandler();
      JdbcType jdbcType = parameterMapping.getJdbcType();
      if (value == null && jdbcType == null) {
        jdbcType = configuration.getJdbcTypeForNull();
      }
      try {
        if (typeHandler.getClass().isInstance(value)) {
          typeHandler.setParameter(ps, i + 1, value, jdbcType);
        } else {
          LOGGER
            .warn("can not set parameter {} of type {} at index {}", propertyName, value.getClass(),
              i);
        }
      } catch (TypeException | SQLException e) {
        throw new TypeException(
          "Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
      }
    }
  }

  protected Object getValue(Configuration configuration, BoundSql boundSql, Object parameterObject,
    String propertyName) {
    Object value;
    if (boundSql
      .hasAdditionalParameter(propertyName)) {
      value = boundSql.getAdditionalParameter(propertyName);
    } else if (parameterObject == null) {
      value = getValueFromContext(propertyName);
    } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
      value = parameterObject;
    } else {
      MetaObject metaObject = configuration.newMetaObject(parameterObject);
      value = metaObject.getValue(propertyName);
      if (null == value) {
        value = getValueFromContext(propertyName);
      }
    }

    return value;
  }

  private Object getValueFromContext(String propertyName) {
    if (null == easyWeb4JApplicationContext) {
      LOGGER.warn("easyWeb4JApplicationContext not found");
      return null;
    }
    return easyWeb4JApplicationContext.get(propertyName);
  }


}
