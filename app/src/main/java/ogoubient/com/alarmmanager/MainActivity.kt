package ogoubient.com.alarmmanager

import android.app.FragmentManager
import android.app.Notification
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pop_time.view.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

/**
 *This app will allow the user to set up notifications to be reminded at a specific time .
 * The switch in the middle , is made so that the notification can be cancelled , if need be .
 * Reminder that the switch need to be on , before the time is selected or it will not work.
 */
class MainActivity : AppCompatActivity() {
    private val notification_one = 101
    private val notification_two = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val SaveData = SaveData(applicationContext)

        tvShowTime.setText(SaveData.getHour().toString() + ":" + SaveData.getMinute().toString()
                + " Note_Title : " + SaveData.getNoteTitle() + " Note : " + SaveData.getNote())

        cancel_alarm.setOnClickListener {
            val saveData = SaveData(applicationContext)
            saveData.cancelAlarm()
        }

    }

    // No impact on Code , if not selected .
    fun TimePick(view: View) {
        /**
         *For automated notifications , first save the time in sharedpreferences or some other methods , then initialize
         * the Swither to ischecked .
         *
         * So for instance , by clicking on  the switch , it opens a timepicker allowing the user to set their time .
         */
        var tp2 = tp2 as TimePicker
        val switch = alarm_switch as Switch
        tp2.setIs24HourView(true)

        /*     buDone2.setOnClickListener({
                 //This is the way to reference a function that is present , in a different class .
                 if (Build.VERSION.SDK_INT >= 23) {
                     switchTester(tp2.hour, tp2.minute)
                 } else {
                     switchTester(tp2.currentHour, tp2.currentMinute)
                 }
             })*/

        alarm_switch.setOnClickListener {


            if (switch.isChecked) {

                var Seconds = 0
                var alarmID = 423 // with using a different id , we can set up different notifications , under the same application .
                if (Build.VERSION.SDK_INT >= 23) {
                    tvShowTime.setText(tp2.hour.toString() + ":" + tp2.minute.toString())
                } else {
                    tvShowTime.setText(tp2.currentHour.toString() + ":" + tp2.currentMinute.toString())


                }
                val saveData = SaveData(applicationContext)
                if (Build.VERSION.SDK_INT >= 23) {
                    saveData.SaveData(alarmID, tp2.hour!!, tp2.minute!!, Seconds, "Alarm Time2", "Time To Wake Up2") //SharedPreferences

                } else {
                    saveData.SaveData(alarmID, tp2.currentHour!!, tp2.currentMinute!!, Seconds, "Alarm Time2", "Time To Wake Up2") //SharedPreferences
                }
                saveData.setAlarm()

            } else if (!switch.isChecked) {
                val saveData = SaveData(applicationContext)
                saveData.cancelAlarm()
            }
        }


    }


    // Here , we are simply opening the Fragment containing the Time Picker
    fun buSetTime(view: View) {
        val popTime = PopTime()
        val fm = fragmentManager
        popTime.show(fm, "Selected Time")


    }

    // We setting the time with the help of the information we got from the PopTime Fragment .
    // Android post O
    fun SetNotifications(Hours: Int, Minute: Int) {
        var Seconds = 0
        var alarmID = 454 // with using a different id , we can set up different notifications , under the same application .
        tvShowTime.setText(Hours.toString() + ":" + Minute.toString())

        val saveData = SaveData(applicationContext)
        saveData.SaveData(alarmID, Hours, Minute, Seconds, "Alarm Time1", "Time To Wake Up1") //SharedPreferences
        saveData.setAlarm()


        //    SaveData.setAlarm(Hours , Minute ,"Alarm Time")
    }

    //Set the notifications For O //
    fun SetNotificationO(Hours: Int, Minute: Int) {
        val saveData = SaveData(applicationContext)
        var Seconds = 0
        var alarmID = 454 // with using a different id , we can set up different notifications , under the same application .
        tvShowTime.setText(Hours.toString() + ":" + Minute.toString())
        saveData.SaveData(alarmID, Hours, Minute, Seconds, "Alarm Time Test O", "Alarm Time Test O") //SharedPreferences
        saveData.createChannels()

    }

    // Go directly to the settings menu of the app.
    fun goToNotificationSettings(channel: String) {
        val i = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
        startActivity(i)
    }
}