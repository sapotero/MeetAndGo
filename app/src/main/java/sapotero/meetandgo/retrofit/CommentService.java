package sapotero.meetandgo.retrofit;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import sapotero.meetandgo.model.Comment;


public interface CommentService {
  @POST("comments.json")
  Observable<Comment> addComment(
    @Body RequestBody body
  );

}

