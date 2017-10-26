package sapotero.meetandgo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.email) EditText email;
  @BindView(R.id.password) EditText password;

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
  }
}
