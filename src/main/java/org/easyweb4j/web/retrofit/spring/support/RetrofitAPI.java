package org.easyweb4j.web.retrofit.spring.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * retrofit annotation indicate api interface scanned by retrofit importor.
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/05/21
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitAPI {

}
