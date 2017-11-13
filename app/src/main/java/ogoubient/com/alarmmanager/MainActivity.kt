package ogoubient.com.alarmmanager


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Switch
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

/**
 *This app will allow the user to set up notifications to be reminded at a specific time .
 * The switch in the middle , is made so that the notification can be cancelled , if need be .
 * Reminder that the switch need to be on , before the time is selected or it will not work.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val saveData = SaveData(applicationContext)

        tvShowTime.text = (saveData.getHour().toString() + ":" + saveData.getMinute().toString())

        cancel_alarm.setOnClickListener {
            saveData.cancelAlarm()
        }

    }

    // No impact on Code , if not selected .
    fun timePick(view: View) {
        val tp2 = tp2 as TimePicker
        val switch = alarm_switch as Switch
        tp2.setIs24HourView(true)

        alarm_switch.setOnClickListener {

            if (switch.isChecked) {

                val seconds = 0
                if (Build.VERSION.SDK_INT >= 23) {
                    val message = tp2.hour.toString() + ":" + tp2.minute.toString()
                    tvShowTime.text = message
                } else {
                    val message = tp2.currentHour.toString() + ":" + tp2.currentMinute.toString()
                    tvShowTime.text = message
                }
                val saveData = SaveData(applicationContext)
                if (Build.VERSION.SDK_INT >= 23) {
                    saveData.saveDataPrefs(SaveData.requestID.toInt(), tp2.hour, tp2.minute, seconds, SaveData.alarm_id_two_title, SaveData.alarm_id_two_note) //SharedPreferences

                } else {
                    saveData.saveDataPrefs(SaveData.requestID.toInt(), tp2.currentHour!!, tp2.currentMinute!!, seconds, SaveData.alarm_id_two_title, SaveData.alarm_id_two_note) //SharedPreferences
                }
                saveData.setAlarm()

            } else if (!switch.isChecked) {
                val saveData = SaveData(applicationContext)
                saveData.cancelAlarm()
            }
        }


    }

    fun buSetTime(view: View) {
        val popTime = PopTime()
        val fm = fragmentManager
        popTime.show(fm, SaveData.information)

    }

    // Android pre O
    fun setNotifications(Hours: Int, Minute: Int) {
        val seconds = 0
        val message = Hours.toString() + ":" + Minute.toString()
        Toast.makeText(applicationContext, SaveData.activated, Toast.LENGTH_SHORT).show()
        tvShowTime.text = message
        val saveData = SaveData(applicationContext)
        saveData.saveDataPrefs(SaveData.requestID.toInt(), Hours, Minute, seconds, SaveData.alarm_id_one_title, SaveData.alarm_id_one_note) //SharedPreferences
        saveData.setAlarm()

    }

    //Android post O
    fun setNotificationsO(Hours: Int, Minute: Int) {
        val saveData = SaveData(applicationContext)
        val seconds = 0
        val message = Hours.toString() + ":" + Minute.toString()
        Toast.makeText(applicationContext, SaveData.activated_O, Toast.LENGTH_SHORT).show()
        tvShowTime.text = message
        saveData.saveDataPrefs(SaveData.requestID.toInt(), Hours, Minute, seconds, SaveData.alarm_id_oreo_title, SaveData.alarm_id_oreo_note) //SharedPreferences
        saveData.createChannels()

    }

    // Go directly to the settings menu of the app.
    fun goToNotificationSettings(channel: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val i = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
            i.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            i.putExtra(Settings.EXTRA_CHANNEL_ID, channel)
            startActivity(i)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

    }
}