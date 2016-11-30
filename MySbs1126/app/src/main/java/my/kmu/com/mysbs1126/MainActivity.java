package my.kmu.com.mysbs1126;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity {

    GoogleMap gmap;
    boolean locationTag = true;

    // 위치 관리자 얻기
    LocationManager locationManager;
    LocationListener locationListener;
    Button getLocation;
    TextView tv_location, tv_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 구글맵 띄우기
        gmap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // 위치 관리자 열기
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation     = (Button)findViewById(R.id.getLocation);
        tv_location     = (TextView)findViewById(R.id.tv_location);
        tv_address      = (TextView)findViewById(R.id.tv_address);

        // 제공자 목록 출력하기
        List<String> apProvider = locationManager.getProviders(false);
        String result = "";
        for (int i = 0; i < apProvider.size(); i++) {
            result += ("Provider" + i + " : " + apProvider.get(i) + "\n");
        }

        /*
            NO_REQUIREMENT : 상관없다
            ACCURACY_COARSE : 대충의 정밀도를 요구
            ACCURACY_FINE : 정밀한 정밀도를 요구
            POWER_HIGH : 배터리를 많이 사용해도 됨
            POWER_MEDIUM : 배터리를 중간 정도 사용해도 됨
            POWER_LOW : 배터리를 조금 사용해야 함
        */

        // 최적의 제공자 선택
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        String best = locationManager.getBestProvider(criteria, true);
        result += ("\n Best provider : " + best + "\n\n");

        // GPS와 네트워크 제공자를 사용가능한지 검사
        result += LocationManager.GPS_PROVIDER + " : "
                + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) + "\n";
        result += LocationManager.NETWORK_PROVIDER + " : "
                + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) + "\n";
        result += LocationManager.PASSIVE_PROVIDER + " : "
                + locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER) + "\n";

        // 결과 출력
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(result);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if(locationTag){
                    LatLng loadPoint = new LatLng(location.getLatitude(), location.getLongitude());
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(loadPoint, 15));
                    locationTag = false;
                }
                tv_location.setText("위도: " + location.getLatitude() + " 경도: " + location.getLongitude());

                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.KOREA);
                double latValue = location.getLatitude();
                double lngValue = location.getLongitude();

                try{
                    List<Address> addressList = geocoder.getFromLocation(latValue, lngValue, 4);
                    StringBuilder builder = new StringBuilder();

                    // ad.getAddressLine(0) : 실제주소(도로명 주소)
                    // ad.getPostalCode() : 우편주소
                    // ad.getLocality() : 구역주소
                    // ad.getCountryName() : 국가명

                    for(Address ad : addressList){
                        builder.append(ad.getAddressLine(0)).append("\n")
                                .append(ad.getPostalCode()).append("\n")
                                .append(ad.getLocality()).append("\n")
                                .append(ad.getCountryName()).append("\n\n");
                    }

                    tv_address.setText(builder.toString());
                }
                catch (Exception e){

                }

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
        getLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, locationListener);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }catch (SecurityException e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
