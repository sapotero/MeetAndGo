package sapotero.meetandgo.activities;

import android.app.Notification;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sapotero.meetandgo.R;
import sapotero.meetandgo.adapter.CommentAdapter;
import sapotero.meetandgo.model.Comment;
import sapotero.meetandgo.model.UserApi;
import sapotero.meetandgo.model.wrapper.CommentWrapper;
import sapotero.meetandgo.retrofit.CommentService;
import sapotero.meetandgo.retrofit.UserService;
import timber.log.Timber;

public class UserInfoActivity extends AppCompatActivity {

  private int id = 4;

  @BindView(R.id.commentList) RecyclerView commentList;
  @BindView(R.id.commentField) EditText commentField;
  private CommentAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);
    ButterKnife.bind(this);

    Bundle bundle = getIntent().getExtras();

    if(bundle != null){
      id = bundle.getInt("id");
    }

    createAdapter();

    Observable
      .interval(1000, 5000, TimeUnit.MILLISECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Action1<Long>() {
        public void call(Long aLong) {
          getUserInfo();
        }
      });

  }

  private void createAdapter() {
    adapter = new CommentAdapter(this);
    commentList.setLayoutManager( new LinearLayoutManager(this));
    commentList.setAdapter(adapter);
  }

  private void getUserInfo() {

    Retrofit retrofit = getRetrofit();

    UserService userService = retrofit.create(UserService.class);

    userService
      .getUserInfo( String.valueOf(id) )
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<UserApi>() {
          @Override
          public void call(UserApi user) {


            if ( user.getCommentList().isEmpty() ){
              Timber.d("no comments");
            } else {
              for (Comment comment: user.getCommentList()) {
                Timber.d("%s - %s", comment.getDate(), comment.getBody());
                adapter.add(comment);
                commentList.smoothScrollToPosition(adapter.getItemCount() - 1);
              }
            }
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

  @NonNull
  private Retrofit getRetrofit() {
    OkHttpClient okhttp = new OkHttpClient.Builder()
      .readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
          Request request = chain.request();
          Log.e(getClass().getName(), request.method() + " " + request.url());
          RequestBody rb = request.body();
          Buffer buffer = new Buffer();
          if (rb != null)
            rb.writeTo(buffer);
          Timber.e( getClass().getName(), "Payload- " + buffer.readUtf8());
          return chain.proceed(request);
        }
      })
      .build();

    return new Retrofit.Builder()
      .client(okhttp)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl( "http://192.168.150.157:3000/" )
      .build();
  }

  private void showComment(String date, String body){

    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

    builder
            .setContentTitle(date)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon( R.drawable.icon )
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(Notification.VISIBILITY_PUBLIC);

    notificationManagerCompat.notify(
            new Random().nextInt(1000000),
            builder.build()
    );
  }

  @OnClick(R.id.notification)
  public void showNotification(View v){

    try {

      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

      builder
              .setContentTitle("Title")
              .setContentText("Content text")
              .setAutoCancel(true)
              .setSmallIcon( R.drawable.icon )
              .setDefaults(Notification.DEFAULT_ALL)
              .setPriority(NotificationCompat.PRIORITY_HIGH)
              .setVisibility(Notification.VISIBILITY_PUBLIC);

      notificationManagerCompat.notify(123, builder.build());

    } catch (Exception e){
      Timber.tag("ERROR").d(e);
    }

  }

  @OnClick(R.id.add_comment_button)
  public void addComment(View v){
    Retrofit retrofit = getRetrofit();

    CommentService userService = retrofit.create(CommentService.class);

    String json_m = new Gson().toJson(
      new CommentWrapper(
        new Comment(

          String.format("%s :%s", getCurrentUser(), commentField.getText().toString()),
          null,
          String.valueOf(id) )
      ), CommentWrapper.class
    );

    Timber.e("json: %s", json_m);

    RequestBody json = RequestBody.create(
      MediaType.parse("application/json"),
      json_m
    );

    userService
      .addComment( json )
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<Comment>() {
          @Override
          public void call(Comment comment) {

            Toast.makeText(UserInfoActivity.this, comment.toString(), Toast.LENGTH_SHORT).show();
            adapter.add(comment);
            commentList.smoothScrollToPosition(adapter.getItemCount() - 1);


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

  private String getCurrentUser(){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    RxSharedPreferences rxPreferences = RxSharedPreferences.create(preferences);
    return rxPreferences.getString("current_user", "аноним").get();
  }

}
