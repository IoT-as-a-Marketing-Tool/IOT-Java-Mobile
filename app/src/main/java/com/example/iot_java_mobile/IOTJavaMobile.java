package com.example.iot_java_mobile;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.iot_java_mobile.Activity.AdPage;
import com.example.iot_java_mobile.Activity.MainActivity;
import com.example.iot_java_mobile.Activity.ProductPage;
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
import java.lang.ref.WeakReference;
import java.time.temporal.WeekFields;
import java.util.Collection;
import java.util.HashSet;
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

    public void onCreate() {

        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);
        Log.e(TAG, "setting up background monitoring in app onCreate");
        beaconManager.addMonitorNotifier(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        for (Region region: beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(wildcardRegion);
        visited_beacons = new HashSet<Beacon>();



    }

    @Override
    public void didEnterRegion(Region region) {
        Log.e(TAG, "did enter region.");
        insideRegion = true;

        final String[] detail = {"Bla bla"};
        RangeNotifier rangeNotifier = new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.e(TAG, "didRangeBeaconsInRegion called with beacon count:  "+beacons.size());
                    Beacon firstBeacon = beacons.iterator().next();
                    Log.e(TAG, "first beacon = "+firstBeacon );
                    while (firstBeacon != null && (visited_beacons.contains(firstBeacon) || (firstBeacon.getDistance() > 1))){
                        firstBeacon = beacons.iterator().next();
                    }
                    if(firstBeacon == null){

                        Log.e(TAG, "didRangeBeaconsInRegion: first beacon == null");
                    }
                    Log.e(TAG, "first beacon = "+firstBeacon );
                    if (firstBeacon != null){
                        String uuid = String.valueOf(firstBeacon.getId1()).toUpperCase(Locale.ROOT);

                        detail[0] = uuid + " is about " + firstBeacon.getDistance() + " meters away.";
                        Log.e(TAG, "didRangeBeaconsInRegion: detail = "+detail[0]);
                        Log.e("Don", "The first beacon " + uuid + " is about " + firstBeacon.getDistance() + " meters away.");

                        getEstProduct(uuid);
                        //TODO: make visited uuid when it sends notification
                        Toast.makeText(IOTJavaMobile.this, "Returned from getEstProduct " ,Toast.LENGTH_LONG).show();
                        visited_beacons.add(firstBeacon);
//                        beaconManager.stopMonitoring(region);
//                        beaconManager.startMonitoring(IOTJavaMobile.wildcardRegion);
                    }
                }
            }
        };
        beaconManager.addRangeNotifier(rangeNotifier);
        beaconManager.startRangingBeacons(IOTJavaMobile.wildcardRegion);

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }, 3000);


    }

    @Override
    public void didExitRegion(Region region) {
        insideRegion = false;
        Log.e(TAG, "didExitRegion: " );
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }
    public void getEstProduct(String uuid){
        Log.e(TAG, "getEstProduct: uuid = "+ uuid );

        APIInterface apiInterface = APIClient.getRetrofitClient(Establishment.Location.class, new APIClient.LocationDeserializer());
        apiInterface.getEstProduct(uuid).enqueue(new Callback<EstProduct>() {
            @Override
            public void onResponse(Call<EstProduct> call, Response<EstProduct> response) {
                if(response.isSuccessful() && response.body()!= null){
                    Log.e("Don", "SUCCESSSSS got EstProduct " + response.body());
                    EstProduct estProduct = response.body();
//                    Toast.makeText(IOTJavaMobile.this, "Sending notification  " +estProduct,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sending notification.");
                    //sendNotification(estProduct);
                    new MyAsyncTask().execute(estProduct);

                }
            }

            @Override
            public void onFailure(Call<EstProduct> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });

    }
    private void sendNotification(EstProduct estProduct) {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Beacon Reference Notifications",
                    "Beacon Reference Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, channel.getId());
        }
        else {
            builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        Log.e(TAG, "sendNotification: " );
        Toast.makeText(IOTJavaMobile.this, "Inside SendNotification ",Toast.LENGTH_LONG).show();
        AdCampaign campaign = estProduct.getCampaign();

        Intent intent = new Intent(this, AdPage.class);
        intent.putExtra("GivingAdCampaign", campaign);
        intent.putExtra("GivingEstablishment", estProduct.getEstablishment().getName());
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle(campaign.getName());
        builder.setContentText("Tap here to view the Ad");
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, builder.build());

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
            sendNotification(estProducts[0]);
            return null;
        }
    }

}
