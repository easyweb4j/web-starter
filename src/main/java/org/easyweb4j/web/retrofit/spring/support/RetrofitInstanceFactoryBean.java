package org.easyweb4j.web.retrofit.spring.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import retrofit2.Retrofit;

/**
 * 通过此类注册到spring，达到使用注释配置retrofit的目的
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
public class RetrofitInstanceFactoryBean<T extends Object> implements FactoryBean<T>,
  ApplicationContextAware {

  protected String baseURL;
  protected String converterFactory;
  protected String callAdapterFactory;
  protected String client;
  protected ApplicationContext applicationContext;

  @Override
  public T getObject() throws Exception {
    // TODO
    // find beans
    // build client
    // custmize client
    // build retrofit instance
    return null;
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

}
