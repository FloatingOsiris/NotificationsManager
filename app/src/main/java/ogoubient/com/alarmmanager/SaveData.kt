package ogoubient.com.alarmmanager

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.widget.Toast
import java.util.*


class SaveData {
    private var context: Context? = null
    private var sharedRef: SharedPreferences? = null
    constructor(context: Context)  {
        this.context=context
        sharedRef=context.getSharedPreferences("myref",Context.MODE_PRIVATE)
    }

    companion object {
        var flag = 0
        var Second_Key = "seconds"
        var Minute_Key = "minute"
        var Hour_Key = "hour"
        var defValue = 0
        var defString = ""
        var alarmId_Key = "alarmID"
        var Note_Key = "note"
        var Note_Title_Key = "note_title"
        var NOTIFYTAG = "new request"
        var CHANNEL = "ogoubient.com.alarmmanager"
        var CHANNEL_ONE_ID = "ogoubient.com.alarmmanager.ONE"
        var CHANNEL_ONE_Name = "Hard Notifications"
        var cancellation = "Cancelled alarm"
        var intent = "android.intent.action.BOOT_COMPLETED"
        var requestID = System.currentTimeMillis()
        var alarm_id_one_title = "Alarm 1"
        var alarm_id_one_note = "Time To Wake Up /1"
        var alarm_id_two_title = "Alarm 2"
        var alarm_id_two_note = "Time To Wake Up /2"
        var alarm_id_oreo_title = "Alarm O"
        var alarm_id_oreo_note = "Come Visit our Store"
        var information = "Selected Time"
        var activated = "Alarm activated"
        var activated_O = "Alarm activated/O"

    }

    fun saveDataPrefs(alarmID: Int, hour: Int, minute: Int, seconds: Int, note_title: String, note: String) {
        val editor = sharedRef!!.edit()
        editor.putInt(alarmId_Key, alarmID)
        editor.putInt(Hour_Key, hour)
        editor.putInt(Minute_Key, minute)
        editor.putInt(Second_Key, seconds)
        editor.putString(Note_Title_Key, note_title)
        editor.putString(Note_Key, note)
        editor.apply()
    }

    private fun getalarmID(): Int {

        return sharedRef!!.getInt(alarmId_Key, defValue)

    }
    fun getHour():Int {

        return sharedRef!!.getInt(Hour_Key, defValue)
    }

    fun getMinute():Int {

        return sharedRef!!.getInt(Minute_Key, defValue)
    }
    fun getSeconds():Int {

        return sharedRef!!.getInt(Second_Key, defValue)
    }
    fun getNoteTitle():String {

        return sharedRef!!.getString(Note_Title_Key, defString)
    }

    private fun getNote(): String {

        return sharedRef!!.getString(Note_Key, defString)
    }

    // Set up the Alarm
   fun setAlarm () {
       val alarmID:Int=getalarmID()
       val hour:Int=getHour()
       val minute:Int=getMinute()
        val noteTitle: String = getNoteTitle()
       val note:String=getNote()

        //create a calendar instance.
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minute)
        calendar.set(Calendar.SECOND,0)

        //get alarm service and intent to the broadcastreceiver .
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MyBroadcastReceiver::class.java)
        alarmIntent.putExtra(Note_Key, note)
        alarmIntent.putExtra(Note_Title_Key, noteTitle)
        alarmIntent.action = CHANNEL

        val pi = PendingIntent.getBroadcast(context, alarmID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        am.setRepeating(AlarmManager.RTC_WAKEUP, requestID, AlarmManager.INTERVAL_DAY, pi)

    }

    //Create Channels for Android O
    fun createChannels() {
        val nm = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmID: Int = getalarmID()
        val hour: Int = getHour()
        val minute: Int = getMinute()
        val noteTitle: String = getNoteTitle()
        val note: String = getNote()
        val calendar = Calendar.getInstance()
        val alarmIntent = Intent(context, MyBroadcastReceiver::class.java)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create Channel
            val notificationChannel = NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_Name, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.setShowBadge(true)
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

            nm.createNotificationChannel(notificationChannel)

            //Set Alarm
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, defValue)
            alarmIntent.putExtra(Note_Key, note)
            alarmIntent.putExtra(Note_Title_Key, noteTitle)
            alarmIntent.action = CHANNEL

            val pi = PendingIntent.getBroadcast(context, alarmID, alarmIntent, flag)
            am.cancel(pi)
            am.setRepeating(AlarmManager.RTC_WAKEUP, requestID, AlarmManager.INTERVAL_DAY, pi)

        }
    }

    // Cancel the Alarm
    fun cancelAlarm() {
        val alarmID:Int=getalarmID()
        val noteTitle: String = getNoteTitle()
        val note:String=getNote()
        val am = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MyBroadcastReceiver::class.java)
        alarmIntent.putExtra(Note_Key, note)
        alarmIntent.putExtra(Note_Title_Key, noteTitle)
        alarmIntent.action = CHANNEL
        val pi = PendingIntent.getBroadcast(context, alarmID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        am.cancel(pi)
        Toast.makeText(context, cancellation, Toast.LENGTH_SHORT).show()
    }
}