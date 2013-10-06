package com.cflocator.cflocator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by randi on 10/5/13.
 */
public class backgroundService extends Service {

    private Timer timer;
    private String unique_id;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

         this.unique_id = md5(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Log.i("timer", "Android id: " + this.unique_id);

        Log.i("timer", "Service creating");
        alertNotification(50, 2);

        timer = new Timer("backgroundServiceTimer");
        timer.schedule(updateTask, 1000L, 30 * 1000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("timer", "Service destroying");

        timer.cancel();
        timer = null;
    }

    @SuppressWarnings("deprecation")
	public void alertNotification(int distance, int count) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        CharSequence notiText = "CFLocator Alerts";
        long meow = System.currentTimeMillis();

        Notification notification = new Notification(icon, notiText, meow);

        Context context = getApplicationContext();
        CharSequence contentTitle = "CFLocator Alert!";
        CharSequence contentText = Integer.toString(count) + " CFLocator users within " + Integer.toString(distance) + "ft";
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        int SERVER_DATA_RECEIVED = 1;
        notificationManager.notify(SERVER_DATA_RECEIVED, notification);
    }

    private static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
            return "";
        }
}
