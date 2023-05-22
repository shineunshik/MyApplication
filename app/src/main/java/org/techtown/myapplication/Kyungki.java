package org.techtown.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.myapplication.API_1.ApiExplorer;
import org.techtown.myapplication.API_1.CustomAdapter_BusNumber;
import org.techtown.myapplication.API_1.Kyungki_Bus_API_1;
import org.techtown.myapplication.API_1.Ob_BusNumber;
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
import java.security.PublicKey;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Kyungki extends AppCompatActivity{

//    ArrayList<Ob> arrayList;
//    RecyclerView recyclerview;
//    RecyclerView.Adapter adapter;
//    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyungki_bus_api_1);


//        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);
//        layoutManager = new GridLayoutManager(this, 1);  //가로 2개씪 (그리드)
//        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
//        recyclerview.setHasFixedSize(true);
//        arrayList = (ArrayList<Ob>)getIntent().getSerializableExtra("list");
//        System.out.println("출력출력출력출력"+arrayList);
//
//
//        adapter = new CustomAdapter(arrayList, Kyungki.this);
//        recyclerview.setAdapter(adapter);

//        new Thread(new Runnable() {
//        public void run() {
//
//            try{
//                //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
//                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/buslocationservice/getBusLocationList"); /*URL*/
//                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
//                urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("200000078", "UTF-8")); /*노선 ID*/
//                URL url = new URL(urlBuilder.toString());
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Content-type", "application/json");
//                System.out.println("Response code: " + conn.getResponseCode());
//                BufferedReader rd;
//
//                if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                } else {
//                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//                }
//
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    sb.append(line);
//                }
//                rd.close();
//                conn.disconnect();
//                System.out.println(sb.toString());
//                //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
//
//                Document document = DocumentBuilderFactory
//                        .newInstance()
//                        .newDocumentBuilder()
//                        .parse(url+"");
//                document.getDocumentElement().normalize();
//
//                NodeList nList = document.getElementsByTagName("busLocationList"); //xml에서 파싱할 리스트명
//
//                for(int temp = 0; temp < nList.getLength(); temp++){
//                    Node nNode = nList.item(temp);
//                    if(nNode.getNodeType() == Node.ELEMENT_NODE){
//                        //log 확인 작업
//                        Element eElement = (Element) nNode;
//                        System.out.println("######################");
//                        System.out.println("차량 번호판  : " + getTagValue("plateNo", eElement));
//                        System.out.println("정류소 번호  : " + getTagValue("routeId", eElement));
//                        System.out.println("######################");
//
//                        //오브젝트 생성 후 해당 오브젝트에 담은 데이터를 arraylist에 담는다
//                        Ob ob = new Ob();
//                        ob.setPlateNo(getTagValue("plateNo", eElement));
//                        arrayList.add(ob);
//                    }
//                }
//
//                //리사이클러뷰에 arraylist를 뿌려준다 adapter로 연결결
//                adapter = new CustomAdapter(arrayList, Kyungki.this);
//                recyclerview.setAdapter(adapter);
//
//
//
//            }
//            catch (IOException e){
//                e.printStackTrace();
//            }
//            catch (SAXException e) {
//                e.printStackTrace();
//            } catch (ParserConfigurationException e) {
//                e.printStackTrace();
//            }
//            catch (RuntimeException e){
//
//            }
//
//        }
//        }).start();


    }

    //xal 데이터 파일의 tag값을 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

}
