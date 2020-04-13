package org.easyweb4j.web.jackson.core;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson 库支持
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/03
 * @since 1.0
 */
@Configuration
public class JacksonAutoConfiguration {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer(
    List<Module> modules
  ) {
    return (jacksonObjectMapperBuilder -> {
      jacksonObjectMapperBuilder.featuresToEnable(
        // mapper features
        MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS,
        MapperFeature.USE_STD_BEAN_NAMING,
        // deserializer features
        DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
        // serializer features
        SerializationFeature.CLOSE_CLOSEABLE
      ).featuresToDisable(
        // deserializer features
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
      );

      // register modules
      if (CollectionUtils.isNotEmpty(modules)) {
        jacksonObjectMapperBuilder.modulesToInstall(modules.toArray(new Module[modules.size()]));
      }

    });
  }

}
