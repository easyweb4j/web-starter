package org.easyweb4j.web.retrofit;

import java.io.IOException;
import java.util.List;
import org.easyweb4j.web.retrofit.api.comment.CommentService;
import org.easyweb4j.web.retrofit.api.post.PostService;
import org.easyweb4j.web.retrofit.api.data.Comment;
import org.easyweb4j.web.retrofit.api.data.Post;
import org.easyweb4j.web.retrofit.spring.support.EnableRetrofit;
import org.easyweb4j.web.retrofit.spring.support.RetrofitInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Retrofit 集成测试 1. 测试配置项 2. 测试上下文 3. 测试handler
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/06/02
 * @since 1.0
 */
@SpringBootTest
@SpringBootApplication
@EnableRetrofit(
  baseURL = "https://jsonplaceholder.typicode.com/", converterFactory = "jsonRetrofitFactory",
  instance = {
    @RetrofitInstance(basePackages = "org.easyweb4j.web.retrofit.api.post", baseURL = "${api.post.url}"),
    @RetrofitInstance(basePackages = "org.easyweb4j.web.retrofit.api.comment")
  },
  client = "retrofitClientCustomizer"
)
public class RetrofitTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  private PostService postService;
  @Autowired
  private CommentService commentService;

  @Test
  public void main() throws IOException, InterruptedException {
    Call<Post> postCall = postService.queryByID(1);
    Response<Post> execute = postCall.execute();
    Post post = execute.body();
    Assert.assertNotNull(post);
    Assert.assertEquals(post.getId(), Integer.valueOf(1));

    Thread.sleep(2000);

    Call<List<Comment>> commentCall = commentService.queryByPostID(1);
    Response<List<Comment>> response = commentCall.execute();
    List<Comment> commentList = response.body();
    Assert.assertNotNull(commentList);
    Assert.assertEquals(commentList.size(), 5);
  }


}
