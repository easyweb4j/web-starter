package org.easyweb4j.web.mybatis.spring.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.easyweb4j.web.core.context.spring.config.EnableEasyWeb4JCore;
import org.easyweb4j.web.mybatis.spring.Easyweb4JMybatisAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 启用mybatis相关特性
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/31
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({
  Easyweb4JMybatisAutoConfiguration.class
})
@EnableEasyWeb4JCore
public @interface EnableEasyWeb4jMybatis {

}
