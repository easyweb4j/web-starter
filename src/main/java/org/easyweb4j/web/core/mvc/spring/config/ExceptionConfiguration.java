package org.easyweb4j.web.core.mvc.spring.config;

import org.easyweb4j.web.core.mvc.CommonControllerAdvice;
import org.easyweb4j.web.core.mvc.CommonRestControllerAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 异常配置类
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/13
 * @since 1.0
 */
@Configuration
@Import({
  CommonControllerAdvice.class,
  CommonRestControllerAdvice.class
})
public class ExceptionConfiguration {

}
