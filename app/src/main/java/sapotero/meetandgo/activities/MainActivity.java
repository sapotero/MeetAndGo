package sapotero.meetandgo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.requery.Persistable;
import io.requery.query.Tuple;
import io.requery.rx.SingleEntityStore;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sapotero.meetandgo.R;
import sapotero.meetandgo.adapter.UserAdapter;
import sapotero.meetandgo.application.Application;
import sapotero.meetandgo.db.UserEntity;
import sapotero.meetandgo.model.UserApi;
import sapotero.meetandgo.retrofit.UserService;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.email)    TextView email;
  @BindView(R.id.password) TextView password;
  @BindView(R.id.userRecycleView) RecyclerView userRecycleView;

  private UserAdapter adapter;
  private SingleEntityStore<Persistable> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setUserAdapter();

    initDb();


  }

  private void initDb() {
    data = ((Application) getApplication()).getData();

    data
      .select(UserEntity.EMAIL,UserEntity.PASSWORD, UserEntity.PHONE, UserEntity.INFO)
      .from(UserEntity.class)
      .get()
      .toObservable()
      .toList()
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(

        new Action1<List<Tuple>>() {
          @Override
          public void call(List<Tuple> tuples) {
            Timber.e("size: %s", tuples.size());

            for (Tuple tuple: tuples) {
              Timber.e("tuple: %s", tuple.toString());

              adapter.add(
                new UserApi(
                  tuple.get(0) == null ? "-" : tuple.get(0).toString(),
                  tuple.get(1) == null ? "-" : tuple.get(1).toString(),
                  tuple.get(2) == null ? "-" : tuple.get(2).toString(),
                  tuple.get(3) == null ? "-" : tuple.get(3).toString()
                ));
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

  @OnClick(R.id.updateUserInfo)
  public void addUser(){
    OkHttpClient okhttp = new OkHttpClient.Builder()
      .readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .build();

    Retrofit retrofit = new Retrofit.Builder()
      .client(okhttp)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl( "http://192.168.150.157:3000/" )
      .build();

    UserService userService = retrofit.create(UserService.class);

    userService
      .getUserInfo("4")
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<UserApi>() {
          @Override
          public void call(UserApi user) {
            Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
            adapter.add(user);
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

  @OnClick(R.id.addUsers)
  public void addUsers(){

    OkHttpClient okhttp = new OkHttpClient.Builder()
      .readTimeout(60, TimeUnit.SECONDS)
      .connectTimeout(10, TimeUnit.SECONDS)
      .build();

    Retrofit retrofit = new Retrofit.Builder()
      .client(okhttp)
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl( "http://192.168.150.157:3000/" )
      .build();

    UserService userService = retrofit.create(UserService.class);

    userService
      .getUserList()
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<ArrayList<UserApi>>() {
          @Override
          public void call(ArrayList<UserApi> userArrayList) {

            for (final UserApi user: userArrayList) {
//              Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();
              adapter.add(user);

              UserEntity newUser = new UserEntity();
              newUser.setEmail( user.getEmail() );
              newUser.setInfo( user.getInfo() );
              newUser.setPassword( user.getPassword() );
              newUser.setPhone( user.getPhone() );

              SingleEntityStore<Persistable> data = ((Application) getApplication()).getData();
              data
                .upsert(newUser)
                .toObservable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                  new Action1<UserEntity>() {
                    @Override
                    public void call(UserEntity userEntity) {
                      Timber.d( "new user inserted - %s", userEntity.getId() );
                    }
                  }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      Timber.e(throwable);
                    }
                  }
                );

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

  private void setUserAdapter() {
    adapter = new UserAdapter();
    userRecycleView.setLayoutManager( new LinearLayoutManager(this));
    userRecycleView.setAdapter(adapter);
  }

  private void executeOldCode() {
    Bundle bundle = getIntent().getExtras();
    String name  = "default_login";
    String pass  = "default_password";

    if(bundle != null){
      name = bundle.getString("email");
      pass = bundle.getString("password");
    }

    email.setText(name);
    password.setText(pass);
  }
}
