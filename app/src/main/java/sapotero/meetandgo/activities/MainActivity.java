package sapotero.meetandgo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import sapotero.meetandgo.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.email)    TextView email;
  @BindView(R.id.password) TextView password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    Bundle bundle = getIntent().getExtras();
    String name  = "default_login";
    String pass  = "default_password";

    if(bundle != null){
      name = bundle.getString("email");
      pass = bundle.getString("password");
    }

    email.setText(name);
    password.setText(pass);

    Subscription subscription = Observable
      .interval(1000L, TimeUnit.MILLISECONDS)
      .timeInterval()
      .observeOn(Schedulers.computation())
      .subscribeOn(AndroidSchedulers.mainThread())
      .subscribe(
        new Action1<TimeInterval<Long>>() {
          @Override
          public void call(TimeInterval<Long> s) {
            Timber.d("EXECUTED");
          }
        },
        new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {

          }
        }
      );
  }
}
