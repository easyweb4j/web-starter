package org.easyweb4j.web.core.mvc.entity.impl;

import com.google.auto.value.AutoValue;
import org.easyweb4j.web.core.mvc.entity.RestResponse;

@AutoValue
public abstract class DefaultRestResponse<T extends Object> implements RestResponse<T> {

  public static Builder builder() {
    return new AutoValue_DefaultRestResponse.Builder();
  }

  abstract String errorCode();

  abstract String errorMessage();

  abstract T root();

  @Override
  public String getErrorCode() {
    return errorCode();
  }

  @Override
  public String getErrorMessage() {
    return errorMessage();
  }

  @Override
  public T getRoot() {
    return root();
  }

  @AutoValue.Builder
  public interface Builder<T> {

    Builder<T> errorCode(String errorCode);

    Builder<T> errorMessage(String errorMessage);

    Builder<T> root(T root);

    DefaultRestResponse<T> build();
  }
}
