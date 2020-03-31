package org.easyweb4j.web.mybatis.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.AutoMappingUnknownColumnBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.easyweb4j.web.mybatis.core.scripting.EasyWeb4jApplicationContextAwareXMLLanguageDriver;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Easyweb4JMybatisAutoConfiguration {

  private final MybatisProperties properties;

  public Easyweb4JMybatisAutoConfiguration(
    MybatisProperties properties) {
    this.properties = properties;
  }

  @Bean
  public List<ConfigurationCustomizer> mybatisConfiguration() {
    List<ConfigurationCustomizer> customizers = new ArrayList<>();

    customizers.add(settings());

    return customizers;
  }

  private ConfigurationCustomizer settings() {
    return new ConfigurationCustomizer() {
      @Override
      @SuppressWarnings("unchecked")
      public void customize(org.apache.ibatis.session.Configuration configuration) {
        BiConsumer<String, Consumer<Object>> setIfAbsent = (key, consumer) -> {
          if (!properties.getConfigurationProperties().contains(key)) {
            consumer.accept(null);
          }
        };
        Function<String, Class<?>> resolveType = (clz) ->
          configuration.getTypeAliasRegistry().resolveAlias(clz);

        setIfAbsent
          .accept("lazyLoadingEnabled", (nullValue) -> configuration.setLazyLoadingEnabled(true));

        setIfAbsent.accept("autoMappingUnknownColumnBehavior", (nullValue) -> configuration
          .setAutoMappingUnknownColumnBehavior(AutoMappingUnknownColumnBehavior.WARNING));

        setIfAbsent.accept("defaultExecutorType", (nullValue) ->
          configuration.setDefaultExecutorType(ExecutorType.REUSE));

        setIfAbsent.accept("defaultStatementTimeout",
          (nullValue) -> configuration.setDefaultStatementTimeout(5));

        setIfAbsent.accept("defaultFetchSize",
          (nullValue) -> configuration.setDefaultFetchSize(1));

        setIfAbsent.accept("mapUnderscoreToCamelCase",
          (nullValue) -> configuration.setMapUnderscoreToCamelCase(true));

        setIfAbsent.accept("defaultScriptingLanguage",
          (nullValue) -> configuration.setDefaultScriptingLanguage(
            EasyWeb4jApplicationContextAwareXMLLanguageDriver.class));

        setIfAbsent.accept("defaultEnumTypeHandler",
          (nullValue) -> configuration.setDefaultEnumTypeHandler(EnumOrdinalTypeHandler.class));

        setIfAbsent.accept("logImpl",
          (nullValue) -> configuration
            .setLogImpl((Class<? extends Log>) resolveType.apply("SLF4J")));
      }
    };
  }

}
