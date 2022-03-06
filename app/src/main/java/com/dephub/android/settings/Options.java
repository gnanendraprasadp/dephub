package com.dephub.android.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.dephub.android.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Options extends AppCompatActivity {
    String[] Listviewtitle = new String[]{
            "Help",
            "Follow Us On",
            "View Website",
            "Invite a Friend"};

    String[] Listviewdescription = new String[]{
            "Feedback, Policies, App Info",
            "Facebook Groups, Instagram",
            "View our official website",
            "Tell them about DepHub"};

    int[] images = new int[]{
            R.drawable.ic_help,
            R.drawable.ic_network,
            R.drawable.ic_website,
            R.drawable.ic_invite};

    int[] external = new int[]{
            R.drawable.ic_external,
            R.drawable.ic_external,
            R.drawable.ic_external,
            R.drawable.ic_external};

    TextView txt;

    @SuppressLint({"SetTextI18n","ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        setContentView(R.layout.activity_settings);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow( ).setNavigationBarColor(getResources( ).getColor(R.color.black));
        }


        txt = findViewById(R.id.name);
        Calendar c = Calendar.getInstance( );
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        //noinspection ConstantConditions
        if (timeOfDay >= 0 && timeOfDay < 12) {
            txt.setText("Good Morning");
        } else if (timeOfDay >= 12 && timeOfDay < 18) {
            txt.setText("Good Afternoon");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            txt.setText("Good Evening");
        }

        Toolbar toolbar = findViewById(R.id.toolbarsettings);
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        int nightModeFlags = getResources( ).getConfiguration( ).uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            int white = Color.parseColor("#ffffff");
            toolbar.setTitleTextColor(white);
        } else {
            int black = Color.parseColor("#000000");
            toolbar.setTitleTextColor(black);
        }
        setSupportActionBar(toolbar);

        List<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>( );
        for (int x = 0; x <= 3; x++) {
            HashMap<String, String> hm = new HashMap<String, String>( );
            hm.put("ListTitle",Listviewtitle[x]);
            hm.put("ListDescription",Listviewdescription[x]);
            hm.put("Listimages",Integer.toString(images[x]));
            if (x == 2) {
                hm.put("Listimagesexternal",Integer.toString(external[x]));
            }
            alist.add(hm);
        }
        String[] from = {
                "ListTitle","ListDescription","Listimages","Listimagesexternal"
        };
        int[] to = {
                R.id.listview_text,R.id.listview_description,R.id.listviewimage,R.id.listviewexternal
        };

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext( ),alist,R.layout.listview_items_with_ext_desc,from,to);
        ListView listView = findViewById(R.id.listview1);
        listView.setDivider(null);
        listView.setDividerHeight(1);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                if (position == 0) {
                    Intent intent = new Intent(view.getContext( ),Help.class);
                    startActivity(intent);
                }
                if (position == 1) {
                    Intent intent = new Intent(view.getContext( ),Follow.class);
                    startActivity(intent);
                }
                if (position == 2) {
                    String website = "https://gnanendraprasadp.github.io/DepHub-Web";
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder( );
                    builder.setToolbarColor(ContextCompat.getColor(Options.this,R.color.colorPrimary));
                    builder.setShowTitle(true);
                    builder.addDefaultShareMenuItem( );
                    builder.setUrlBarHidingEnabled(true);
                    builder.setStartAnimations(Options.this,R.anim.slide_up,R.anim.trans);
                    builder.setExitAnimations(Options.this,R.anim.trans,R.anim.slide_down);
                    CustomTabsIntent customTabsIntent = builder.build( );
                    customTabsIntent.launchUrl(Options.this,Uri.parse(website));
                }
                if (position == 3) {
                    Intent intent52 = new Intent(Intent.ACTION_SEND);
                    intent52.setType("text/plain");
                    String shareBody2 = "About DepHub App";
                    String shareSub2 = "Hi there\n\nI found this new app called DepHub. It has a collection of Android Dependencies. You can find varieties of dependencies in this app and also the user interface is super friendly and more colourful.\n\nThe most exciting features are every dependency is assigned with QR Code, Some dependencies are provided with a YouTube video link which gives information about how to implement them. You can submit your dependency too.\n\nDownload this Android App: https://bit.ly/installdephubapp\n\nIf any query, feel free to mail them: mailtodephub@gmail.com\n\nThank You";
                    intent52.putExtra(Intent.EXTRA_SUBJECT,shareBody2);
                    intent52.putExtra(Intent.EXTRA_TEXT,shareSub2);
                    startActivity(Intent.createChooser(intent52,"Invite to DepHub using"));
                }
            }
        });
    }
}