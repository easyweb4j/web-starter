package org.easyweb4j.web.core.mvc.spring.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.easyweb4j.web.core.mvc.spring.config.ExceptionConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 异常处理相关配置
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/13
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
  ExceptionConfiguration.class
})
public @interface EnableEasyWeb4JException {

}
