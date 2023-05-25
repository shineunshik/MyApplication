package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener , GoogleMap.OnMapClickListener , GoogleMap.OnMarkerClickListener{

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

    //custom marker
    TextView name;
    View Custom_Marker;
    Marker selectedMarker;
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true); //나침반 기능 사용 유무

        setCustomMarkerView();
        getSampleMarkerItems();

//        timer = new Timer();//지정한 시간마다 리프레시
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//                new Thread(new Runnable() {
//                    public void run() {
//
//                        apiExplorer = new ApiExplorer();
//                        try {
//                            apiExplorer.cc();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        System.out.println("성공성22222222 : " + apiExplorer.arrayList);
//                        runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
//                            public void run() {
//
//                                try {
//                                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //gps신호 받아오기
//                                    Location location1 =manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);// 네트워크 신호받아오기
//                                    if (location != null) { //gps 수신(gps 신호는 최근에 받아왔던 신호를 받아오기때문에 재부팅을하면 gps신호가 사라져서 null값을 가져옴 그래서 네트워크 신호도 가져오게 함)
//                                        if (num == 1) {
//                                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
//                                            double lat, lng;
//                                            lat = location.getLatitude();
//                                            lng = location.getLongitude();
//                                            LatLng my_addresss = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
//                                        } else {
//                                            double lat, lng;
//                                            lat = location.getLatitude();
//                                            lng = location.getLongitude();
//                                            LatLng my_addresss = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
//                                        }
//
//                                    }
//                                    else if (location1 != null) { //네트워크로 수신
//                                        if (num == 1) {
//                                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
//                                            double lat, lng;
//                                            lat = location1.getLatitude();
//                                            lng = location1.getLongitude();
//                                            LatLng my_addresss = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
//                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
//                                        } else {
//                                            double lat, lng;
//                                            lat = location1.getLatitude();
//                                            lng = location1.getLongitude();
//                                            LatLng my_addresss = new LatLng(lat, lng);
//                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
//                                        }
//                                    }
//
//                                    GPSListener gpsListener = new GPSListener();
//                                    long minTime = 1000;  //10초마다  위치 갱신
//                                    float minDistance = 0; //최소 거리 0
//
//                                    manager.requestLocationUpdates(
//                                            LocationManager.GPS_PROVIDER,
//                                            minTime,
//                                            minDistance, gpsListener);
//
//                                } catch (SecurityException e) {
//                                    e.printStackTrace();
//                                    System.out.println("완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n");
//
//                                }
//
//                                mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지
//
//                                for (int i = 0; i < apiExplorer.arrayList.size(); i++) {
//
//                                        double lat,lng;
//                                        lat = apiExplorer.arrayList.get(i).getGpslati();
//                                        lng = apiExplorer.arrayList.get(i).getGpslong();
//                                        LatLng bus_addresss = new LatLng(lat,lng);
//                                        Marker nMarker = mMap.addMarker(new MarkerOptions()
//                                                .position(bus_addresss)
//                                                .title("차량번호:"+apiExplorer.arrayList.get(i).getVehicleno())
//                                                .snippet("노선번호:"+apiExplorer.arrayList.get(i).getRoutenm()+"\n"+
//                                                        "현재 정류장:"+apiExplorer.arrayList.get(i).getNodenm()));
//                                        nMarker.setPosition(bus_addresss);
//                                       // nMarker.showInfoWindow(); //정보창을 띄어주는 녀석인데 마지막꺼 하나만 띄워줌
//                                    }
//                                }
//                            });
//
//
//                        refresh.setOnClickListener(new View.OnClickListener() { //새로 고침
//                            @Override
//                            public void onClick(View view) {
//
//                                mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지
//
//                                for (int i = 0; i < apiExplorer.arrayList.size(); i++) {
//
//                                    double lat,lng;
//                                    lat = apiExplorer.arrayList.get(i).getGpslati();
//                                    lng = apiExplorer.arrayList.get(i).getGpslong();
//                                    LatLng bus_addresss = new LatLng(lat,lng);
//                                    Marker nMarker = mMap.addMarker(new MarkerOptions()
//                                            .position(bus_addresss)
//                                            .title("차량번호:"+apiExplorer.arrayList.get(i).getVehicleno())
//                                            .snippet("노선번호:"+apiExplorer.arrayList.get(i).getRoutenm()+"\n"+
//                                                    "현재 정류장:"+apiExplorer.arrayList.get(i).getNodenm()));
//                                    nMarker.setPosition(bus_addresss);

