package ogoubient.com.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.content.Context
import android.os.Build


/**
 * Created by OceanSpray on 9/20/2017.
 */

class Notifications {
    private var notifManager: NotificationManager? = null
    val CHANNEL_ONE_ID = "ogoubient.com.alarmmanager.ONE"

/*
    //Android O
    fun createChannels(context: Context,message:String,number:Int) {
        val SaveData = SaveData(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val build = Notification.Builder(context, CHANNEL_ONE_ID)
                    .setContentTitle(SaveData.getNoteTitle())
                    .setContentText(body)
                    .setSmallIcon(R.drawable.alert)
                    .setAutoCancel(true)

            val notificationChannel = NotificationChannel(CHANNEL_ONE_ID,CHANNEL_ONE_NAME, IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            nm.createNotificationChannel(notificationChannel)

        }
    }*/


    //Android > Eclair
    val NOTIFYTAG ="new request"
    fun Notify(context: Context,message:String,number:Int) {
        val intent = Intent(context,MainActivity::class.java)
        val SaveData = SaveData(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val buildO = Notification.Builder(context, CHANNEL_ONE_ID)
                    .setContentTitle(SaveData.getNoteTitle())
                    .setContentText(message)
                    .setSmallIcon(R.drawable.alert)
                    .setChronometerCountDown(true)
                    // .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)
            nm.notify(NOTIFYTAG, 0, buildO.build())

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            val builder = NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(SaveData.getNoteTitle())
                    .setContentText(message)
                    .setNumber(number)
                    .setSmallIcon(R.drawable.alert)
                    .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)
            nm.notify(NOTIFYTAG,0,builder.build())

        }else {
            val builder = NotificationCompat.Builder(context)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentTitle(SaveData.getNoteTitle())
                    .setContentText(message)
                    .setNumber(number)
                    .setSmallIcon(R.drawable.alert)
                    .setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)
            nm.notify(NOTIFYTAG.hashCode().toString(),0,builder.build())

        }

        //   val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }
    //Send your notifications to the NotificationManager system service//

    private fun getManager(context: Context): NotificationManager {
        if (notifManager == null) {
            notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notifManager as NotificationManager
    }


}