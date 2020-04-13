package org.easyweb4j.web.core.context.support;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.GenericConverter;

@Configuration
public class ConversionServiceConfiguration {

  @Bean
  ConversionServiceFactoryBean conversionService(
    Set<GenericConverter> genericConverters,
    Set<Converter<?, ?>> converters,
    Set<ConverterFactory<?, ?>> converterFactories
  ) {
    Set converterSet = new HashSet<>();
    if (CollectionUtils.isNotEmpty(genericConverters)) {
      converterSet.addAll(genericConverters);
    }
    if (CollectionUtils.isNotEmpty(converters)) {
      converterSet.addAll(converters);
    }
    if (CollectionUtils.isNotEmpty(converterFactories)) {
      converterSet.addAll(converterFactories);
    }
    ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
    conversionServiceFactoryBean.setConverters(converterSet);
    return conversionServiceFactoryBean;
  }
}
