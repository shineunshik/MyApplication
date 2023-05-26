package org.techtown.myapplication;

public class Code_save {

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

    public void thread(){
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

