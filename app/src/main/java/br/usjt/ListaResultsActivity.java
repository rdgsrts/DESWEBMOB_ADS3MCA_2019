package br.usjt;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListaResultsActivity extends AppCompatActivity {

    private static final String TAG = "erro-android";

    private static final int REQUEST_CODE_GPS = 1001;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double locationTextView;
    private double latitudeAtual;
    private double longitudeAtual;
    private ListView gpsListView;
    private double locations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                latitudeAtual = lat;
                longitudeAtual = lon;
                locationTextView = lat + lon;
                Log.i(TAG,"onLocationChanged");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        setContentView(R.layout.activity_lista_results);
        Log.i(TAG,"onCreate-ListaResultsActivity");
        gpsListView = findViewById(R.id.gpsListView);
        locations = locationTextView;
        final List<Double> locations = (List<Double>) busca(locationTextView);
        ArrayAdapter <Double> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        gpsListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, locationListener);
        }else{
            ActivityCompat.requestPermissions(
                    this,new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, REQUEST_CODE_GPS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GPS){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permissão concedida, ativamos o GPS
                if (ActivityCompat.checkSelfPermission(
                        this,Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,120000, 200, locationListener);
                }
            }else{
                //usuário negou, não ativamos
                Toast.makeText(this, getString(R.string.no_gps_no_app), Toast.LENGTH_SHORT).show();
            }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Double> busca (double location){
        List<Double> locations = geraListaGps();
        if(location != 0){
            return locations;
        }
        List <Double> resultado = new ArrayList<>();
        for(Double localizacao : locations){
                resultado.add(localizacao);
        }
        return resultado;
    }

    public ArrayList<Double> geraListaGps(){
        Log.i(TAG,"geraListaErro");
        ArrayList<Double> lista = new ArrayList<>();
        for(int cont = 0 ; cont <= 50 ; cont++) {
            lista.add(locations);
        }
        return lista;
    }
}
