//package com.example.iot_java_mobile;
//
//import android.app.Application;
//import android.content.Intent;
//import android.os.Build;
//
//import com.example.iot_java_mobile.Services.MyForegroundService;
//
//import org.altbeacon.beacon.Region;
//
//public class IoT extends Application {
//
//    public static final Region wildcardRegion = new Region("wildcardRegion", null, null, null);
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Intent intent = new Intent(this, MyForegroundService.class);
////            intent.putExtra("context", (Serializable) this);
//            startForegroundService(intent);
//        }
//    }
//}
