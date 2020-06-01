package org.easyweb4j.web.retrofit.spring.support;

import okhttp3.OkHttpClient;

/**
 * 定制retrofit 客户端
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/04/14
 * @since 1.0
 */
public interface RetrofitClientCustomizer {

  void customize(OkHttpClient.Builder builder);
}
