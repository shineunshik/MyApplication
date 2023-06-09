package org.techtown.myapplication.API_1;

import android.content.SyncStatusObserver;

import org.techtown.myapplication.Ob;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public  class ApiExplorer {

    NodeList nlList;
    Node nValue;
    ArrayList<Ob> arrayList;
    Ob ob,ob1;

    public  void cc() throws IOException {

        try{
            arrayList = new ArrayList<>();
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
            urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "=" + URLEncoder.encode("31040", "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
            urlBuilder.append("&" + URLEncoder.encode("routeId", "UTF-8") + "=" + URLEncoder.encode("GGB208000008", "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            // System.out.println("Response code: " + conn.getResponseCode());

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            //System.out.println(sb.toString());
            //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

            //xml 데이터를 파싱하기 위한 코드
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(url + "");

            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("item"); //xml에서 파싱할 리스트명

            if (nList.getLength() == 0) {//list에 데이터가 없으면
                //테스트용
                ob = new Ob();
                ob.setVehicleno("부산 바 1234");
                ob.setGpslati(37.432124);
                ob.setGpslong(127.129064);
                ob.setNodenm("모란역");
                ob.setRoutenm("333");
                arrayList.add(ob);
                ob1 = new Ob();
                ob1.setVehicleno("부산 바 5678");
                ob1.setGpslati(37.439854);
                ob1.setGpslong(127.128035);
                ob1.setNodenm("태평역");
                ob1.setRoutenm("51");
                arrayList.add(ob1);
                System.out.println("111111111111111\n2222222222222222\n3333333333333333\n44444444444444");
            }

            //for문 밑으로는  api가 작동하지않으면 안돌아감 내일 다시 확인
            //안됐던 이유는 변수들이 지역번수라서 안됐던거 같음
            //현재 전역변수로 다 돌려놔서 확인만 하면 됨
            for (int temp = 0; temp < nList.getLength(); temp++) { //api가 작동을 하지않아 item 갯수가 없어서 for문이 작동을 안함
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) { //노드 type이 같을 경우에 실행
                    //log 확인 작업
                    Element eElement = (Element) nNode;

                    ob = new Ob();
                    ob.setVehicleno(getTagValue("vehicleno", eElement));
                    ob.setGpslati(Double.parseDouble(getTagValue("gpslati",eElement)));
                    ob.setGpslong(Double.parseDouble(getTagValue("gpslong",eElement)));
                    ob.setNodenm(getTagValue("nodenm",eElement));
                    ob.setRoutenm(getTagValue("routenm",eElement));

                    arrayList.add(ob);
                }
            }
        }
        catch (IOException e){
            System.out.println("IOException : 실패");
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException : 실패");
            e.printStackTrace();
        } catch (SAXException e) {
            System.out.println("SAXException : 실패");
            e.printStackTrace();
        }

    }

    public  String getTagValue(String tag, Element eElement){ //xml에서 파싱할 데이터 찾기
        nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

}