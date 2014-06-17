package com.example.wazzyeventos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
 
public class gpsControle extends Activity {
  private LatLng frameworkSystemLocation = new LatLng(-19.92550, -43.64058);
  private GoogleMap map;
 
  @SuppressLint("NewApi")
@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gpsloc);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    Marker frameworkSystem = map.addMarker(new MarkerOptions()
                                               .position(frameworkSystemLocation)
                                               .title("Framework System"));
    // Move a câmera para Framework System com zoom 15. 
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation , 15));
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
  }
 
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
 
}