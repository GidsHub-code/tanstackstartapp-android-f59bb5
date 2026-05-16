package app.dropframe.dev.workers.nnadigideon20.tanstackstartapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        // Token available; forward to your backend if needed.
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        String title = getString(R.string.app_name);
        String body = "";
        if (message.getNotification() != null) {
            if (message.getNotification().getTitle() != null) title = message.getNotification().getTitle();
            if (message.getNotification().getBody() != null) body = message.getNotification().getBody();
        } else if (!message.getData().isEmpty()) {
            body = message.getData().toString();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) nm.notify((int) System.currentTimeMillis(), b.build());
    }
}
