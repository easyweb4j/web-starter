package org.easyweb4j.web.retrofit.config;

import java.time.Duration;
import okhttp3.OkHttpClient.Builder;
import org.easyweb4j.web.retrofit.spring.support.RetrofitClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

  @Bean("jsonRetrofitFactory")
  public Converter.Factory jsonFactory() {
    return JacksonConverterFactory.create();
  }

  @Bean("retrofitClientCustomizer")
  public RetrofitClientCustomizer customizer() {
    return new RetrofitClientCustomizer() {
      @Override
      public void customize(Builder builder) {
        builder.connectTimeout(Duration.ofMillis(100000))
          .readTimeout(Duration.ofMillis(100000));
      }
    };
  }

}