//                                }
//
//
//                                Toast.makeText(MainActivity.this,"새로고침 성공",Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                        }
//                    }).start();
//
//                   }
//                };
//            timer.schedule(timerTask,0,5000);



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

         //   showCurrentLocation(latitude, longitude);


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


    private void setCustomMarkerView(){

        Custom_Marker = LayoutInflater.from(this).inflate(R.layout.custom_marker,null);
        name = (TextView) Custom_Marker.findViewById(R.id.name);
    }

    private void getSampleMarkerItems(){
        refresh = (Button)findViewById(R.id.refresh);
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

                                try {
                                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //gps신호 받아오기
                                    Location location1 =manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);// 네트워크 신호받아오기
                                    if (location != null) { //gps 수신(gps 신호는 최근에 받아왔던 신호를 받아오기때문에 재부팅을하면 gps신호가 사라져서 null값을 가져옴 그래서 네트워크 신호도 가져오게 함)
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
                                    else if (location1 != null) { //네트워크로 수신
                                        if (num == 1) {
                                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
                                            double lat, lng;
                                            lat = location1.getLatitude();
                                            lng = location1.getLongitude();
                                            LatLng my_addresss = new LatLng(lat, lng);
                                            mMap.addMarker(new MarkerOptions().position(my_addresss).title("은시깅"));
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
                                        } else {
                                            double lat, lng;
                                            lat = location1.getLatitude();
                                            lng = location1.getLongitude();
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
                                    ArrayList<Ob> multi_marker_list = new ArrayList<>();

                                    multi_marker_list.add(new Ob(apiExplorer.arrayList.get(i).getGpslati(),apiExplorer.arrayList.get(i).getGpslong(),
                                            apiExplorer.arrayList.get(i).getVehicleno(),apiExplorer.arrayList.get(i).getNodenm(),apiExplorer.arrayList.get(i).getRoutenm()));
                                    for (Ob ob : multi_marker_list){
                                        addMarker(ob,false);
                                    }

//                                    double lat,lng;
//                                    lat = apiExplorer.arrayList.get(i).getGpslati();
//                                    lng = apiExplorer.arrayList.get(i).getGpslong();
//                                    LatLng bus_addresss = new LatLng(lat,lng);
//                                    Marker nMarker = mMap.addMarker(new MarkerOptions()
//                                            .position(bus_addresss)
//                                            .title("차량번호:"+apiExplorer.arrayList.get(i).getVehicleno())
//                                            .snippet("노선번호:"+apiExplorer.arrayList.get(i).getRoutenm()+"\n"+
//                                                    "현재 정류장:"+apiExplorer.arrayList.get(i).getNodenm()));
//                                    nMarker.setPosition(bus_addresss);
                                    // nMarker.showInfoWindow(); //정보창을 띄어주는 녀석인데 마지막꺼 하나만 띄워줌
                                }
                            }
                        });


                        refresh.setOnClickListener(new View.OnClickListener() { //새로 고침
                            @Override
                            public void onClick(View view) {

                                mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지

                                for (int i = 0; i < apiExplorer.arrayList.size(); i++) {
                                    ArrayList<Ob> multi_marker_list = new ArrayList<>();
                                    multi_marker_list.add(new Ob(apiExplorer.arrayList.get(i).getGpslati(),apiExplorer.arrayList.get(i).getGpslong(),
                                            apiExplorer.arrayList.get(i).getVehicleno(),apiExplorer.arrayList.get(i).getNodenm(),apiExplorer.arrayList.get(i).getRoutenm()));
                                    for (Ob ob : multi_marker_list){
                                        addMarker(ob,false);
                                    }

//                                    double lat,lng;
//                                    lat = apiExplorer.arrayList.get(i).getGpslati();
//                                    lng = apiExplorer.arrayList.get(i).getGpslong();
//                                    LatLng bus_addresss = new LatLng(lat,lng);
//                                    Marker nMarker = mMap.addMarker(new MarkerOptions()
//                                            .position(bus_addresss)
//                                            .title("차량번호:"+apiExplorer.arrayList.get(i).getVehicleno())
//                                            .snippet("노선번호:"+apiExplorer.arrayList.get(i).getRoutenm()+"\n"+
//                                                    "현재 정류장:"+apiExplorer.arrayList.get(i).getNodenm()));
//                                    nMarker.setPosition(bus_addresss);
                                }


                                Toast.makeText(MainActivity.this,"새로고침 성공",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).start();

            }
        };
        timer.schedule(timerTask,0,5000);

    }


    private Marker addMarker(Ob ob, boolean isSelectedMarker) {

        LatLng position = new LatLng(ob.getGpslati(),ob.getGpslong());

        name.setText("차량번호:"+ob.getVehicleno()+"\n"+"노선번호:"+ob.getRoutenm()+"\n"+
                "현재 정류장:"+ob.getNodenm());
        //이게 기본으로 뜨는 정보


        if (isSelectedMarker) {
          //  name.setBackgroundResource(R.drawable.ic_launcher_background);
            name.setTextColor(Color.RED);
        } else {
         //   name.setBackgroundResource(R.drawable.ic_launcher_foreground);
            name.setTextColor(Color.WHITE);
        }

        //이건 눌럿을때 뜨는 정보
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("차량번호:"+ob.getVehicleno());
        markerOptions.snippet("노선번호sfsfd:"+ob.getRoutenm()+"\n"+
                                                    "현재 정류장:"+ob.getNodenm());
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, Custom_Marker)));


        return mMap.addMarker(markerOptions);

    }

//    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
//        double lat = marker.getPosition().latitude;
//        double lon = marker.getPosition().longitude;
//        String bus_number = marker.getTitle();
//        String info = marker.getSnippet();
//
//
//
//        LatLng position = new LatLng(lat,lon);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.title(bus_number);
//        markerOptions.snippet(info);
//        markerOptions.position(position);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, Custom_Marker)));
//
//
//        return mMap.addMarker(markerOptions);
//    }


    private Bitmap createDrawableFromView(Context context, View view) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private Marker addMarker(Marker marker, boolean isSelectedMarker) {
        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;
        String bus_number = marker.getTitle();
        String info = marker.getSnippet();
        Ob temp = new Ob(lat, lon, bus_number,info,info);
        return addMarker(temp, isSelectedMarker);

    }




    @Override
    public boolean onMarkerClick(Marker marker) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(marker.getPosition());
        mMap.animateCamera(center);

        changeSelectedMarker(marker);

        return true;
    }



    private void changeSelectedMarker(Marker marker) {
        // 선택했던 마커 되돌리기
        if (selectedMarker != null) {
            addMarker(selectedMarker, false);
            selectedMarker.remove();
        }

        // 선택한 마커 표시
        if (marker != null) {
            selectedMarker = addMarker(marker, true);
            marker.remove();
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        changeSelectedMarker(null);
    }



}



