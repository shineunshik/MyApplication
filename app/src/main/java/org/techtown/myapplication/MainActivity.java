package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    GoogleMap mMap;
    Button change;
    SupportMapFragment mapFragment;
    Timer timer;
    TimerTask timerTask;
    ApiExplorer apiExplorer;

    private static final int GPS_ENABLE_REQUEST_CODE = 1000;
    MarkerOptions myLocationMarker;

    int num = 1;

    Button refresh;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        change = (Button) findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.bus_map);

                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MainActivity.this::onMapReady);  // <-이코드 됨

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        refresh = (Button)findViewById(R.id.refresh);
        mMap.setOnMyLocationButtonClickListener(this); //나침반을 누르면 나의 위치로 이동 기능
        mMap.setOnMyLocationClickListener(this); //나침반을 누르면 나의 위치로 이동 기능

        mMap.getUiSettings().setZoomControlsEnabled(true); //확대/축소 버튼
        mMap.getUiSettings().setMapToolbarEnabled(false); //마켜 클릭했을때 네비게이션 기능 표시 여부
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true); //나침반 기능 사용 유무

        timer = new Timer();//지정한 시간마다 리프레시
        timerTask = new TimerTask() {
            @Override
            public void run() {

                new Thread(new Runnable() {
                    public void run() {

                        apiExplorer = new ApiExplorer();
                        try {
                            apiExplorer.cc();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("성공성22222222 : " + apiExplorer.arrayList);
                        runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
                            public void run() {

                                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                                try {
                                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (location != null) {
                                        if (num == 1) {
                                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
                                            double lat, lng;
                                            lat = location.getLatitude();
                                            lng = location.getLongitude();
                                            LatLng my_addresss = new LatLng(lat, lng);
                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
                                        } else {
                                            double lat, lng;
                                            lat = location.getLatitude();
                                            lng = location.getLongitude();
                                            LatLng my_addresss = new LatLng(lat, lng);
                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
                                        }

                                    }
                                    GPSListener gpsListener = new GPSListener();
                                    long minTime = 1000;  //10초마다  위치 갱신
                                    float minDistance = 0; //최소 거리 0

                                    manager.requestLocationUpdates(
                                            LocationManager.GPS_PROVIDER,
                                            minTime,
                                            minDistance, gpsListener);

                                } catch (SecurityException e) {
                                    e.printStackTrace();
                                    System.out.println("완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n");
                                }

                                mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지

                                for (int i = 0; i < apiExplorer.arrayList.size(); i++) {

                                        double lat,lng;
                                        lat = apiExplorer.arrayList.get(i).getGpslati();
                                        lng = apiExplorer.arrayList.get(i).getGpslong();
                                        LatLng bus_addresss = new LatLng(lat,lng);
                                        Marker nMarker = mMap.addMarker(new MarkerOptions()
                                                .position(bus_addresss)
                                                .title("차량번호:"+apiExplorer.arrayList.get(i).getVehicleno())
                                                .snippet("노선번호:"+apiExplorer.arrayList.get(i).getRoutenm()+"\n"+
                                                        "현재 정류장:"+apiExplorer.arrayList.get(i).getNodenm()));
                                        nMarker.setPosition(bus_addresss);
                                       // nMarker.showInfoWindow(); //정보창을 띄어주는 녀석인데 마지막꺼 하나만 띄워줌
                                    }
                                }
                            });


                        refresh.setOnClickListener(new View.OnClickListener() { //새로 고침
                            @Override
                            public void onClick(View view) {

                                mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지

                                for (int i = 0; i < apiExplorer.arrayList.size(); i++) {

                                    double lat,lng;
                                    lat = apiExplorer.arrayList.get(i).getGpslati();
                                    lng = apiExplorer.arrayList.get(i).getGpslong();
                                    LatLng bus_addresss = new LatLng(lat,lng);
                                    Marker nMarker = mMap.addMarker(new MarkerOptions().position(bus_addresss).title(apiExplorer.arrayList.get(i).getVehicleno()));
                                    nMarker.setPosition(bus_addresss);

                                    System.out.println("새로고침 성공 : "+  bus_addresss );
                                }


                                Toast.makeText(MainActivity.this,"성공",Toast.LENGTH_LONG).show();
                            }
                        });

                        }
                    }).start();

                   }
                };
            timer.schedule(timerTask,0,5000);



    }

    private boolean checkLocationServiceStatus() {
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;
                System.out.println("최근위치최근위치최근위치최근위치"+message);
                Log.d("Map", message);

            }

            GPSListener gpsListener = new GPSListener();
            long minTime =1000;  //10초마다  위치 갱신
            float minDistance = 10; //최소 거리 0

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "현재 위치로 이동합니다.",
                    Toast.LENGTH_LONG).show();

        } catch(SecurityException e) {
            e.printStackTrace();
        }
    }


    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.d("Map", message);

          //  showCurrentLocation(latitude, longitude);


        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }

    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

     //   showMyLocationMarker(curPoint);
    }

    private void showMyLocationMarker(LatLng curPoint) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();
            myLocationMarker.position(curPoint);
            myLocationMarker.title("내 위치\n");
             myLocationMarker.snippet("GPS로 확인한 위치");
            // myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            mMap.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(curPoint);
        }
    }


    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}



