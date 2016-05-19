package com.example.armadillianonimi.emergencyphonenumbers;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.content.Intent;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"EMERGENCY", "LOCATION", "SETTINGS"};
    int numberOfTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating The Toolbar and setting it as the Toolbar for the activity
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        TextView country = (TextView) findViewById(R.id.country);
        country.setText("Brasil");
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numberOfTabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)findViewById(R.id.tabs);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setDistributeEvenly(true);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        // EmergencyPhoneNumbersAPI api = new EmergencyPhoneNumbersAPI();
        openFlags();


    }

    public void selectAppBarColour(int position) {
        switch (position) {
            case 0:
                int emergencyColour = getResources().getColor(R.color.colorPrimary);
                toolbar.setBackgroundColor(emergencyColour);
                tabs.setBackgroundColor(emergencyColour);
                break;
            case 1:
                int locationColour = getResources().getColor(R.color.colorPrimaryLocation);
                toolbar.setBackgroundColor(locationColour);
                tabs.setBackgroundColor(locationColour);
                break;
            case 2:
                int settingsColour = getResources().getColor(R.color.colorPrimarySettings);
                toolbar.setBackgroundColor(settingsColour);
                tabs.setBackgroundColor(settingsColour);
                break;
        }
    }


    public void openFlags() {
        LinearLayout button = (LinearLayout) findViewById(R.id.set_country);
        final Context context = this;

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent flags = new Intent(context, CountrySelection.class);
                startActivity(flags);

            }

        });
    }

    // Callback method called when the user allows or denies the access to the location. We reload the map if we have the permission to do so.
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationTab locationTab = (LocationTab)adapter.getItem(1);
                    locationTab.loadMap();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}