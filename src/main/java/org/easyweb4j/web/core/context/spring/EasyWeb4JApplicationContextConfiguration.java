package org.easyweb4j.web.core.context.spring;

import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.GlobalChainedEasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.LocalChainedEasyWeb4JApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EasyWeb4JApplicationContextConfiguration {

  @Primary
  @Bean("webApplicationContext")
  public EasyWeb4JApplicationContext<String, Object> easyWeb4JApplicationContext() {
    GlobalChainedEasyWeb4JApplicationContext<String, Object> globalContext =
      new GlobalChainedEasyWeb4JApplicationContext<>();
    LocalChainedEasyWeb4JApplicationContext<String, Object> localApplicationContext
      = new LocalChainedEasyWeb4JApplicationContext<>();
    localApplicationContext.setParent(globalContext);
    return localApplicationContext;
  }

}
