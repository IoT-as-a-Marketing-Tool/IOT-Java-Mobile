//package com.example.iot_java_mobile.Services;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.example.iot_java_mobile.Activity.AdsPage;
//import com.example.iot_java_mobile.Activity.MainActivity;
//import com.example.iot_java_mobile.Domain.AdCampaign;
//import com.example.iot_java_mobile.Domain.EstProduct;
//import com.example.iot_java_mobile.Domain.Establishment;
//import com.example.iot_java_mobile.IOTJavaMobile;
//import com.example.iot_java_mobile.IoT;
//import com.example.iot_java_mobile.R;
//
//import org.altbeacon.beacon.Beacon;
//import org.altbeacon.beacon.BeaconManager;
//import org.altbeacon.beacon.BeaconParser;
//import org.altbeacon.beacon.MonitorNotifier;
//import org.altbeacon.beacon.RangeNotifier;
//import org.altbeacon.beacon.Region;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MyForegroundService extends Service {
//    List<EstProduct> estProductList = null;
//    List<AdCampaign> campaignList = null;
//    List<Establishment> establishmentList = null;
//    List<Integer> campaigns = new ArrayList<>();
//    ArrayList<String> uuids = new ArrayList<>();
//    public MyForegroundService() {
//    }
//
//    private static final int NOTIF_ID = 1;
//    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//
//        // do your jobs here
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            new IOTJavaMobile();
////        }
////        Context context = (Context) intent.getSerializableExtra("context");
//        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());
//
//        RangeNotifier rangeNotifier = new RangeNotifier() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                if (beacons.size() > 0) {
//                    Log.e("TAG", "didRangeBeaconsInRegion called with beacon count:  "+beacons.size());
//
//                    for (Beacon beacon : beacons) {
////                        Log.e(TAG, "first beacon = "+beacon.toString()+ String.format("%.2f", beacon.getDistance()) + " meters away.");
//                        String uuid=String.valueOf(beacon.getId1()).toUpperCase(Locale.ROOT);
//                        if(uuids.indexOf(uuid)== -1){
//                            uuids.add(uuid);
//                            Log.e("TAG", "beacon = "+beacon.toString()+ String.format("%.2f", beacon.getDistance()) + " meters away.");
//
//                            getEstProduct(uuid);
//                        }
//
//
//
//                    }
//
//                }
//            }
//        };
////        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
////        beaconManager.getBeaconParsers().clear();
////        beaconManager.getBeaconParsers().add(new BeaconParser().
////                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
////
//        beaconManager.addRangeNotifier(rangeNotifier);
//        beaconManager.startRangingBeacons(IoT.wildcardRegion);
//        Log.e("TAG", "beacons" + uuids.toString() );
//
//        startForeground();
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private void startForeground() {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(NOTIF_CHANNEL_ID, NOTIF_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
//            getSystemService(NotificationManager.class).createNotificationChannel(channel);
//        }
//
//        Notification.Builder builder = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            builder = new Notification.Builder(this, NOTIF_CHANNEL_ID).
//                    setSmallIcon(R.drawable.icons8_notification_96)
//                    .setContentTitle(getString(R.string.app_name))
//                    .setContentText("Service is running background")
//                    .setContentIntent(pendingIntent);
//        }
//
//        startForeground(NOTIF_ID, builder.build());
//    }
//    public void getEstProduct(String uuid){
//        Log.e("TAG", "getEstProduct: uuid = "+ uuid );
//
//        APIInterface apiInterface = APIClient.getRetrofitClient();
//        apiInterface.getEstProduct(uuid,MainActivity.custID).enqueue(new Callback<EstProduct>() {
//            @Override
//            public void onResponse(Call<EstProduct> call, Response<EstProduct> response) {
//                if(response.isSuccessful() && response.body()!= null){
//                    Log.e("Don", "SUCCESSSSS got EstProduct " + response.body());
//                    EstProduct estProduct = response.body();
////                    Toast.makeText(IOTJavaMobile.this, "Sending notification  " +estProduct,Toast.LENGTH_SHORT).show();
//                    Log.d("TAG", "Sending notification.");
//                    List<EstProduct>estProductList = new ArrayList<EstProduct>();
//                    List<AdCampaign>campaignList = new ArrayList<AdCampaign>();
//                    List<Establishment>establishmentList = new ArrayList<>();
//                    estProductList.add(estProduct);
//                    campaignList.add(estProduct.getCampaign());
//                    establishmentList.add(estProduct.getEstablishment());
//                    sendNotification(estProduct, uuids.indexOf(uuid));
//                    campaigns.add(estProduct.getCampaign().getId());
//
////                    new MyAsyncTask().execute(estProduct);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EstProduct> call, Throwable t) {
//                Log.e("TAG", "onFailure: "+ t.getMessage() );
//            }
//        });
//
//    }
//    private void sendNotification(EstProduct estProduct, Integer index) {
//        HashMap<String, List> hashMap = new HashMap<String, List>();
//        List<Integer> campaigns = new ArrayList<>();
//        campaigns.add(estProduct.getCampaign().getId());
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
//
//
//        String GROUP_KEY_ADS = "com.android.example.WORK_EMAIL";
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//
//        Log.e("TAG", "sendNotification: " );
////        Toast.makeText(IOTJavaMobile.this, "Inside SendNotification ",Toast.LENGTH_LONG).show();
//        AdCampaign campaign = estProduct.getCampaign();
//        Log.e("TAG", "sendNotification: campaign ="+ campaign );
//
//        Intent notifyIntent = new Intent(this, AdsPage.class);
//
//        notifyIntent.putExtra("GivingCampaignList", (Serializable) campaignList);
//        notifyIntent.putExtra("UnFavoriteCampaignList", (Serializable) campaigns);
//        notifyIntent.putExtra("GivingEstablishmentNameList", (Serializable) establishmentList);
//        stackBuilder.addNextIntentWithParentStack(notifyIntent);
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
//                this, 0, notifyIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
//        );
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            Toast.makeText(IOTJavaMobile.this, "Build version >= 0", Toast.LENGTH_SHORT).show();
//            NotificationCompat.Builder builder;
//
//            NotificationChannel channel = new NotificationChannel("Beacon Reference Notifications",
//                    "Beacon Reference Notifications", NotificationManager.IMPORTANCE_HIGH);
//            channel.enableLights(true);
//            channel.enableVibration(true);
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//            notificationManager.createNotificationChannel(channel);
//
//            builder = new NotificationCompat.Builder(this, channel.getId());
//            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//            builder.setContentTitle(campaign.getName());
//            builder.setContentText("Tap here to view the Ad");
//            builder.setContentIntent(notifyPendingIntent);
////            builder.setGroup(GROUP_KEY_ADS);
//
//            notificationManager.notify(index, builder.build());
//
//        }
//        else {
//            NotificationManager notificationManager =
//                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//            Notification.Builder builder = new Notification.Builder(this);
//            builder.setPriority(Notification.PRIORITY_HIGH);
//            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//            builder.setContentTitle(campaign.getName());
//            builder.setContentText("Tap here to view the Ad");
//            builder.setContentIntent(notifyPendingIntent);
//            notificationManager.notify(index, builder.build());
//        }
//
//
////        Log.e(TAG, "Stopping ranging");
////        beaconManager.stopRangingBeacons(IOTJavaMobile.wildcardRegion);
////        beaconManager.removeAllRangeNotifiers();
//    }
//
//
//}
