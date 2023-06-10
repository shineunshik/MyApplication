package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PlaceBusSelect_1_RealTime_Address extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener{
    GoogleMap mMap,mMap2;
    Button refresh;
    TextView name,station_name;
    LinearLayout real_view,station_view;
 //   View custom_Marker;
    View custom_marker_station;
    View custom_Marker;
    Timer timer;
    TimerTask timerTask;
    int num = 1;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        custom_Marker =inflater.inflate(R.layout.custom_marker, null);
      //  custom_Marker = LayoutInflater.from(this).inflate(R.layout.custom_marker,null);
        name = (TextView) custom_Marker.findViewById(R.id.name);
        real_view = (LinearLayout)custom_Marker.findViewById(R.id.real_view);



        custom_marker_station =inflater.inflate(R.layout.custom_marker_station, null);
       // custom_marker_station = LayoutInflater.from(this).inflate(R.layout.custom_marker_station,null);
        station_name = (TextView) custom_marker_station.findViewById(R.id.station_name);
        station_view = (LinearLayout)custom_marker_station.findViewById(R.id.station_view);





        refresh = (Button)findViewById(R.id.refresh);


      //  setCustomMarkerView2(); //custom marker
     //   setCustomMarkerView(); //custom marker
             new Thread(new Runnable() { //메인스레드
                @Override
                public void run() {

                    ArrayList<LatLng> point = new ArrayList<>();
                    ArrayList<Ob_Station> multi_marker_station_list = new ArrayList<>();
                    RealTime_Address station_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("station"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));

                    try {
                        station_address.RealTime_Address_Call(); //정류소 위치
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(new Runnable() {//ui 스레드
                        @Override
                        public void run() {  //속도가 느림
                            for (int i = 0; i < station_address.arrayList2.size(); i++) {
                                multi_marker_station_list.add(new Ob_Station(station_address.arrayList2.get(i).getGpslati(),station_address.arrayList2.get(i).getGpslong(),
                                        station_address.arrayList2.get(i).getNodeid(),station_address.arrayList2.get(i).getNodenm()
                                        ,station_address.arrayList2.get(i).getNodeord(),station_address.arrayList2.get(i).getRouteid()));
                                point.add(i,new LatLng(station_address.arrayList2.get(i).getGpslati(),station_address.arrayList2.get(i).getGpslong()));
                            }

                            for (Ob_Station ob_station : multi_marker_station_list){
                                addCustomMarker_station(ob_station,point);
                            }

                        Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,"무한루프 테스트",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).start();



    timer = new Timer();//지정한 시간마다 리프레시
    timerTask = new TimerTask() {
        @Override
        public void run() {

         new Thread(new Runnable() {
            @Override
            public void run() {

                RealTime_Address realTime_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("realtimeAddress"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));
                ArrayList<Ob> multi_marker_list = new ArrayList<>();
                try {
                    realTime_address.RealTime_Address_Call(); //실시간 버스 위치

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            for (int i = 0; i < realTime_address.arrayList.size(); i++) {
                                multi_marker_list.add(new Ob(realTime_address.arrayList.get(i).getGpslati(),realTime_address.arrayList.get(i).getGpslong(),
                                        realTime_address.arrayList.get(i).getVehicleno(),realTime_address.arrayList.get(i).getNodenm(),realTime_address.arrayList.get(i).getRoutenm(),realTime_address.arrayList.get(i).getNodeid()));
                            }
                            for (Ob ob : multi_marker_list){
                                addCustomMarker_real(ob);
                            }


                        }catch (NullPointerException e){
                            System.out.println("NullPointerException : 실패");
                            e.printStackTrace();
                        }

                        Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,"3초 마다 갱신",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();

            }
        };
        timer.schedule(timerTask,0,3000);

      //  thread_station.start();







    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap2 = googleMap;
        try {
                    LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //gps신호 받아오기
                    Location location1 =manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);// 네트워크 신호받아오기
                    PlaceBusSelect_1_RealTime_Address.GPSListener gpsListener = new PlaceBusSelect_1_RealTime_Address.GPSListener();
                    long minTime = 1000;  //10초마다  위치 갱신
                    float minDistance = 0; //최소 거리 0

                    manager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            minTime,
                            minDistance, gpsListener);

                    if (location != null) { //gps 수신(gps 신호는 최근에 받아왔던 신호를 받아오기때문에 재부팅을하면 gps신호가 사라져서 null값을 가져옴 그래서 네트워크 신호도 가져오게 함)
                        if (num == 1) {
                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
                            double lat, lng;
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                            LatLng my_addresss = new LatLng(lat, lng);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
                            Toast.makeText(this,"gps 수신",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (location1 != null) { //네트워크로 수신
                        if (num == 1) {
                            ++num; //최초 한번만 나의 위치로 포커스 되도록 함
                            double lat, lng;
                            lat = location1.getLatitude();
                            lng = location1.getLongitude();
                            LatLng my_addresss = new LatLng(lat, lng);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
                            Toast.makeText(this,"네트워크 수신",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(this,"수신 안됨",Toast.LENGTH_SHORT).show();
                    }
        } catch (SecurityException e) {
           // e.printStackTrace();
            System.out.println("완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n완전 실패\n");

        }

        mMap.setOnMyLocationButtonClickListener(this); //나침반을 누르면 나의 위치로 이동 기능
        mMap.setOnMyLocationClickListener(this); //나침반을 누르면 나의 위치로 이동 기능
        mMap.getUiSettings().setZoomControlsEnabled(true); //확대/축소 버튼
        mMap.getUiSettings().setMapToolbarEnabled(false); //마켜 클릭했을때 네비게이션 기능 표시 여부
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true); //나침반 기능 사용 유무
        mMap.setOnMarkerClickListener(this);




    }


    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

        }

        public void onProviderDisabled(String provider) { }
        public void onProviderEnabled(String provider) { }
        public void onStatusChanged(String provider, int status, Bundle extras) { }
    }



//    private void getSampleMarkerItems(){
//
//        timer = new Timer();//지정한 시간마다 리프레시
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        RealTime_Address realTime_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("realtimeAddress"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));
//                        ArrayList<Ob> multi_marker_list = new ArrayList<>();
//                        try {
//                            realTime_address.RealTime_Address_Call(); //실시간 버스 위치
//
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                try {
//                                    for (int i = 0; i < realTime_address.arrayList.size(); i++) {
//                                        multi_marker_list.add(new Ob(realTime_address.arrayList.get(i).getGpslati(),realTime_address.arrayList.get(i).getGpslong(),
//                                                realTime_address.arrayList.get(i).getVehicleno(),realTime_address.arrayList.get(i).getNodenm(),realTime_address.arrayList.get(i).getRoutenm(),realTime_address.arrayList.get(i).getNodeid()));
//                                    }
//                                    for (Ob ob : multi_marker_list){
//                                        addCustomMarker_real(ob);
//                                    }
//                                }catch (NullPointerException e){
//                                    System.out.println("NullPointerException : 실패");
//                                    e.printStackTrace();
//                                }
//
//                                Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,"3초 마다 갱신",Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//                }).start();
//
//            }
//        };
//        timer.schedule(timerTask,0,3000);
//
//
//    }

//    private void getSampleMarkerItems2(){
//
//        new Thread(new Runnable() {
//            public void run() {
//
//                RealTime_Address station_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("station"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));
//
//                try {
//                    station_address.RealTime_Address_Call(); //정류소 위치
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//
//                runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
//                    public void run() {
//
//
//
//                        mMap.clear();
//                        ArrayList<LatLng> point = new ArrayList<>();
//                        ArrayList<Ob_Station> multi_marker_station_list = new ArrayList<>();
//                        for (int i = 0; i < station_address.arrayList2.size(); i++) {
//
//                            multi_marker_station_list.add(new Ob_Station(station_address.arrayList2.get(i).getGpslati(),station_address.arrayList2.get(i).getGpslong(),
//                                    station_address.arrayList2.get(i).getNodeid(),station_address.arrayList2.get(i).getNodenm()
//                                    ,station_address.arrayList2.get(i).getNodeord(),station_address.arrayList2.get(i).getRouteid()));
//
//                            point.add(i,new LatLng(station_address.arrayList2.get(i).getGpslati(),station_address.arrayList2.get(i).getGpslong()));
//
//                            for (Ob_Station ob_station : multi_marker_station_list){
//
//                                addCustomMarker_station(ob_station,point);
//                            }
//
//
//                        }
//
//
//
//                    }
//                });
//            }
//        }).start();
//
//    }


    private Marker addCustomMarker_real(Ob ob) {

        LatLng position = new LatLng(ob.getGpslati(),ob.getGpslong());
        MarkerOptions markerOptions = new MarkerOptions();

        name.setText("테스트 차량번호:"+ob.getVehicleno()+"\n"+"노선번호:"+ob.getRoutenm()+"\n"+"정류장:"+ob.getNodenm());

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, custom_Marker)));
        markerOptions.position(position);
        markerOptions.title("노선:"+ob.getRoutenm());
        markerOptions.snippet("차량번호:"+ob.getVehicleno());


        Marker marker_realtime = mMap.addMarker(markerOptions);
        marker_realtime.setTag(ob.getVehicleno()+"/"+ob.getNodenm()+"/"+ob.getRoutenm()+"/");
        // marker_realtime.remove();


        marker_realtime.setPosition(position);


        //markeroption에서는 저장할수있는 정보가 정해져있어서
        //내가 원하는 정보를 저장하려면 새로운 marker형식의 저장소를 만들어 내가 원하는
        //데이터를 / 슬래쉬로 구분하여 전부 집어 넣음
        //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀





     //   marker_realtime.setPosition(position);

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            //여기서 클릭 리스너 구현을 하고 클릭했을때 밑에 상세정보 카드뷰가 나오게 하기
//            @Override
//            public boolean onMarkerClick(@NonNull Marker marker) {
//                //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀
//                String[] arr = marker.getTag().toString().split("/");
//                Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,arr[0]+"\n"+arr[1]+"\n"+arr[2],Toast.LENGTH_SHORT).show();
//                //marker 클릭했을때 원하는 정보 띄우는거까지 성공
//                return false;
//            }
//        });



        return mMap.addMarker(markerOptions);

    }



    private Marker addCustomMarker_station(Ob_Station ob_station,ArrayList<LatLng> point) {
        LatLng position = new LatLng(ob_station.getGpslati(),ob_station.getGpslong());

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(point);
        mMap2.addPolyline(polylineOptions);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("정류장:"+ob_station.getNodenm());
        markerOptions.position(position);
        //  markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, custom_marker_station)));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

        //markeroption에서는 저장할수있는 정보가 정해져있어서
        //내가 원하는 정보를 저장하려면 새로운 marker형식의 저장소를 만들어 내가 원하는
        //데이터를 / 슬래쉬로 구분하여 전부 집어 넣음
        //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀
        Marker marker_station = mMap2.addMarker(markerOptions);
        marker_station.setTag(ob_station.getNodenm()+"/"+ob_station.getNodeid()+"/"+ob_station.getStartnodenm()+"/");


        mMap2.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            //여기서 클릭 리스너 구현을 하고 클릭했을때 밑에 상세정보 카드뷰가 나오게 하기
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀
                String[] arr = marker.getTag().toString().split("/");
                Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,arr[0]+"\n"+arr[1]+"\n"+arr[2],Toast.LENGTH_SHORT).show();
                //marker 클릭했을때 원하는 정보 띄우는거까지 성공
                return false;
            }
        });



        return mMap2.addMarker(markerOptions);

    }



    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }


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


}