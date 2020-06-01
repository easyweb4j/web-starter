package org.easyweb4j.web.retrofit.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于retrofit 接口实现
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitClient {

}
