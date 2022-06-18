package com.example.iot_java_mobile;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.iot_java_mobile.Activity.AdPage;
import com.example.iot_java_mobile.Activity.AdsPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Domain.AdCampaign;
import com.example.iot_java_mobile.Domain.AdItem;
import com.example.iot_java_mobile.Domain.EstProduct;
import com.example.iot_java_mobile.Domain.Establishment;
import com.example.iot_java_mobile.Domain.Product;
import com.example.iot_java_mobile.Services.APIClient;
import com.example.iot_java_mobile.Services.APIInterface;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IOTJavaMobile extends Application implements MonitorNotifier {
    private static final String TAG = "BeaconReferenceApp";
    public static final Region wildcardRegion = new Region("wildcardRegion", null, null, null);
    public static boolean insideRegion = false;
    BeaconManager beaconManager = null;
    Handler mHandler = new Handler();
    HashSet<Beacon> visited_beacons;
    ArrayList<String> uuids;
    String GROUP_KEY_ADS = "com.android.example.WORK_EMAIL";
    NotificationChannel channel = null;
    NotificationManagerCompat notificationManager = null;
    int SUMMARY_ID = 0;
    boolean new_ad_flag = false;
    List<EstProduct> estProductList = null;
    List<AdCampaign> campaignList = null;
    List<Establishment> establishmentList = null;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate() {

        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        Log.e(TAG, "setting up background monitoring in app onCreate");
        beaconManager.addMonitorNotifier(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        for (Region region : beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(wildcardRegion);
        visited_beacons = new HashSet<Beacon>();
        uuids = new ArrayList<>();
        estProductList = new ArrayList<EstProduct>();
        campaignList = new ArrayList<AdCampaign>();
        establishmentList = new ArrayList<>();





    }

    @Override
    public void didEnterRegion(Region region) {
        new_ad_flag = false;
        Log.e(TAG, "did enter region.");
        insideRegion = true;
        final String[] detail = {"Bla bla"};

        RangeNotifier rangeNotifier = new RangeNotifier() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.e(TAG, "didRangeBeaconsInRegion called with beacon count:  "+beacons.size());
                    for (Beacon beacon : beacons) {
//                        Log.e(TAG, "first beacon = "+beacon.toString()+ String.format("%.2f", beacon.getDistance()) + " meters away.");
                        String uuid=String.valueOf(beacon.getId1()).toUpperCase(Locale.ROOT);
                        if(uuids.indexOf(uuid)== -1){
                            uuids.add(uuid);
                            new_ad_flag = true;
                            Log.e(TAG, "beacon = "+beacon.toString()+ String.format("%.2f", beacon.getDistance()) + " meters away.");

                            getEstProduct(uuid);
                        }



                    }

//                    if(new_ad_flag){
//                        Notification summaryNotification =
//                                new NotificationCompat.Builder(IOTJavaMobile.this, channel.getId())
//
//                                        .setContentText("New Ad messages")
//                                        .setSmallIcon(R.drawable.ic_baseline_star_outline_24)
//                                        //build summary info into InboxStyle template
////                        .setStyle(new NotificationCompat.InboxStyle()
////                                .addLine("Alex Faarborg  Check this out")
////                                .addLine("Jeff Chang    Launch Party")
////                                .setBigContentTitle("2 new messages")
////                                .setSummaryText("janedoe@example.com"))
//                                        //specify which group this notification belongs to
//                                        .setGroup(GROUP_KEY_ADS)
//                                        //set this notification as the summary for the group
//                                        .setGroupSummary(true)
//                                        .build();
//                        SUMMARY_ID = beacons.size();
//                        notificationManager.notify(SUMMARY_ID, summaryNotification);
//                    }

//                    Beacon firstBeacon = beacons.iterator().next();
//                    Log.e(TAG, "first beacon = "+firstBeacon );
//                    while (firstBeacon != null && (visited_beacons.contains(firstBeacon) || (firstBeacon.getDistance() > 1))){
//                        firstBeacon = beacons.iterator().next();
//                    }
//                    if(firstBeacon == null){
//
//                        Log.e(TAG, "didRangeBeaconsInRegion: first beacon == null");
//                    }
//                    Log.e(TAG, "first beacon = "+firstBeacon );
//                    if (firstBeacon != null){
//                        String uuid = String.valueOf(firstBeacon.getId1()).toUpperCase(Locale.ROOT);
//
//                        detail[0] = uuid + " is about " + firstBeacon.getDistance() + " meters away.";
//                        Log.e(TAG, "didRangeBeaconsInRegion: detail = "+detail[0]);
//                        Log.e("Don", "The first beacon " + uuid + " is about " + firstBeacon.getDistance() + " meters away.");
//
//                        getEstProduct(uuid);
//                        //TODO: make visited uuid when it sends notification
//                        Toast.makeText(IOTJavaMobile.this, "Returned from getEstProduct " ,Toast.LENGTH_LONG).show();
//                        visited_beacons.add(firstBeacon);
////                        beaconManager.stopMonitoring(region);
////                        beaconManager.startMonitoring(IOTJavaMobile.wildcardRegion);
//                    }
                }
            }
        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.startRangingBeacons(IOTJavaMobile.wildcardRegion);
        Log.e(TAG, "beacons" + uuids.toString() );

    }

    @Override
    public void didExitRegion(Region region) {
        insideRegion = false;
        Log.e(TAG, "didExitRegion: " );
        uuids.clear();
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }
    public void getEstProduct(String uuid){
        Log.e(TAG, "getEstProduct: uuid = "+ uuid );

        APIInterface apiInterface = APIClient.getRetrofitClient();
        apiInterface.getEstProduct(uuid,MainActivity.custID).enqueue(new Callback<EstProduct>() {
            @Override
            public void onResponse(Call<EstProduct> call, Response<EstProduct> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Log.e("Don", "SUCCESSSSS got EstProduct " + response.body());
                    EstProduct estProduct = response.body();
//                    Toast.makeText(IOTJavaMobile.this, "Sending notification  " +estProduct,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sending notification.");
                    estProductList.add(estProduct);
                    campaignList.add(estProduct.getCampaign());
                    establishmentList.add(estProduct.getEstablishment());
                    sendNotification(estProduct, uuids.indexOf(uuid));
//                    new MyAsyncTask().execute(estProduct);

                }
            }

            @Override
            public void onFailure(Call<EstProduct> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });

    }
    private void sendNotification(EstProduct estProduct, Integer index) {
        String GROUP_KEY_ADS = "com.android.example.WORK_EMAIL";

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        Log.e(TAG, "sendNotification: " );
//        Toast.makeText(IOTJavaMobile.this, "Inside SendNotification ",Toast.LENGTH_LONG).show();
        AdCampaign campaign = estProduct.getCampaign();
        Log.e(TAG, "sendNotification: campaign ="+ campaign );

        Intent notifyIntent = new Intent(this, AdsPage.class);

        notifyIntent.putExtra("GivingCampaignList", (Serializable) campaignList);
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
            NotificationManager notificationManager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            Notification.Builder builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle(campaign.getName());
            builder.setContentText("Tap here to view the Ad");
            builder.setContentIntent(notifyPendingIntent);
            notificationManager.notify(index, builder.build());
        }


//        Log.e(TAG, "Stopping ranging");
//        beaconManager.stopRangingBeacons(IOTJavaMobile.wildcardRegion);
//        beaconManager.removeAllRangeNotifiers();
    }



