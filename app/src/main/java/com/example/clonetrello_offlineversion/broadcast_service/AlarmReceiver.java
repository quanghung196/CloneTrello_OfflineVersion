package com.example.clonetrello_offlineversion.broadcast_service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.clonetrello_offlineversion.Card_Activity;
import com.example.clonetrello_offlineversion.R;


public class AlarmReceiver extends BroadcastReceiver {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builderToDay, builderTomorrow;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Receiver", "Làm con mẹ mày đi!!!");

        String taskName = intent.getStringExtra("taskName");
        String boardName = intent.getStringExtra("boardName");
        String cardName = intent.getStringExtra("cardName");
        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);
        int cardID = intent.getIntExtra("cardID", 0);

        String datetime = "";
        datetime = hour + ":" + minute;
        if (minute < 10) {
            datetime = hour + ":0" + minute;
        }

        Intent intentOpenApp = new Intent(context, Card_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("cardId", cardID);
        intentOpenApp.putExtra("TABLE_BOARD", bundle);
        PendingIntent pendingIntentToDay = PendingIntent.getActivity(context, cardID, intentOpenApp, 0);
        builderToDay = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_trello)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_trello))
                .setContentTitle("Trello")
                .setContentText("Thẻ " + cardName + " trong bảng " + boardName + " đã hết hạn")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(taskName + "\nThẻ " + cardName + " trong bảng " + boardName + " đã hết hạn"))
                .setPriority(1)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntentToDay);

        /*PendingIntent pendingIntentTomorrow = PendingIntent.getActivity(context, cardID, intent, 0);
        builderTomorrow = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_trello)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_trello))
                .setContentTitle("Trello")
                .setContentText("Thẻ " + cardName + " trong bảng " + boardName + " sẽ hết hạn vào ngày mai lúc " + datetime)
                .setPriority(1)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntentTomorrow);*/

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builderToDay.build());
        //notificationManager.notify(2, builderTomorrow.build());
    }
}
