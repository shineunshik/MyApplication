package org.techtown.myapplication.API_1;

import android.content.SyncStatusObserver;

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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ApiExplorer extends Thread {

    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if(nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    public void run(){

      try{
          StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6410000/buslocationservice/getBusLocationList"); /*URL*/
          urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=mpCKK0vB8d8I%2FXawDUzzlAsLZVdxFbFTUSFg6sBzw9tp3kLhU7H%2Bu2qlNbNaI0IK8gD0NK4Laky19EEQo3qALg%3D%3D"); /*Service Key*/
          urlBuilder.append("&" + URLEncoder.encode("routeId","UTF-8") + "=" + URLEncoder.encode("200000078", "UTF-8")); /*노선 ID*/
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


          Document document = DocumentBuilderFactory
                  .newInstance()
                  .newDocumentBuilder()
                  .parse(url+"");

          document.getDocumentElement().normalize();
          NodeList nList = document.getElementsByTagName("busLocationList");

          for(int temp = 0; temp < nList.getLength(); temp++){
              Node nNode = nList.item(temp);
              if(nNode.getNodeType() == Node.ELEMENT_NODE){
                  System.out.println("실패1111111");
                  Element eElement = (Element) nNode;
                  System.out.println("######################");
                  System.out.println("차량 번호판  : " + getTagValue("plateNo", eElement));
                  System.out.println("정류소 번호  : " + getTagValue("routeId", eElement));
                  System.out.println("######################");
              }
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
}