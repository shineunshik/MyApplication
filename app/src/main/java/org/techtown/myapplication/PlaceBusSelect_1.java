package org.techtown.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class PlaceBusSelect_1 extends Fragment {
    View view;
    SearchView search_view;
    RecyclerView recyclerview;
    RecyclerView.Adapter adapter;
    ArrayList<Ob_Bus_Select> arrayList;
    RecyclerView.LayoutManager layoutManager;

    int num = 5;

    public PlaceBusSelect_1() {
    }

    public static PlaceBusSelect_1 newInstance() {

        PlaceBusSelect_1 fragment = new PlaceBusSelect_1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_place_bus_select_1, container, false);

        search_view = view.findViewById(R.id.search_view);
        recyclerview = view.findViewById(R.id.recyclerview);

        layoutManager = new GridLayoutManager(getActivity(), 1);  //가로 2개씪 (그리드)
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerview.setHasFixedSize(true);
        arrayList = new ArrayList<>();//이거는 항상 맨 위에

        new Thread(new Runnable() {
            public void run() {


                try{

                    for (int n = 0; n <2; n++){


                    //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)
                    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteNoList"); /*URL*/
                    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
                    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
                    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(num), "UTF-8"));
                        System.out.println("결과수2222222 : "+String.valueOf(num));
                    urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8"));
                    urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8"));
                    urlBuilder.append("&" + URLEncoder.encode("routeNo","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8"));
                    URL url = new URL(urlBuilder.toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-type", "application/json");
                    System.out.println("Response code: " + conn.getResponseCode());
                    BufferedReader rd;

                    if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    }

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                    conn.disconnect();
                    System.out.println(sb.toString());
                    //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

                    //xml 데이터를 파싱하기 위한 코드
                    Document document = DocumentBuilderFactory
                            .newInstance()
                            .newDocumentBuilder()
                            .parse(url+"");

                    document.getDocumentElement().normalize();
                    NodeList nList = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명

                    //totalcount를 파싱해서 변수에 넣어 totallist를 띄워줌
                    NodeList nList2 = document.getElementsByTagName("body"); //xml에서 파싱할 리스트명
                    Node nNode2 = nList2.item(0);
                    Element eElement2 = (Element) nNode2;
                    num= Integer.parseInt(getTagValue("totalCount", eElement2));

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            //arraylist를 클리어하고 항상 최신정보를 덮어쓰기
                            arrayList.clear();
                            for(int temp = 0; temp < nList.getLength(); temp++){

                                Node nNode = nList.item(temp);
                                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                                    //log 확인 작업
                                    Element eElement = (Element) nNode;
                                    //오브젝트 생성 후 해당 오브젝트에 담은 데이터를 arraylist에 담는다
                                    Ob_Bus_Select ob_bus_select = new Ob_Bus_Select();
                                    ob_bus_select.setStartvehicletime(getTagValue("startvehicletime", eElement));
                                    ob_bus_select.setEndvehicletime(getTagValue("endvehicletime", eElement));
                                    ob_bus_select.setStartnodenm(getTagValue("startnodenm", eElement));
                                    ob_bus_select.setEndnodenm(getTagValue("endnodenm", eElement));
                                    ob_bus_select.setRouteno(getTagValue("routeno", eElement));
                                    ob_bus_select.setRoutetp(getTagValue("routetp", eElement));

                                    arrayList.add(ob_bus_select);


                                }
                            }

                            adapter = new CustomAdapter_Bus_Select(arrayList, getActivity());
                            recyclerview.setAdapter(adapter);

                        }
                    });


                    }

                }
                catch (IOException e){

                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    System.out.println("실패1");
                    e.printStackTrace();
                } catch (SAXException e) {
                    System.out.println("실패2");
                    e.printStackTrace();
                }


            }
        }).start();

//리사이클러뷰에 arraylist를 뿌려준다 adapter로 연결결


        search_view.setQueryHint("버스 번호, 정류장 이름");
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override //값을 받아왔을때 처리
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return false;
            }

            @Override //값이 변했을때 처리
            public boolean onQueryTextChange(String s) {
                search(s);
                return true;
            }
        });






        return view;
    }


    //search_view에 텍스트가 입력되었을때 실행
    @SuppressLint("NotifyDataSetChanged")
    public void search(String charText){
        ArrayList<Ob_Bus_Select> myList = new ArrayList<>();  //새로운 ArrayList  생성
        for(Ob_Bus_Select object : arrayList)
        {
            try {
                if(object.getRouteno().toLowerCase().contains(charText.toLowerCase())||object.getEndnodenm().toLowerCase().contains(charText.toLowerCase())||object.getStartnodenm().toLowerCase().contains(charText.toLowerCase()))
                { //입력된 글자가 Ob_User_BCD 겍체인 Address 와 일치하면 myList 에 담기
                    myList.add(object);
                }
            } catch (RuntimeException nullPointerException){

            }
        }
        adapter = new CustomAdapter_Bus_Select(myList,getActivity()); // 새로 생성한 ArrayList 보여주기
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // 리스트 저장, 새로고침
    }

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }



}