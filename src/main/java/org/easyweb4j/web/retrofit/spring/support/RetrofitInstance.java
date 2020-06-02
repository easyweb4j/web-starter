package org.easyweb4j.web.retrofit.spring.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置retrofit实例
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitInstance {

  /**
   * 默认转换类bean name
   *
   * @return
   */
  String converterFactory() default "";

  /**
   * 默认适配类bean name
   *
   * @return
   */
  String callAdapterFactory() default "";

  /**
   * 基础地址, 支持如{@see org.springframework.beans.factory.annotation.Value}的变量解析
   *
   * @return
   */
  String baseURL() default "";

  /**
   * 客户端配置类，基于spring获取应的bean
   *
   * @return
   */
  String client() default "";

  /**
   * 默认类扫描路径
   *
   * @return
   */
  String[] basePackages();
}
