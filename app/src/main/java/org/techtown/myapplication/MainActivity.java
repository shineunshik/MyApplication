package org.techtown.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap mMap;

    ArrayList<Ob> arrayList;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Button change;

    LatLng address;

    LatLng sydney = new LatLng(-34, 151);
    LatLng TamWorth = new LatLng(-31.083332, 150.916672);
    LatLng NewCastle = new LatLng(-32.916668, 151.750000);
    LatLng Brisbane = new LatLng(-27.470125, 153.021072);
  //  ArrayList<LatLng> locationArrayList ;

    CopyOnWriteArrayList<LatLng> locationArrayList ;

    Element eElement;
    Document document;
    StringBuilder sb;
    String line;
    URL url;
    StringBuilder urlBuilder;
    NodeList nList;
    Node nNode;
    Ob ob;
    BufferedReader rd;
    HttpURLConnection conn;
    SupportMapFragment mapFragment;
    Timer timer;
    TimerTask timerTask;
    NodeList nlList;
    Node nValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationArrayList = new CopyOnWriteArrayList<>();

        change = (Button)findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setContentView(R.layout.bus_map);

                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(MainActivity.this::onMapReady);  // <-이코드 됨


                Handler handler = new Handler();


                timer = new Timer();//지정한 시간마다 리프레시
                timerTask = new TimerTask() {
                    @Override
                    public void run() {


                        new Thread(new Runnable() {
                            public void run() {

                                try{
                                    //현재 api가 안먹혀서 임의로 데이터를 넣어서 테스트를 함 테스트 데이터를 여가에 놓으면 되지만 밑에 for,if 문의 넣으면 되지 않음


                                    //메인스레드안에 arraylist에 데이터를 담아서 밖으로 나가면 데이터가 날라감
                                    //  System.out.println("출력 테스트11111111111"+locationArrayList);

                                    //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
                                    urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
                                    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
                                    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                                    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
                                    urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                                    urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
                                    urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("DJB30300052", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
                                    url = new URL(urlBuilder.toString());
                                    conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("GET");
                                    conn.setRequestProperty("Content-type", "application/json");
                                   // System.out.println("Response code: " + conn.getResponseCode());


                                    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                                        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    } else {
                                        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                                    }

                                    sb = new StringBuilder();
                                    while ((line = rd.readLine()) != null) {
                                        sb.append(line);
                                    }
                                    rd.close();
                                    conn.disconnect();
                                    //System.out.println(sb.toString());
                                    //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

                                    //xml 데이터를 파싱하기 위한 코드
                                    document = DocumentBuilderFactory
                                            .newInstance()
                                            .newDocumentBuilder()
                                            .parse(url + "");

                                    document.getDocumentElement().normalize();
                                    nList = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명

                                    if(nList.getLength() ==0){//list에 데이터가 없으면
                                        System.out.println("111111111111111\n2222222222222222\n3333333333333333\n44444444444444");
                                    }

                                    //for문 밑으로는  api가 작동하지않으면 안돌아감 내일 다시 확인
                                    //안됐던 이유는 변수들이 지역번수라서 안됐던거 같음
                                    //현재 전역변수로 다 돌려놔서 확인만 하면 됨
                                    for (int temp = 0; temp < nList.getLength(); temp++) { //api가 작동을 하지않아 item 갯수가 없어서 for문이 작동을 안함
                                        nNode = nList.item(temp);
                                        if (nNode.getNodeType() == Node.ELEMENT_NODE) { //노드 type이 같을 경우에 실행
                                            //log 확인 작업
                                            eElement = (Element) nNode;

                                            //오브젝트 생성 후 해당 오브젝트에 담은 데이터를 arraylist에 담는다 (recyclerview 전용)
//                                            ob = new Ob();
//                                            ob.setPlateNo(getTagValue("plateNo", eElement));
//                                            ob.setGpslati(getTagValue("gpslati",eElement));
//                                            ob.setGpslong(getTagValue("gpslong",eElement));

                                            address = new LatLng(Double.parseDouble(getTagValue("gpslati", eElement)),Double.parseDouble(getTagValue("gpslong", eElement)));
                                            locationArrayList.add(address);

//                                            locationArrayList.add(sydney);
//                                            locationArrayList.add(TamWorth);
//                                            locationArrayList.add(NewCastle);
//                                            locationArrayList.add(Brisbane);


                                         //   synlist = Collections.synchronizedList(locationArrayList);


                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {

                                                   // Toast.makeText(MainActivity.this,"gdfgdfgdfgdfgdf",Toast.LENGTH_LONG).show();

                                                    //   adapter = new CustomAdapter(arrayList,MainActivity.this);
                                                    //   recyclerview.setAdapter(adapter);
                                                }
                                            });

                                        }



                                    }



                                }
                                catch (IOException e){
                                    System.out.println("실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패");
                                    e.printStackTrace();
                                } catch (ParserConfigurationException e) {
                                    System.out.println("실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패");
                                    e.printStackTrace();
                                } catch (SAXException e) {
                                    System.out.println("실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패실패");
                                    e.printStackTrace();
                                }

                            }
                        }).start();


//                        locationArrayList.add(TamWorth);
//                        locationArrayList.add(NewCastle);
//                        locationArrayList.add(Brisbane);

                    }
                };
                timer.schedule(timerTask,0,3000);


            }
        });


        System.out.println("출력 테스트4444444444444444444444"+locationArrayList);

    }

    //xal 데이터 파일의 tag값을 가져오는 메소드
    public String getTagValue(String tag, Element eElement) {
        nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("출력 테스트222222222"+locationArrayList);



        for (int i = 0; i < locationArrayList.size(); i++) {

            // below line is use to add marker to each location of our array list.
            mMap.addMarker(new MarkerOptions().position(locationArrayList.get(i)).title("Marker"));

            // below line is use to zoom our camera on map.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

            // below line is use to move our camera to the specific location.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)));
        }

    }

}
