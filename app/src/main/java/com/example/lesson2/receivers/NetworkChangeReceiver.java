package com.example.lesson2.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

import com.example.lesson2.NetworkUtil;
import com.example.lesson2.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private final int ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (NetworkUtil.getConnectivityStatus(context)) {
            notificationManager.cancel(ID);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(ID))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("NetworkMessage")
                    .setContentText("Соединение отсутствует");
            notificationManager.notify(ID, builder.build());
        }
    }
}
