package sapotero.meetandgo.retrofit;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import sapotero.meetandgo.model.UserApi;


public interface UserService  {
  @PUT("users.json")
  Observable<UserApi> createUser();

  @GET("users/{UID}.json")
  Observable<UserApi> getUserInfo(
    @Path("UID") String UID
  );

  @GET("users.json")
  Observable<ArrayList<UserApi>> getUserList();
}

