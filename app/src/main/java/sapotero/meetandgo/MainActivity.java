package sapotero.meetandgo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Bundle bundle = getIntent().getExtras();
    String name  = "default_login";
    String pass  = "default_password";

    if(bundle != null){
      name = bundle.getString("email");
      pass = bundle.getString("password");
    }

    TextView email    = (TextView) findViewById( R.id.email);
    TextView password = (TextView) findViewById( R.id.password);

    email.setText(name);
    password.setText(pass);
  }
}
