package ogoubient.com.alarmmanager

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import java.util.*
import android.app.AlarmManager





/**
 * Created by OceanSpray on 9/20/2017.
 */

class SaveData {
    val CHANNEL_ONE_ID = "ogoubient.com.alarmmanager.ONE"
    val CHANNEL_ONE_NAME = "Hard Notifications"

    var context:Context?=null
    var sharedRef:SharedPreferences?=null
    constructor(context: Context)  {
        this.context=context
        sharedRef=context.getSharedPreferences("myref",Context.MODE_PRIVATE)
    }

    fun SaveData(alarmID:Int, hour:Int,minute:Int,seconds:Int,note_title:String ,note:String) {
        var editor = sharedRef!!.edit()
        editor.putInt("alarmID",alarmID)
        editor.putInt("hour",hour)
        editor.putInt("minute",minute)
        editor.putInt("seconds",seconds)
        editor.putString("note_title",note_title)
        editor.putString("note",note)
        editor.commit()
    }

    fun getalarmID():Int {

        return  sharedRef!!.getInt("alarmID",0)

    }
    fun getHour():Int {

        return  sharedRef!!.getInt("hour",0)
    }

    fun getMinute():Int {

        return  sharedRef!!.getInt("minute",0)
    }
    fun getSeconds():Int {

        return  sharedRef!!.getInt("seconds",0)
    }
    fun getNoteTitle():String {

        return  sharedRef!!.getString("note_title","")
    }

    fun getNote():String {

        return  sharedRef!!.getString("note","")
    }

   // fun setAlarm (hour:Int,minute:Int,note:String) {
    // Set up the Alarm
   fun setAlarm () {
       val alarmID:Int=getalarmID()
       val hour:Int=getHour()
       val minute:Int=getMinute()
       val note_title:String=getNoteTitle()
       val note:String=getNote()

        // First , we create a calendar instance , with all of the specifications.
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minute)
        calendar.set(Calendar.SECOND,0)

        // Then we get the Alarm Service , and intent to the broadcastreceiver .
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarm_intent=Intent(context,myBroadcastReceiver::class.java)
     //  alarm_intent.putExtra("alarmID",alarmID)
        alarm_intent.putExtra("note",note)
       alarm_intent.putExtra("note_title",note_title)
        alarm_intent.action= "ogoubient.com.alarmmanager"

        val pi=PendingIntent.getBroadcast(context,alarmID,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT)

        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pi)

    }


    //Create Channels for Android O
    fun createChannels() {
        val nm = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmID: Int = getalarmID()
        val hour: Int = getHour()
        val minute: Int = getMinute()
        val note_title: String = getNoteTitle()
        val note: String = getNote()
        val calendar = Calendar.getInstance()
        var alarm_intent = Intent(context, myBroadcastReceiver::class.java)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create Channel
            val notificationChannel = NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            nm.createNotificationChannel(notificationChannel)

            //Set Alarm
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            //  alarm_intent.putExtra("alarmID",alarmID)
            alarm_intent.putExtra("note", note)
            alarm_intent.putExtra("note_title", note_title)
            alarm_intent.action = "ogoubient.com.alarmmanager"

            val pi = PendingIntent.getBroadcast(context, alarmID, alarm_intent, 0)
            am.cancel(pi)
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)

        }
    }

    // Cancel the Alarm
    fun cancelAlarm() {
        val alarmID:Int=getalarmID()
        val note_title:String=getNoteTitle()
        val note:String=getNote()
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var alarm_intent=Intent(context,myBroadcastReceiver::class.java)
        alarm_intent.putExtra("note",note)
        alarm_intent.putExtra("note_title",note_title)
        alarm_intent.action= "ogoubient.com.alarmmanager"
        val pi=PendingIntent.getBroadcast(context,alarmID,alarm_intent,PendingIntent.FLAG_CANCEL_CURRENT)

        am.cancel(pi)
        Toast.makeText(context, "Cancelled alarm", Toast.LENGTH_SHORT).show()
    }
}