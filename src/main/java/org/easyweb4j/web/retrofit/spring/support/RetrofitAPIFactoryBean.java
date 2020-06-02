package org.easyweb4j.web.retrofit.spring.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import retrofit2.Retrofit;

/**
 * retrofit 实例创建API
 *
 * @param <T> 枚举对象
 * @author Ray(linxray @ gmail.com)
 * @date 2020/06/01
 * @since 1.0
 */
public class RetrofitAPIFactoryBean<T extends Object> implements FactoryBean<T>,
  ApplicationContextAware {
  protected String retrofitBeanName;
  protected Class<T> apiClass;
  protected ApplicationContext applicationContext;

  @Override
  public T getObject() throws Exception {
    Retrofit retrofit = (Retrofit) applicationContext.getBean(retrofitBeanName);
    return retrofit.create(apiClass);
  }

  @Override
  public Class<?> getObjectType() {
    return apiClass;
  }

  public void setApiClass(Class<T> apiClass) {
    this.apiClass = apiClass;
  }

  public void setRetrofitBeanName(String retrofitBeanName) {
    this.retrofitBeanName = retrofitBeanName;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
