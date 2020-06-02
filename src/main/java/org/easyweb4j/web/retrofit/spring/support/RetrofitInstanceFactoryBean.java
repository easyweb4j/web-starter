package org.easyweb4j.web.retrofit.spring.support;

import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.easyweb4j.web.core.exception.ParameterInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import retrofit2.CallAdapter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

/**
 * 通过此类注册到spring，达到使用注释配置retrofit的目的
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
public class RetrofitInstanceFactoryBean<T extends Object> implements FactoryBean<T>,
  ApplicationContextAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(RetrofitInstanceFactoryBean.class);

  protected String baseURL;
  protected String converterFactory;
  protected String callAdapterFactory;
  protected String client;
  protected ApplicationContext applicationContext;

  @Autowired
  private Environment environment;

  @Override
  public T getObject() throws Exception {
    if (StringUtils.isBlank(baseURL)) {
      throw new ParameterInvalidException(String.class, "baseURL", baseURL);
    }

    // find beans
    Factory converterFactoryBean = getBean(converterFactory, Factory.class);
    CallAdapter.Factory callAdapterFactoryBean = getBean(callAdapterFactory,
      CallAdapter.Factory.class);
    RetrofitClientCustomizer clientCustomizerBean = getBean(client, RetrofitClientCustomizer.class);
    // build client
    // custmize client
    // build retrofit instance
    Builder builder = new Builder().baseUrl(resolveDependency(baseURL));
    if (null != converterFactoryBean) {
      builder.addConverterFactory(converterFactoryBean);
    }
    if (null != callAdapterFactoryBean) {
      builder.addCallAdapterFactory(callAdapterFactoryBean);
    }

    customizeHttpClient(builder, clientCustomizerBean);

    return (T) builder.build();
  }

  private void customizeHttpClient(Builder builder, RetrofitClientCustomizer clientCustomizerBean) {
    if (null == clientCustomizerBean) {
      return;
    }

    OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
    clientCustomizerBean.customize(httpClientBuilder);

    builder.client(httpClientBuilder.build());
  }

  private String resolveDependency(String baseURL) {
    StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(
      environment);
    ExpressionParser expressionParser = new SpelExpressionParser();
    return expressionParser.parseExpression(baseURL)
      .getValue(standardEvaluationContext, String.class);
  }

  @Override
  public Class<?> getObjectType() {
    return Retrofit.class;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public void setBaseURL(String baseURL) {
    this.baseURL = baseURL;
  }

  public void setConverterFactory(String converterFactory) {
    this.converterFactory = converterFactory;
  }

  public void setCallAdapterFactory(String callAdapterFactory) {
    this.callAdapterFactory = callAdapterFactory;
  }

  public void setClient(String client) {
    this.client = client;
  }

  private <E extends Object> E getBean(String beanName, Class<E> clz) {
    try {
      if (StringUtils.isNoneBlank(beanName)) {
        return applicationContext.getBean(beanName, clz);
      }
    } catch (NoSuchBeanDefinitionException e) {
      LOGGER.debug("can not find bean: {}", beanName, e);
    }
    return null;
  }
}
