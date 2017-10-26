package sapotero.meetandgo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sapotero.meetandgo.R;

public class RegistrationActivity extends AppCompatActivity {

  @BindView(R.id.email)    EditText email;
  @BindView(R.id.password) EditText password;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.login)
  public void toMain(View v){

    Intent intent = new Intent(this, MainActivity.class);
    Bundle bundle = new Bundle();

    bundle.putString("email",    email.getText().toString() );
    bundle.putString("password", password.getText().toString() );
    intent.putExtras(bundle);

    startActivity(intent);

  }

  @OnClick(R.id.registration)
  public void toLogin(View v){

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();

  }
}
