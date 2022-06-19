package com.example.iot_java_mobile.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Profile;
import com.example.iot_java_mobile.Domain.SessionManager;
import com.example.iot_java_mobile.Domain.User;
import com.example.iot_java_mobile.Fragments.FavoritesFragment;
import com.example.iot_java_mobile.Fragments.HomeFragment;
import com.example.iot_java_mobile.Fragments.NotificationFragment;
import com.example.iot_java_mobile.Fragments.SettingsFragment;
import com.example.iot_java_mobile.R;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;
//import com.example.iot_java_mobile.Services.MyForegroundService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.altbeacon.beacon.BeaconManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 2;
    private FusedLocationProviderClient fusedLocationClient;
    private BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    FirebaseAnalytics firebaseAnalytics;
    FavoritesFragment favoritesFragment;
    NotificationFragment notificationFragment;
    SettingsFragment settingsFragment;
    public static int custID = 0;

    static double latitude = 0.0;
    static double longitude = 0.0;
    FragmentTransaction fragmentTransaction;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyBluetooth();
        requestPermissions();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);



//        homeBrand = findViewById(R.id.homeBrand);
//        homeBrand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, brand.class));
//            }
//        });

//        homeAd = findViewById(R.id.homeAd);
//        homeAd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, Ad.class));
//            }
//        });
        sessionManager = new SessionManager(getApplicationContext());
        Profile profile = null;
        try {
            profile = sessionManager.getCustomerDetails().getProfile();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LocalDate birthday=LocalDate.parse(profile.getBirthday());
        LocalDate today= LocalDate.now();

        firebaseAnalytics.setUserProperty("user_age", String.valueOf(Period.between(birthday, today).getYears()));
        firebaseAnalytics.setUserProperty("user_country", profile.getCountry());
        firebaseAnalytics.setUserProperty("user_gender", profile.getGender());



        try {
            custID = sessionManager.getCustomerDetails().getId();
            Log.e("Don", "-----cussstID "+sessionManager.getCustomerDetails() );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            Log.e("Don", "------ lat and long " + latitude + " " + longitude);
                            Toast.makeText(MainActivity.this, "lat and long==  " + latitude + " " + longitude, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        homeFragment = new HomeFragment(sessionManager);
        notificationFragment = new NotificationFragment();
        favoritesFragment = new FavoritesFragment();
        settingsFragment = new SettingsFragment(sessionManager);


//        fragmentTransaction.replace(R.id.fragment_container_view, homeFragment);
//        fragmentTransaction.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
//// Commit the transaction
//        fragmentTransaction.commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view, homeFragment).commit();
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, homeFragment).commit();
                        return true;

                    case R.id.notification_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,notificationFragment ).commit();
                        return true;

                    case R.id.favorites_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, favoritesFragment).commit();
                        return true;

                    case R.id.settings_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
//        List<AdCampaign> notifications = new ArrayList<>();
//
//        APIInterface apiInterface = APIClient.getRetrofitClient();
//        apiInterface.getNotifications(MainActivity.custID).enqueue(new Callback<List<AdCampaign>>() {
//            @Override
//            public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {
//                if(response.isSuccessful() && response.body()!= null){
//                    Log.e("Don", "onResponse: "+ response.body() );
//                    notifications.addAll(response.body());
//                    for (int i = 0; i <notifications.size(); i++){
//                        AdCampaign n = notifications.get(i);
//                        sendNotification(n, i, notifications);
//                    }
//
//
////                    Log.e("Don", "SUCCESSSSS "+ response.body().get(0).getName());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AdCampaign>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error " +t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("This app needs background location access");
                            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @TargetApi(23)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                            PERMISSION_REQUEST_BACKGROUND_LOCATION);
                                }

                            });
                            builder.show();
                        }
                        else {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Functionality limited");
                            builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons in the background.  Please go to Settings -> Applications -> Permissions and grant background location access to this app.");
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }

                            });
                            builder.show();
                        }
                    }
                }
            } else {
                if (!this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                            PERMISSION_REQUEST_FINE_LOCATION);
                }
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.  Please go to Settings -> Applications -> Permissions and grant location access to this app.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }

            }
        }
    }



    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_FINE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Don", "fine location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Don", "background location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since background location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }
    private void verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Bluetooth not enabled");
                builder.setMessage("Please enable bluetooth in settings and restart this application.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finishAffinity();
                    }
                });
                builder.show();
            }
        }
        catch (RuntimeException e) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth LE not available");
            builder.setMessage("Sorry, this device does not support Bluetooth LE.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    finishAffinity();
                }

            });
            builder.show();

        }

    }

    private void sendNotification(AdCampaign campaign,int index, List<AdCampaign> campaignList) {
//        HashMap<String, List> hashMap = new HashMap<String, List>();
//        hashMap.put("campaigns", campaigns);
//
//        APIInterface apiInterface = APIClient.getRetrofitClient();
//        apiInterface.createNotifications(MainActivity.custID,hashMap).enqueue(new Callback<List<AdCampaign>>() {
//            @Override
//            public void onResponse(Call<List<AdCampaign>> call, Response<List<AdCampaign>> response) {
//                if(response.isSuccessful() && response.body()!= null){
//                    Log.e("Don", "onResponse: "+ response.body() );
//
//                    Log.e("Don", "SUCCESSSSSfully added notification ");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AdCampaign>> call, Throwable t) {
//                Log.e("Don", "not added notification Error: "+ t.getMessage());
//            }
//        });

        NotificationChannel channel = null;
        NotificationManagerCompat notificationManager = null;
        ArrayList<Establishment> establishmentList = new ArrayList<>();
        ArrayList<Integer> campaigns = new ArrayList<>();
        campaigns.add(campaign.getId());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        String TAG = "HELP";
        Log.e(TAG, "sendNotification: " );
//        Toast.makeText(IOTJavaMobile.this, "Inside SendNotification ",Toast.LENGTH_LONG).show();

        Log.e(TAG, "sendNotification: campaign ="+ campaign );

        Intent notifyIntent = new Intent(this, AdsPage.class);

        notifyIntent.putExtra("GivingCampaignList", (Serializable) campaignList);
        notifyIntent.putExtra("UnFavoriteCampaignList", (Serializable) campaigns);
        notifyIntent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);
        stackBuilder.addNextIntentWithParentStack(notifyIntent);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Toast.makeText(IOTJavaMobile.this, "Build version >= 0", Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder builder;

            channel = new NotificationChannel("Beacon Reference Notifications",
                    "Beacon Reference Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            notificationManager = NotificationManagerCompat.from(this);

            notificationManager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, channel.getId());
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle(campaign.getName());
            builder.setContentText("Tap here to view the Ad");
            builder.setContentIntent(notifyPendingIntent);
//            builder.setGroup(GROUP_KEY_ADS);

            notificationManager.notify(index, builder.build());

        }
        else {
            NotificationManager notifManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            Notification.Builder builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle(campaign.getName());
            builder.setContentText("Tap here to view the Ad");
            builder.setContentIntent(notifyPendingIntent);
            notifManager.notify(index, builder.build());
        }


//        Log.e(TAG, "Stopping ranging");
//        beaconManager.stopRangingBeacons(IOTJavaMobile.wildcardRegion);
//        beaconManager.removeAllRangeNotifiers();
    }



}