package org.easyweb4j.web.core.context.spring.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.easyweb4j.web.core.context.spring.EasyWeb4JApplicationContextConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 注释，启用web的核心内容
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/31
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
  EasyWeb4JApplicationContextConfiguration.class
})
public @interface EnableEasyWeb4JCore {

}
