package sapotero.meetandgo.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.f2prateek.rx.preferences.RxSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sapotero.meetandgo.R;

public class SharedActivity extends AppCompatActivity {

  @BindView(R.id.text) EditText field;
  @BindView(R.id.load) Button load;
  @BindView(R.id.save) Button save;
  private RxSharedPreferences rxPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shared);
    ButterKnife.bind(this);

    initSharedPreferences();
  }

  private void initSharedPreferences() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    rxPreferences = RxSharedPreferences.create(preferences);
  }

  @OnClick(R.id.load)
  public void load(View v){
    if (rxPreferences != null){
      field.setText( rxPreferences.getString("someKey", "empty").get() );
    }
  }

  @OnClick(R.id.save)
  public void save(View v){
    if (rxPreferences != null){
      rxPreferences.getString("someKey").set( field.getText().toString());
    }
  }
}
