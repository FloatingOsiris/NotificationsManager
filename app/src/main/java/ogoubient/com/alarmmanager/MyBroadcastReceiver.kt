package ogoubient.com.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// Obsolete for Android O
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action == SaveData.CHANNEL) {
            val b = intent.extras
            val notifyMe = Notifications()
            notifyMe.notify(context!!, b.getString(SaveData.Note_Key), System.currentTimeMillis().toInt())
        } else if (intent.action == SaveData.intent) {
            val saveData = SaveData(context!!)
            saveData.setAlarm()
        }
    }


}
