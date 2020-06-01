package org.easyweb4j.web.retrofit.spring.support;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.easyweb4j.web.core.exception.ParameterInvalidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * retrofit 默认bean注册类
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
public class RetrofitClientImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar,
  BeanClassLoaderAware {

  private static final Logger LOGGER = LoggerFactory
    .getLogger(RetrofitClientImportBeanDefinitionRegistrar.class);

  private static final String ENABLE_RETROFIT_ANNOTATION_CLZ =
    "org.easyweb4j.web.retrofit.spring.support.EnableRetrofit";

  private ClassLoader beanClassLoader;

  @Override
  public void registerBeanDefinitions(
    AnnotationMetadata importingClassMetadata,
    BeanDefinitionRegistry registry) {
    // parse EnableRetrofit args
    RetrofitConfig defaultConfig = parseDefaultRetrofitConfig(importingClassMetadata);
    RetrofitInstance[] instances = defaultConfig.instances;
    if (ArrayUtils.isEmpty(instances)) {
      throw new ParameterInvalidException(RetrofitInstance.class, "instances", null);
    }

    // get all retrofit instance configuration
    for (int i = 0; i < instances.length; i++) {
      RetrofitInstance instance = instances[i];
      // scan by base packages
      Set<Class<?>> apiInterfaces = scanAPI(instance);
      // register bean factory
      registerBeans(registry, i, defaultConfig, instance, apiInterfaces);
    }

  }

  private void registerBeans(BeanDefinitionRegistry registry, int beanIndex,
    RetrofitConfig defaultConfig,
    RetrofitInstance instance, Set<Class<?>> apiInterfaces) {
    if (CollectionUtils.isEmpty(apiInterfaces)) {
      LOGGER.error("no api interface found under");
      return;
    }

    GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
    genericBeanDefinition.setBeanClass(RetrofitInstanceFactoryBean.class);

    MutablePropertyValues propertyValues = genericBeanDefinition.getPropertyValues();

    propertyValues.addPropertyValue("baseURL",
      StringUtils.isBlank(instance.baseURL()) ? defaultConfig.baseURL : instance.baseURL());

    propertyValues.addPropertyValue("converterFactory",
      StringUtils.isBlank(instance.converterFactory()) ? defaultConfig.factory
        : instance.converterFactory());

    propertyValues.addPropertyValue("client",
      StringUtils.isBlank(instance.client()) ? defaultConfig.clientCustomizer
        : instance.client());

    propertyValues.addPropertyValue("callAdapterFactory",
      StringUtils.isBlank(instance.callAdapterFactory()) ? defaultConfig.callAdapterFactory
        : instance.callAdapterFactory());

    propertyValues.addPropertyValue("apiClasses", apiInterfaces);

    String beanName = "retrofit-instance-" + (beanIndex + 1);
    registry.registerBeanDefinition(beanName, genericBeanDefinition);

    // register api factory bean
    Iterator<Class<?>> iterator = apiInterfaces.iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      Class<?> apiClz = iterator.next();

      GenericBeanDefinition apiGenericBeanDefinition = new GenericBeanDefinition();
      apiGenericBeanDefinition.setBeanClass(RetrofitAPIFactoryBean.class);
      MutablePropertyValues apiPropertyValues = apiGenericBeanDefinition.getPropertyValues();
      apiPropertyValues.addPropertyValue("retrofitBeanName", beanName);
      apiPropertyValues.addPropertyValue("apiClass", apiClz);
      registry.registerBeanDefinition(beanName + "-" + (i + 1), apiGenericBeanDefinition);
    }
  }

  private Set<Class<?>> scanAPI(RetrofitInstance instance) {
    if (ArrayUtils.isEmpty(instance.basePackages())) {
      return null;
    }

    Set<Class<?>> resultClass = new HashSet<>();
    for (String basePackage : instance.basePackages()) {
      if (StringUtils.isAllBlank(basePackage)) {
        LOGGER.warn("basePackage empty");
        continue;
      }

      ClassPath classPath = null;
      try {
        classPath = ClassPath.from(beanClassLoader);
      } catch (IOException e) {
        LOGGER.error("classpath creation: {}", getClass().getClassLoader(), e);
        continue;
      }

      ImmutableSet<ClassInfo> topLevelClassesRecursive = classPath
        .getTopLevelClassesRecursive(basePackage);
      if (topLevelClassesRecursive.isEmpty()) {
        continue;
      }

      UnmodifiableIterator<ClassInfo> iterator = topLevelClassesRecursive.iterator();
      while (iterator.hasNext()) {
        ClassInfo classInfo = iterator.next();

        if (!classInfo.load().isAnnotationPresent(RetrofitAPI.class)) {
          continue;
        }

        resultClass.add(classInfo.load());
      }

    }

    return resultClass;
  }

  private RetrofitConfig parseDefaultRetrofitConfig(AnnotationMetadata importingClassMetadata) {
    if (!importingClassMetadata.hasAnnotation(ENABLE_RETROFIT_ANNOTATION_CLZ)) {
      throw new ParameterInvalidException(EnableRetrofit.class, "annotation", null);
    }

    Map<String, Object> annotationAttributes = importingClassMetadata
      .getAnnotationAttributes(ENABLE_RETROFIT_ANNOTATION_CLZ);

    RetrofitConfig retrofitConfig = new RetrofitConfig();
    for (Entry<String, Object> entry : annotationAttributes.entrySet()) {
      switch (entry.getKey()) {
        case "converterFactory":
          retrofitConfig.factory = (String) entry.getValue();
          break;
        case "callAdapterFactory":
          retrofitConfig.callAdapterFactory = (String) entry.getValue();
          break;
        case "baseURL":
          retrofitConfig.baseURL = (String) entry.getValue();
          break;
        case "instance":
          retrofitConfig.instances = (RetrofitInstance[]) entry.getValue();
          break;
        case "client":
          retrofitConfig.clientCustomizer = (String) entry.getValue();
          break;
        case "basePackage":
          retrofitConfig.basePackage = (String) entry.getValue();
          break;
        default:
          LOGGER.error("unexpected annotation attributes: annotation={} attribute-name={}",
            ENABLE_RETROFIT_ANNOTATION_CLZ, entry.getKey());
          break;
      }

    }
    return retrofitConfig;
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    beanClassLoader = classLoader;
  }

  private static class RetrofitConfig {

    String factory;
    String callAdapterFactory;
    String baseURL;
    String basePackage;
    RetrofitInstance[] instances;
    String clientCustomizer;
  }
}
