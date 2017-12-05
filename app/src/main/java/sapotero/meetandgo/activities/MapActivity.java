package sapotero.meetandgo.activities;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

import java.util.ArrayList;

import sapotero.meetandgo.R;

import static org.osmdroid.tileprovider.util.StorageUtils.getStorage;

public class MapActivity extends AppCompatActivity {

  private static final int MENU_ZOOMIN_ID = Menu.FIRST;
  private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

  private MapView map;
  private ItemizedOverlay<OverlayItem> mMyLocationOverlay;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Context context = getApplicationContext();
    setContentView(R.layout.activity_map);
    //important! set your user agent to prevent getting banned from the osm servers
//    Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
    Configuration.getInstance().setUserAgentValue(getPackageName());
    Configuration.getInstance().setOsmdroidBasePath(getStorage());

    map = (MapView) findViewById(R.id.map);
    map.setTileSource(TileSourceFactory.USGS_SAT);
    map.setTilesScaledToDpi(true);


    addScaleBar();

    addComapss(context);

    addMiniMap(context);

    addPoints(context);

    addMyLocation(context);

    setZoom(map);

    setStartPoint(map);

  }

  private void addMyLocation(Context context) {
    SimpleLocationOverlay mLocationOverlay = new SimpleLocationOverlay(this);
    map.getOverlays().add(mLocationOverlay);
    map.setMultiTouchControls(true);
  }

  private void addScaleBar() {
    ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
    mScaleBarOverlay.setCentred(true);
    mScaleBarOverlay.setScaleBarOffset(100, 1800);
    map.getOverlays().add(mScaleBarOverlay);
  }

  private void addComapss(Context context) {
    CompassOverlay mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), map);
    mCompassOverlay.enableCompass();
    map.getOverlays().add(mCompassOverlay);
  }

  private void addMiniMap(Context context) {
    MinimapOverlay mMinimapOverlay = new MinimapOverlay(context, map.getTileRequestCompleteHandler());
    mMinimapOverlay.setWidth(250);
    mMinimapOverlay.setHeight(250);
    map.getOverlays().add(mMinimapOverlay);
  }

  private void addPoints(Context context) {
    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

    items.add(
      new OverlayItem("Point 1", "Description", new GeoPoint(55.751334, 37.618523))
    );
    items.add(
      new OverlayItem("Point 2", "Description", new GeoPoint(55.751454, 37.618623))
    );
    items.add(
      new OverlayItem("Point 3", "Description", new GeoPoint(55.751574, 37.618723))
    );

    ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(context, items, new OnItemGestureListener<OverlayItem>() {
      @Override
      public boolean onItemSingleTapUp(int index, OverlayItem item) {
        Toast.makeText(
          MapActivity.this,
          "Item '" + item.getTitle() + "' (index=" + index
            + ") got single tapped up", Toast.LENGTH_LONG).show();
        return true; // We 'handled' this event.
      }

      @Override
      public boolean onItemLongPress(int index, OverlayItem item) {
        Toast.makeText(
          MapActivity.this,
          "Item '" + item.getTitle() + "' (index=" + index
            + ") got long pressed", Toast.LENGTH_LONG).show();
        return false;
      }
    });
    mOverlay.setFocusItemsOnTap(true);

    map.getOverlays().add(mOverlay);
  }

  private void setStartPoint(MapView map) {
    IMapController mapController = map.getController();
    mapController.setZoom(24);
    GeoPoint startPoint = new GeoPoint(55.751244, 37.618423);
    mapController.setCenter(startPoint);
  }

  private void setZoom(MapView map) {
    map.setBuiltInZoomControls(true);
    map.setMultiTouchControls(true);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu pMenu) {
    pMenu.add(0, MENU_ZOOMIN_ID, Menu.NONE, "ZoomIn");
    pMenu.add(0, MENU_ZOOMOUT_ID, Menu.NONE, "ZoomOut");

    return true;
  }


  public void onResume(){
    super.onResume();
    //this will refresh the osmdroid configuration on resuming.
    //if you make changes to the configuration, use
    //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    //Configuration.getInstance().save(this, prefs);
    Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
  }
}
