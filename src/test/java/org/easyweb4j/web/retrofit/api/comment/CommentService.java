package org.easyweb4j.web.retrofit.api.comment;

import java.util.List;
import org.easyweb4j.web.retrofit.api.data.Comment;
import org.easyweb4j.web.retrofit.spring.support.RetrofitAPI;
import org.easyweb4j.web.retrofit.spring.support.RetrofitInstance;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

@RetrofitAPI
public interface CommentService {

  @GET("comments")
  Call<List<Comment>> queryByPostID(@Query("postId") Integer postID);

}
