package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap mMap;
    Button change;
    SupportMapFragment mapFragment;
    Timer timer;
    TimerTask timerTask;
    ApiExplorer apiExplorer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        change = (Button)findViewById(R.id.change);
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

        timer = new Timer();//지정한 시간마다 리프레시
        timerTask = new TimerTask() {
            @Override
            public void run() {

                new Thread(new Runnable() {
                    public void run() {

                        apiExplorer = new ApiExplorer();

                        try {
                            apiExplorer.cc();
                            System.out.println("성공성공성공성공성공 : "+apiExplorer.locationArrayList);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                            runOnUiThread(new Runnable() {//스레드 안에서 ui를 변경할 수 있게 해주는 스레드
                                public void run() {

                                    for (int i = 0; i < apiExplorer.locationArrayList.size(); i++) {
                                        // below line is use to add marker to each location of our array list.
                                        mMap.addMarker(new MarkerOptions().position(apiExplorer.locationArrayList.get(i)).title("Marker"));
                                        // below line is use to zoom our camera on map.
                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
                                        // below line is use to move our camera to the specific location.
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(apiExplorer.locationArrayList.get(i)));
                                    }

                                }
                            });

                        }
                    }).start();

                   }
                };
            timer.schedule(timerTask,0,3000);

    }



}


