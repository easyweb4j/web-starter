package org.easyweb4j.web.jackson.spring.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.easyweb4j.web.jackson.core.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

/**
 * Jackson仓库的支持
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/03
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
  JacksonAutoConfiguration.class
})
@ConditionalOnClass(ObjectMapper.class)
public @interface EnableEasyWeb4JJackson {

}
