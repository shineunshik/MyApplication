package org.techtown.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

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

public class PlaceBusSelect extends AppCompatActivity {

    FragmentPagerAdapter fragmentPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_bus_select);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("지역별 버스 조회"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentPagerAdapter = new ViewPagerAdapter_PlaceBusSelect(getSupportFragmentManager(),1);
        ((ViewPagerAdapter_PlaceBusSelect) fragmentPagerAdapter).getText(getIntent().getStringExtra("seoul"));
        viewPager.setAdapter(fragmentPagerAdapter);



    }

}