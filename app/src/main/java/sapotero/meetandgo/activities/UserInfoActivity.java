package sapotero.meetandgo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sapotero.meetandgo.R;
import sapotero.meetandgo.model.UserApi;
import sapotero.meetandgo.retrofit.UserService;
import timber.log.Timber;

public class UserInfoActivity extends AppCompatActivity {

  private int id = 4;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);

    Bundle bundle = getIntent().getExtras();

    if(bundle != null){
      id = bundle.getInt("id");
    }

    OkHttpClient okhttp = new OkHttpClient.Builder()
      .readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .build();

    Retrofit retrofit = new Retrofit.Builder()
      .client(okhttp)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl( "http://192.168.160.245:3000/" )
      .build();

    UserService userService = retrofit.create(UserService.class);

    userService
      .getUserInfo( String.valueOf(id) )
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<UserApi>() {
          @Override
          public void call(UserApi user) {
            Toast.makeText(UserInfoActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
          }
        },
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            Timber.e(throwable);
          }
        }
      );

  }
}
