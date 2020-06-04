package com.example.lesson2.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.lesson2.R;

public class BatteryChangeReceiver extends BroadcastReceiver {

    private final int ID = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getAction() == Intent.ACTION_BATTERY_LOW) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(ID))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("BatteryMessage")
                    .setContentText("Телефон разряжается");
            notificationManager.notify(ID, builder.build());
        } else if (intent.getAction() == Intent.ACTION_BATTERY_OKAY) {
            notificationManager.cancel(ID);
        }
    }
}
