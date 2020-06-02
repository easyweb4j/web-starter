package org.easyweb4j.web.retrofit.api.post;

import org.easyweb4j.web.retrofit.api.data.Post;
import org.easyweb4j.web.retrofit.spring.support.RetrofitAPI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@RetrofitAPI
public interface PostService {

  @GET("{id}")
  Call<Post> queryByID(@Path("id") Integer postID);

}
