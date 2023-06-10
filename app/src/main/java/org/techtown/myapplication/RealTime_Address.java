package org.techtown.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class RealTime_Address { //버스의 경유 정류소와  버스의 실시간 위치를 받아옴
    Context context;
    NodeList nlList;
    Node nValue;
    ArrayList<Ob> arrayList;
    Ob ob,ob1;
    ArrayList<Ob_Station> arrayList2;
    Ob_Station ob_station;
    String cityCode,routeId,url_address,endnodenm,startnodenm;
    String num ="" ;
    public  RealTime_Address(String cityCode,String routeId,String url_address,String endnodenm,String startnodenm){
        this.cityCode = cityCode;
        this.routeId = routeId;
        this.url_address = url_address;
        this.endnodenm = endnodenm;
        this.startnodenm = startnodenm;
    }

    public  void RealTime_Address_Call() throws IOException {


                try{
                        StringBuilder urlBuilder = new StringBuilder(url_address); /*URL*/
                        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
                        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); /*한 페이지 결과 수*/
                        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                        urlBuilder.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "=" + URLEncoder.encode(cityCode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
                        urlBuilder.append("&" + URLEncoder.encode("routeId", "UTF-8") + "=" + URLEncoder.encode(routeId, "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/
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
                        System.out.println("\n\n\n\n\n\n\n"+sb.toString());
                        //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

                        //xml 데이터를 파싱하기 위한 코드
                        Document document = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .parse(url + "");
                        document.getDocumentElement().normalize();

                        //totalcount를 파싱해서 변수에 넣어 totallist를 띄워줌
                        NodeList nList = document.getElementsByTagName("body"); //xml에서 파싱할 리스트명
                        Node nNode = nList.item(0);
                        Element eElement = (Element) nNode;
                        num= getTagValue("totalCount", eElement);



                        StringBuilder urlBuilder2 = new StringBuilder(url_address); /*URL*/
                        urlBuilder2.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
                        urlBuilder2.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                        urlBuilder2.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(num, "UTF-8")); /*한 페이지 결과 수*/
                        urlBuilder2.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*데이터 타입(xml, json)*/
                        urlBuilder2.append("&" + URLEncoder.encode("cityCode", "UTF-8") + "=" + URLEncoder.encode(cityCode, "UTF-8")); /*도시코드 [상세기능3 도시코드 목록 조회]에서 조회 가능*/
                        urlBuilder2.append("&" + URLEncoder.encode("routeId", "UTF-8") + "=" + URLEncoder.encode(routeId, "UTF-8")); /*노선ID [국토교통부(TAGO)_버스노선정보]에서 조회가능*/

                        url = new URL(urlBuilder2.toString());
                        HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                        conn2.setRequestMethod("GET");
                        conn2.setRequestProperty("Content-type", "application/json");
                        // System.out.println("Response code: " + conn.getResponseCode());


                        if (conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
                            rd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
                        } else {
                            rd = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
                        }

                        sb = new StringBuilder();
                        while ((line = rd.readLine()) != null) {
                            sb.append(line);
                        }
                        rd.close();
                        conn2.disconnect();
                        System.out.println("\n\n\n\n\n\n\n"+sb.toString());
                        //API를 사용하기위한 API정보 가져오기(기본 샘플 코드)

                        //xml 데이터를 파싱하기 위한 코드
                        Document document2 = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .parse(url + "");
                        document2.getDocumentElement().normalize();
                        NodeList nList2 = document2.getElementsByTagName("item"); //xml에서 파싱할 리스트명


                        arrayList = new ArrayList<>();
                        arrayList2 = new ArrayList<>();
                        if (nList2.getLength() == 0) {//list에 데이터가 없으면
                            //테스트용
                            ob = new Ob();
                            ob1 = new Ob();

                            ob.setVehicleno("부산 바 1234");
                            ob.setGpslati(37.432124);
                            ob.setGpslong(127.129064);
                            ob.setNodenm("모란역");
                            ob.setRoutenm("333");

                            ob1.setVehicleno("부산 바 5678");
                            ob1.setGpslati(37.439854);
                            ob1.setGpslong(127.128035);
                            ob1.setNodenm("태평역");
                            ob1.setRoutenm("51");
                            arrayList.add(ob);
                            arrayList.add(ob1);
                            System.out.println("\n\n\n\nn\n" + "1111111111111111111111111111111111111" + "\n\n\n\nn\n");


                        }

                        //for문 밑으로는  api가 작동하지않으면 안돌아감 내일 다시 확인
                        //안됐던 이유는 변수들이 지역번수라서 안됐던거 같음
                        //현재 전역변수로 다 돌려놔서 확인만 하면 됨

                        if (url_address.equals("http://apis.data.go.kr/1613000/BusRouteInfoInqireService/getRouteAcctoThrghSttnList")) {
                            for (int temp = 0; temp < nList2.getLength(); temp++) { //api가 작동을 하지않아 item 갯수가 없어서 for문이 작동을 안함
                                Node nNode2 = nList2.item(temp);
                                if (nNode2.getNodeType() == Node.ELEMENT_NODE) { //노드 type이 같을 경우에 실행
                                    //log 확인 작업
                                    Element eElement2 = (Element) nNode2;
                                    //    System.out.println("\n\n\n\n\n" + "버스 노선의 정류소" + "\n\n\n\n\n");
                                    ob_station = new Ob_Station();
                                    ob_station.setGpslati(Double.parseDouble(getTagValue("gpslati", eElement2)));
                                    ob_station.setGpslong(Double.parseDouble(getTagValue("gpslong", eElement2)));
                                    ob_station.setNodeid(getTagValue("nodeid", eElement2)); //정류소 ID
                                    ob_station.setNodenm(getTagValue("nodenm", eElement2)); //정류소명
                                    ob_station.setNodeord(getTagValue("nodeord", eElement2)); //정류소 순번
                                    ob_station.setRouteid(getTagValue("routeid", eElement2)); //노선ID
                                    ob_station.setEndnodenm(endnodenm); //종점
                                    ob_station.setStartnodenm(startnodenm); //기점
                                    arrayList2.add(ob_station);
                                    System.out.println("\n\n정류장 목록 불러오기 성공\n\n");
                                }
                            }
                        } else if (url_address.equals("http://apis.data.go.kr/1613000/BusLcInfoInqireService/getRouteAcctoBusLcList")) {
                            for (int temp = 0; temp < nList2.getLength(); temp++) { //api가 작동을 하지않아 item 갯수가 없어서 for문이 작동을 안함
                                Node nNode2 = nList2.item(temp);
                                if (nNode2.getNodeType() == Node.ELEMENT_NODE) { //노드 type이 같을 경우에 실행
                                    //log 확인 작업
                                    Element eElement2 = (Element) nNode2;
                                    //    System.out.println("\n\n\n\n\n" + "실시간 위치" + "\n\n\n\n\n");
                                    ob = new Ob();
                                    ob.setVehicleno(getTagValue("vehicleno", eElement2));//차량 번호
                                    ob.setGpslati(Double.parseDouble(getTagValue("gpslati", eElement2))); //위도
                                    ob.setGpslong(Double.parseDouble(getTagValue("gpslong", eElement2))); //경도
                                    ob.setNodenm(getTagValue("nodenm", eElement2)); //정류소명
                                    ob.setRoutenm(getTagValue("routenm", eElement2)); //노선번호
                                    ob.setNodeid(getTagValue("nodeid", eElement2)); //정류소 ID
                                    ob.setEndnodenm(endnodenm); //종점
                                    ob.setStartnodenm(startnodenm); //기점
                                    arrayList.add(ob);
                                    System.out.println("\n\n실시간 버스 위치 불러오기 성공\n\n");

                                }
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
                }catch (NullPointerException e){
                    System.out.println("NullPointerException : 실패");
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
