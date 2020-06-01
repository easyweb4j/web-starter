package org.easyweb4j.web.retrofit.spring.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import retrofit2.CallAdapter;

/**
 * retrofit库
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
  RetrofitClientImportBeanDefinitionRegistrar.class
})
public @interface EnableRetrofit {

  /**
   * 全局默认的转换工厂bean name, 被{@link RetrofitInstance#converterFactory()}覆盖, 默认json
   *
   * @return
   */
  String converterFactory();

  /**
   * 全局默认的适配工厂bean name, 被{@link RetrofitInstance#callAdapterFactory()}覆盖
   *
   * @return
   */
  Class<? extends CallAdapter.Factory> callAdapterFactory();

  /**
   * 基础地址, 支持如{@see org.springframework.beans.factory.annotation.Value}的变量解析, 被{@link
   * RetrofitInstance#baseURL()}覆盖
   *
   * @return
   */
  String baseURL();

  /**
   * 多个示例配置
   *
   * @return
   */
  RetrofitInstance[] instance();

  /**
   * 全局客户端配置bean name, {@link RetrofitInstance#client()} 覆盖
   *
   * @return
   */
  String client();

  /**
   * default base package for api scanning.{@link RetrofitInstance#basePackages()} override
   */
  String basePackage();
}
