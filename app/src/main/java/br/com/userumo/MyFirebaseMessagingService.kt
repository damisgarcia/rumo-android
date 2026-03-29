package br.com.userumo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Mensagem recebida de: ${remoteMessage.from}")
        
        // Log dos dados (Data payload)
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("FCM", "Payload de dados: ${remoteMessage.data}")
        }

        // Log da notificação (Notification payload)
        remoteMessage.notification?.let {
            Log.d("FCM", "Corpo da notificação: ${it.body}")
            sendNotification(it.title, it.body, remoteMessage.data["url"])
        } ?: run {
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            val url = remoteMessage.data["url"]
            if (title != null && body != null) {
                sendNotification(title, body, url)
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "Novo token: $token")
    }

    private fun sendNotification(title: String?, body: String?, url: String?) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra("url", url ?: "https://app.userumo.com.br/app/notifications")
        }
        
        val pendingIntent = PendingIntent.getActivity(
            applicationContext, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "default_channel"
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
