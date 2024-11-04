package com.example.beershop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.beershop.Activities.LoginDetails.LoginActivity;
import com.example.beershop.Models.GpsTracker;
import com.example.beershop.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private SupportMapFragment mapFragment;
    private Toolbar toolbar;
    private Drawable unwrappedDrawable, wrappedDrawable;
    private FirebaseAuth mAuth;
    private Double lat, lon;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private boolean isMembersVisible = false;
    private NavigationView navigationView;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageView app_logo = findViewById(R.id.app_logo);
        app_logo.bringToFront();

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs1 = getSharedPreferences("Check Data", MODE_PRIVATE);
        check = prefs1.getString("check", "");

        setUpDrawer();
    }


    public void setUpDrawer(){
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_menu_black);
        wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setClickable(true);
        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        setupNavigationView();
        mToggle.setHomeAsUpIndicator(wrappedDrawable);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.setDrawerIndicatorEnabled(false);
        mToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        navigationView.setCheckedItem(R.id.switchToggleButton);
//        navigationView.getMenu().performIdentifierAction(R.id.switchToggleButton, 0);
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchScreen(item.getItemId());
                return true;
            }

        });
    }

    private void switchScreen(int id) {

        switch (id){

            case R.id.mainCustomerFragment:
                if(check.matches("Customer")) {
                    startActivity(new Intent(this, CustomerLandingActivity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }else if(check.matches("No")) {
                    startActivity(new Intent(this, No_Activity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }else if(check.matches("Screen")){
                    startActivity(new Intent(this, ScreeningActivity.class));
                    mDrawerLayout.closeDrawers();
                    finish();
                }
                break;

            case R.id.userProfileFragment:
                startActivity(new Intent(this, ProfileActivity.class));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.map:
                startActivity(new Intent(this, MapActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.tokens:
                MenuItem t_received = navigationView.getMenu().findItem(R.id.creditsReceivedFragment);
                MenuItem t_remaining = navigationView.getMenu().findItem(R.id.creditsRemainingFragment);
             //   MenuItem t_used = navigationView.getMenu().findItem(R.id.orderHistoryFragment);

                MenuItem menuItem = navigationView.getMenu().findItem(R.id.tokens);
                final ImageView drawerSwitch = (ImageView) menuItem.getActionView().findViewById(R.id.drawer_switch);

                if (!isMembersVisible) {
                    t_received.setVisible(true);
                    t_remaining.setVisible(true);
                 //   t_used.setVisible(true);
                    isMembersVisible = true;
                    drawerSwitch.setImageResource(R.drawable.ic_up);
                } else {
                    t_received.setVisible(false);
                    t_remaining.setVisible(false);
                 //   t_used.setVisible(false);
                    isMembersVisible = false;
                    drawerSwitch.setImageResource(R.drawable.ic_down);
                }
                break;

            case R.id.creditsReceivedFragment:
                startActivity(new Intent(this, CreditsReceivedActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.creditsRemainingFragment:
                startActivity(new Intent(this, CreditsRemainingActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

//            case R.id.orderHistoryFragment:
//                startActivity(new Intent(this, OrderHistoryActivity.class));
//                mDrawerLayout.closeDrawers();
//                finish();
//                break;

            case R.id.refferals:
                startActivity(new Intent(this, ReferralsActivity.class));
                mDrawerLayout.closeDrawers();
                finish();
                break;

            case R.id.logout:
                Logout();
                mDrawerLayout.close();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        getLocation();
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.792148, -73.880210))
                .title("Spring Lake Tap House")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.268117,-74.328820))
                .title("Bar code"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.802616, -74.379961))
                .title("Finn’s Corner")
                .snippet("660 Washington Ave, Brooklyn, NY 11238, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(41.070785, -73.590161))
                .title("Mickey's Bar & Grill")
                .snippet("601 Riverside Ave #136, Lyndhurst, NJ 07071, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.257691, -73.880210))
                .title("Spring Lake Tap House")
                .snippet("810 NJ-71, Spring Lake, NJ 07762, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(41.201952, -74.781719))
                .title("Ringside Lounge")
                .snippet("475 Tonnelle Ave., Jersey City, NJ 07307, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.216285, -74.010102))
                .title("Asbury Ale House Sports Bar & Grille")
                .snippet("531 Cookman Ave, Asbury Park, NJ 07712, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.89037, -74.925430))
                .title("Chickie's and Pete's"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.89422,  -74.698870))
                .title("Polo's Bar & Grill")
                .snippet("50 NJ-183, Netcong, NJ 07857, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.931520, -74.025580))
                .title("Section 201")
                .snippet("704 River Rd, New Milford, NJ 07646, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.872830, -75.045370))
                .title("Time Out Sports Bar & Grill")
                .snippet("241 White Horse Pike, Barrington, NJ 08007, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.672680, -74.201030))
                .title("Terminal One Sports Bar")
                .snippet("566 Spring St, Elizabeth, NJ 07201, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.626182, -74.259096))
                .title("Styles Inn Sports Bar & Grill")
                .snippet("305 N Stiles St, Linden, NJ 07036, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.216267, -74.010076))
                .title("Hollywood Cafe & Sports Bar")
                .snippet("531 Cookman Ave, Asbury Park, NJ 07712, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.870932, -74.180121))
                .title("Public House 46 Sports Bar & Grill")
                .snippet("1081 US-46, Clifton, NJ 07013, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.799162, -74.058238))
                .title("Plank Road Inn")
                .snippet("1538 Paterson Plank Rd, Secaucus, NJ 07094, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.678299, -74.195876))
                .title("The Lobby NJ")
                .snippet("821 Spring St, Elizabeth, NJ 07201, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.913999, -74.050119))
                .title("Miller's Ale House - Paramus")
                .snippet("270 NJ-4, Paramus, NJ 07652, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.380731, -74.388224))
                .title("Ryan's Pub & Sports Bar")
                .snippet("299 Spotswood Englishtown Rd, Monroe Township, NJ 08831, United States"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(39.485083, -74.583805))
                .title("Tailgaters Sports Bar & Grille")
                .snippet("337 W White Horse Pike, Egg Harbor City, NJ 08215, United State"));

        if(lat==null || lon==null){
            Toast.makeText(getApplicationContext(), "Oopps...something went wrong in getting your current location",Toast.LENGTH_LONG).show();
        }else {

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title("Your current location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 5));
        }
    }

    public void getLocation(){
        GpsTracker gpsTracker = new GpsTracker(MapActivity.this);
        if(gpsTracker.canGetLocation()){
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public void Logout() {

        final PrettyDialog pDialog = new PrettyDialog(MapActivity.this);
        pDialog
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to logout?")
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorPrimary)
                .addButton(
                        "Yes",
                        R.color.colorPrimary,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                                mAuth.getInstance().signOut();

                                SharedPreferences sharedpreferences = getSharedPreferences("User Data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("name", "");
                                editor.putString("email", "");
                                editor.putString("image", "");
                                editor.putString("udid", "");
                                editor.apply();
                                editor.commit();

                                Toast.makeText(getApplicationContext(), "Sign out successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MapActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finishAffinity();
                                pDialog.dismiss();
                            }
                        }
                )
                .addButton("No",
                        R.color.pdlg_color_red,
                        R.color.pdlg_color_white,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        })
                .show();

    }

}
