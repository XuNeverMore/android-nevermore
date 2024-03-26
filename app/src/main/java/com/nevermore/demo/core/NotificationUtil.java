package com.nevermore.demo.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nevermore.demo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xct
 * create on: 2023/3/29 10:21
 */
public class NotificationUtil {

    private static final String CHANNEL_GROUP_ID_DEVICES = "com.seed.service.group.device1";

    private static final String CHANNEL_ID_DEVICE = "com.nevermore.channel.normal";

    private static final String CHANNEL_ID_Notify = "com.nevermore.channel.notify2";

    private NotificationUtil() {
    }

    private static final Map<String, Integer> mapNotificationId = new HashMap<>();

    public static void deleteChannels(Context context) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        for (NotificationChannel notificationChannel : managerCompat.getNotificationChannels()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                managerCompat.deleteNotificationChannel(notificationChannel.getId());
            }
        }
    }

    public static void showDevice(Context context, String deviceName, String mac) {
        if (TextUtils.isEmpty(deviceName)) {
            return;
        }
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        Log.i("xct", "is notification enable: " + managerCompat.areNotificationsEnabled());

        ensureChannelGroup(managerCompat);
        ensureChannel(managerCompat);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_DEVICE)
                .setContentTitle(deviceName)
                .setContentText(mac)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        Integer id = mapNotificationId.get(deviceName);
        if (id == null) {
            id = mapNotificationId.size() + 1;
            mapNotificationId.put(deviceName, id);
        }
        managerCompat.notify(id, notification);
    }

    public static void showNotify(Context context, String title, String content) {
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        Log.i("xct", "is notification enable: " + managerCompat.areNotificationsEnabled());

        ensureChannelGroup(managerCompat);
        ensureNotifyChannel(managerCompat);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID_Notify)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{300,300})
                .build();
        Integer id = mapNotificationId.get(title);
        if (id == null) {
            id = mapNotificationId.size() + 1;
            mapNotificationId.put(title, id);
        }
        managerCompat.notify(id, notification);
    }

    private static void ensureChannel(NotificationManagerCompat managerCompat) {
        NotificationChannelCompat channelCompat =
                new NotificationChannelCompat.Builder(CHANNEL_ID_DEVICE, NotificationManagerCompat.IMPORTANCE_HIGH)
                        .setGroup(CHANNEL_GROUP_ID_DEVICES)
                        .setName("Device")
                        .setDescription("控制器设备")
                        .build();
        managerCompat.createNotificationChannel(channelCompat);
    }

    private static void ensureNotifyChannel(NotificationManagerCompat managerCompat) {
        NotificationChannelCompat channelCompat =
                new NotificationChannelCompat.Builder(CHANNEL_ID_Notify, NotificationManagerCompat.IMPORTANCE_HIGH)
                        .setName("Notify")
                        .setDescription("Notify 通知")
                        .setVibrationEnabled(true)
                        .setVibrationPattern(new long[]{300,300})
                        .setLightsEnabled(true)
                        .build();
        managerCompat.createNotificationChannel(channelCompat);
    }

    private static void ensureChannelGroup(NotificationManagerCompat managerCompat) {
        NotificationChannelGroup group = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            group = new NotificationChannelGroup(CHANNEL_GROUP_ID_DEVICES, "控制器");
            if (managerCompat.getNotificationChannelGroup(CHANNEL_GROUP_ID_DEVICES) == null) {
                managerCompat.createNotificationChannelGroup(group);
            }
        }
    }
}
