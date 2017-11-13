package ogoubient.com.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat


class Notifications {
    private var notifyManager: NotificationManager? = null

    //Android > Eclair
    private val notifyTag = SaveData.NOTIFYTAG

    fun notify(context: Context, message: String, number: Int) {
        val intent = Intent(context,MainActivity::class.java)
        val saveData = SaveData(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.sales) // display image below the notification
        val s = Notification.BigPictureStyle().bigPicture(largeIcon) //>Oreo
        val e = NotificationCompat.BigPictureStyle().bigPicture(largeIcon) //>Eclair

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val buildO = Notification.Builder(context, SaveData.CHANNEL_ONE_ID)
                        .setContentTitle(saveData.getNoteTitle())
                        .setContentText(message)
                        .setSmallIcon(R.drawable.alert)
                        .setChronometerCountDown(true)
                        .setAutoCancel(true)
                buildO.setStyle(s)
                nm.notify(notifyTag, SaveData.requestID.toInt(), buildO.build())

            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR -> {
                val builder = NotificationCompat.Builder(context)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentTitle(saveData.getNoteTitle())
                        .setContentText(message)
                        .setNumber(number)
                        .setSmallIcon(R.drawable.alert)
                        .setAutoCancel(true)
                builder.setStyle(e)
                builder.setContentIntent(PendingIntent.getActivity(context, SaveData.requestID.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT))

                nm.notify(notifyTag, SaveData.requestID.toInt(), builder.build())

            }
            else -> {
                val builder = NotificationCompat.Builder(context)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentTitle(saveData.getNoteTitle())
                        .setContentText(message)
                        .setNumber(number)
                        .setSmallIcon(R.drawable.alert)
                        .setAutoCancel(true)
                builder.setStyle(e)
                builder.setContentIntent(PendingIntent.getActivity(context, SaveData.requestID.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT))

                nm.notify(notifyTag.hashCode().toString(), SaveData.requestID.toInt(), builder.build())

            }
        }
    }

    private fun getManager(context: Context): NotificationManager {
        if (notifyManager == null) {
            notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notifyManager as NotificationManager
    }


}