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

    Button button;

    public NetworkChangeReceiver() {
    }

    public NetworkChangeReceiver(Button button) {
        this.button = button;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        if (NetworkUtil.getConnectivityStatus(context)) {
            button.setEnabled(true);
            notificationManager.cancelAll();
        } else {
            button.setEnabled(false);
            builder = new NotificationCompat.Builder(context, "2")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Broadcast Receiver")
                    .setContentText("Сеть пропала");
            notificationManager.notify(1, builder.build());
        }
    }

}
