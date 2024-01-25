package com.example.processscannerstockmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.processscannerstockmarket.Fragment.DeviceFragment;
import com.example.processscannerstockmarket.Fragment.ProfilePage;
import com.example.processscannerstockmarket.Fragment.TermsConditions;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Handler handler = new Handler();
    private boolean liveTextVisible = true;

   TextView  timeTextView , liveTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        View customToolbar = getLayoutInflater().inflate(R.layout.custom_toolbar_layout, toolbar, false);
        toolbar.addView(customToolbar);
        setSupportActionBar(toolbar);

        timeTextView = findViewById(R.id.timeTextView);
        liveTextView = findViewById(R.id.liveTextView);

        // Start updating time and blinking "Live" text
        startUpdates();


        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

//        loadFragment(new DeviceFragment());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        toolbar.getNavigationIcon()
                .setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        loadFragment(new DeviceFragment());

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.nav_device) {
                    fragment = new DeviceFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_analytics) {
                    fragment = new DeviceFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.nav_profile) {
                    fragment = new ProfilePage();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (item.getItemId() == R.id.nav_termscondition) {
                    fragment = new TermsConditions();
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    fragment = new DeviceFragment();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (fragment != null) {
                    loadFragment(fragment);
                }
                return true;
            }
        });
    }

    private void startUpdates() {
        // Schedule updates every second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
                toggleLiveText();
                handler.postDelayed(this, 1000); // 1000 milliseconds = 1 second
            }
        }, 1000); // Initial delay
    }

    private void updateTime() {
        // Get current time and update the timeTextView
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        timeTextView.setText(currentTime);
    }

    private void toggleLiveText() {
        liveTextVisible = !liveTextVisible;
        liveTextView.setVisibility(liveTextVisible ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle the drawer toggle when the hamburger icon is clicked
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.relativelayout, fragment)
                .commit();
    }
}