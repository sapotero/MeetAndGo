package sapotero.meetandgo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

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
  }
}
