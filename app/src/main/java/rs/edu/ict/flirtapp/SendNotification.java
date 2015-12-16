package rs.edu.ict.flirtapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;

/**
 * Created by Ognjen on 1.7.2015..
 */
public class SendNotification {

    public static int PreviousNotification = 1;
    Context context;
    String message;
    String title;
    String contetnTitle;

    public SendNotification(Context context) {
        this.context = context;
        SendNotification.PreviousNotification++;
    }

    public SendNotification(Context context, String message, String title, String contetnTitle) {
        this.context = context;
        this.message = message;
        this.title = title;
        this.contetnTitle = contetnTitle;
        SendNotification.PreviousNotification++;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void SendSimpleNotification()
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setTicker(title);
        notificationBuilder.setContentTitle(contetnTitle);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.drawable.logo);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(SendNotification.PreviousNotification,notificationBuilder.build());
    }

    public static void SendInlineNotification(Context context,String [] messages, String title, String contetnTitle)
    {
        NotificationCompat.InboxStyle notificationCompat = new NotificationCompat.InboxStyle();
        notificationCompat.setBigContentTitle(title);

        for(String message : messages)
        {
            notificationCompat.addLine(message);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentTitle(contetnTitle);
        notificationBuilder.setSmallIcon(R.drawable.logo);
        notificationBuilder.setStyle(notificationCompat);

        SendNotification.PreviousNotification++;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(SendNotification.PreviousNotification,notificationBuilder.build());
    }

    public void SendBigTextNotification()
    {
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(contetnTitle);
        bigTextStyle.bigText(message);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentTitle(contetnTitle);
        notificationBuilder.setContentText(message);
        notificationBuilder.setSmallIcon(R.drawable.logo);
        notificationBuilder.setStyle(bigTextStyle);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(SendNotification.PreviousNotification,notificationBuilder.build());
    }
}
