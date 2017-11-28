package sapotero.meetandgo.application;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.rx.RxSupport;
import io.requery.rx.SingleEntityStore;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;
import sapotero.meetandgo.db.Models;
import timber.log.Timber;

public class Application extends android.app.Application {
  private SingleEntityStore<Persistable> dataStore;

  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
    getData();
  }

  public SingleEntityStore<Persistable> getData() {
    if (dataStore == null) {
      DatabaseSource source = new DatabaseSource(this, Models.DEFAULT, 2);
      source.setTableCreationMode(TableCreationMode.CREATE_NOT_EXISTS);
      Configuration configuration = source.getConfiguration();
      dataStore = RxSupport.toReactiveStore( new EntityDataStore<Persistable>(configuration) );;
    }
    return dataStore;
  }
}