//    private class MyAsyncTask extends AsyncTask<String, Void, EstProduct>
//    {
//        WeakReference<NotificationManager> notificationManagerWeakReference;
//        WeakReference<Notification.Builder> builderWeakReference;
//        WeakReference<TaskStackBuilder> taskStackBuilderWeekReference;
//        MyAsyncTask(NotificationManager notificationManager, Notification.Builder builder, TaskStackBuilder taskStackBuilder) {
//            notificationManagerWeakReference = new WeakReference<NotificationManager>(notificationManager);
//            builderWeakReference = new WeakReference<Notification.Builder>(builder);
//            taskStackBuilderWeekReference = new WeakReference<TaskStackBuilder>(taskStackBuilder);
//        }
//
//        @Override
//        protected EstProduct doInBackground(String... strings) {
//
//            Log.e("Don", "doInBackground: " );
//            APIInterface apiInterface = APIClient.getRetrofitClient(Establishment.Location.class, new APIClient.LocationDeserializer());
//            try {
//                Response<EstProduct> response = apiInterface.getEstProduct(strings[0]).execute();
//                if(response.isSuccessful() && response.body()!= null){
//
//                    Log.e("Don", "SUCCESSSSS got EstProduct " + response.body());
//                    return response.body();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(TAG, "onFailure: error getting Est Product"+ e.getMessage() );
//            }
//            return null;
//
//        }
//
//        @Override
//        protected void onPostExecute(EstProduct estProduct) {
//            Toast.makeText(IOTJavaMobile.this, "inside on Post Execute " +estProduct,Toast.LENGTH_SHORT).show();
//
//            if (estProduct== null){
//                return;
//            }
//
//            super.onPostExecute(estProduct);
//            Toast.makeText(IOTJavaMobile.this, "Sending notification  " +estProduct,Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "Sending notification.");
//            NotificationManager notificationManager = notificationManagerWeakReference.get();
//            Notification.Builder builder = builderWeakReference.get();
//            TaskStackBuilder taskStackBuilder = taskStackBuilderWeekReference.get();
//            sendNotification(estProduct, notificationManager, builder, taskStackBuilder);
//
//        }
//    }

    class MyAsyncTask extends AsyncTask<EstProduct, Void, Void>{

        @Override
        protected Void doInBackground(EstProduct... estProducts) {
//            sendNotification(estProducts[0]);
            return null;
        }
    }

}
