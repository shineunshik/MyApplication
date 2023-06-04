package org.techtown.myapplication;

import androidx.annotation.NonNull;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PlaceBusSelect_1_RealTime_Address extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener{

    GoogleMap mMap,mMap2;
    Button refresh;
    TextView name;
    View Custom_Marker;
    Timer timer;
    TimerTask timerTask;
    int num = 1;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.place_bus_select1_real_time_address);
        setContentView(R.layout.bus_map);

      //  Toast.makeText(this,"SDfsdfsdfsd",Toast.LENGTH_SHORT).show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap2 = googleMap;



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
        setCustomMarkerView(); //custom marker
        getSampleMarkerItems();

    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

          //  String message = "내 위치 -> Latitude : "+ latitude + "\nLongitude:"+ longitude;
          //  Log.d("Map", message);

            //   showCurrentLocation(latitude, longitude);


        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
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

                       RealTime_Address realTime_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("realtimeAddress"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));

                        try {
                            realTime_address.RealTime_Address_Call(); //실시간 버스 위치

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

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
                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_addresss, 15));
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
                                        }
                                    }

                                    PlaceBusSelect_1_RealTime_Address.GPSListener gpsListener = new PlaceBusSelect_1_RealTime_Address.GPSListener();
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

                             //   mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지


                                for (int i = 0; i < realTime_address.arrayList.size(); i++) {

                                    ArrayList<Ob> multi_marker_list = new ArrayList<>();
                                    multi_marker_list.add(new Ob(realTime_address.arrayList.get(i).getGpslati(),realTime_address.arrayList.get(i).getGpslong(),
                                            realTime_address.arrayList.get(i).getVehicleno(),realTime_address.arrayList.get(i).getNodenm(),realTime_address.arrayList.get(i).getRoutenm(),realTime_address.arrayList.get(i).getNodeid()));
                                    for (Ob ob : multi_marker_list){
                                        addCustomMarker(ob,false);
                                    }
                                }
                            }
                        });


                        refresh.setOnClickListener(new View.OnClickListener() { //새로 고침
                            @Override
                            public void onClick(View view) {

                               mMap.clear(); //버스의 위치가 실시간으로 업데이트되면서 위치를 덮어쓰지못해 clear를 해줌으로써 중복이 되지않고 최신 상태 유지

                                for (int i = 0; i < realTime_address.arrayList.size(); i++) {

                                    ArrayList<Ob> multi_marker_list = new ArrayList<>();
                                    multi_marker_list.add(new Ob(realTime_address.arrayList.get(i).getGpslati(),realTime_address.arrayList.get(i).getGpslong(),
                                            realTime_address.arrayList.get(i).getVehicleno(),realTime_address.arrayList.get(i).getNodenm(),realTime_address.arrayList.get(i).getRoutenm(),realTime_address.arrayList.get(i).getNodeid()));
                                    for (Ob ob : multi_marker_list){
                                        addCustomMarker(ob,false);
                                    }
                                }

                            //    setCustomMarkerView(); //custom marker
                            //    getSampleMarkerItems();

                                Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,"새로고침 성공",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).start();

            }
        };
        timer.schedule(timerTask,0,5000);



                new Thread(new Runnable() {
                    public void run() {
                        RealTime_Address station_address = new RealTime_Address(getIntent().getStringExtra("cityCode"),getIntent().getStringExtra("routeId"),getIntent().getStringExtra("station"),getIntent().getStringExtra("endnodenm"),getIntent().getStringExtra("startnodenm"));

                        try {
                            station_address.RealTime_Address_Call(); //정류소 위치

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
                            public void run() {

                                for (int i = 0; i < station_address.arrayList2.size(); i++) {

                                    ArrayList<Ob_Station> multi_marker_station_list = new ArrayList<>();
                                    multi_marker_station_list.add(new Ob_Station(station_address.arrayList2.get(i).getGpslati(),station_address.arrayList2.get(i).getGpslong(),
                                            station_address.arrayList2.get(i).getNodeid(),station_address.arrayList2.get(i).getNodenm()
                                            ,station_address.arrayList2.get(i).getNodeord(),station_address.arrayList2.get(i).getRouteid()));

                                    for (Ob_Station ob_station : multi_marker_station_list){
                                        addCustomMarker2(ob_station, false);
                                        addPolyLine(multi_marker_station_list);
                                    }
                                }

                            }
                        });
                    }
                }).start();


    }

    public void addPolyLine(ArrayList<Ob_Station> arrayList){




        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
                    public void run() {
                        PolylineOptions polylineOptions;
                        polylineOptions = new PolylineOptions();
                        ArrayList<LatLng> point;
                        point = new ArrayList<>();
                        for (int i = 0; i <arrayList.size();i++){


                            polylineOptions.color(Color.RED);
                            polylineOptions.width(10);
                            LatLng latLng1 = new LatLng(arrayList.get(i).getGpslati(),arrayList.get(i).getGpslong());
                            point.add(latLng1);

                            //윗처럼 위도 경도를 호출해서 하면 라인이 안그어지고
                            //임의로 위도 경도를 적어서 하면 라인이 그어짐  원인을 알아내야함
                            Toast.makeText(PlaceBusSelect_1_RealTime_Address.this,arrayList.get(i).getGpslati()+","+arrayList.get(i).getGpslong(),Toast.LENGTH_SHORT).show();

                        }

                        polylineOptions.addAll(point);
                        mMap.addPolyline(polylineOptions);


                    }
                });
            }
        }).start();
    }

    private Marker addCustomMarker(Ob ob, boolean isSelectedMarker) {

        LatLng position = new LatLng(ob.getGpslati(),ob.getGpslong());
        name.setText("차량번호:"+ob.getVehicleno()+"\n"+"노선번호:"+ob.getRoutenm()+"\n"+"정류장:"+ob.getNodenm());


        //이게 기본으로 뜨는 정보

        if (isSelectedMarker) {
            //  name.setBackgroundResource(R.drawable.ic_launcher_background);
            name.setTextColor(Color.RED);
        } else {
            //   name.setBackgroundResource(R.drawable.ic_launcher_foreground);
            name.setTextColor(Color.WHITE);
        }


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("차량번호:"+ob.getVehicleno());
        //  markerOptions.snippet("노선번호:"+ob.getRoutenm()+"\n"+"현재 정류장:"+ob.getNodenm());
        markerOptions.snippet("현재 정류장:"+ob.getNodenm());
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, Custom_Marker)));

        //markeroption에서는 저장할수있는 정보가 정해져있어서
        //내가 원하는 정보를 저장하려면 새로운 marker형식의 저장소를 만들어 내가 원하는
        //데이터를 / 슬래쉬로 구분하여 전부 집어 넣음
        //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(ob.getVehicleno()+"/"+ob.getNodenm()+"/"+ob.getRoutenm()+"/");


        //눌렀을때 떠있는 기본 정보
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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

        return mMap.addMarker(markerOptions);

    }




    private Marker addCustomMarker2(Ob_Station ob_station, boolean isSelectedMarker) {

        LatLng position = new LatLng(ob_station.getGpslati(),ob_station.getGpslong());

        name.setText("정류장:"+ob_station.getNodenm());
        //이게 기본으로 뜨는 정보

        if (isSelectedMarker) {
            //  name.setBackgroundResource(R.drawable.ic_launcher_background);
            name.setTextColor(Color.RED);
        } else {
            //   name.setBackgroundResource(R.drawable.ic_launcher_foreground);
            name.setTextColor(Color.WHITE);
        }

      //  System.out.println("\n\n\n"+position);




        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("정류장:"+ob_station.getNodenm());
      //  markerOptions.snippet("현재 정류장:"+ob_station.getNodenm());
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, Custom_Marker)));

        //markeroption에서는 저장할수있는 정보가 정해져있어서
        //내가 원하는 정보를 저장하려면 새로운 marker형식의 저장소를 만들어 내가 원하는
        //데이터를 / 슬래쉬로 구분하여 전부 집어 넣음
        //정보를 빼서 쓸때는 슬래쉬로 구분하여 뺴서 씀
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(ob_station.getNodenm()+"/"+ob_station.getNodeid()+"/"+ob_station.getStartnodenm()+"/");


        //눌렀을때 떠있는 기본 정보
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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

        return mMap.addMarker(markerOptions);

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