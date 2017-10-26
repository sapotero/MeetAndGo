package sapotero.meetandgo.application;

import timber.log.Timber;

public class Application extends android.app.Application {
  @Override
  public void onCreate() {
    super.onCreate();

    Timber.plant(new Timber.DebugTree());
  }
}
