package sapotero.meetandgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);
  }

  public void toMain(View v){

    Intent intent = new Intent(this, MainActivity.class);
    Bundle bundle = new Bundle();

    EditText email    = (EditText) findViewById( R.id.email );
    EditText password = (EditText) findViewById( R.id.password );

    bundle.putString("email",    email.getText().toString() );
    bundle.putString("password", password.getText().toString() );
    intent.putExtras(bundle);

    startActivity(intent);
    finish();

  }

  public void toLogin(View v){

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();

  }
}
