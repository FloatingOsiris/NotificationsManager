package ogoubient.com.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

// Obsolete for Android O
class myBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //When we receive the Intent , we pass the Message
        if(intent!!.action.equals("ogoubient.com.alarmmanager")) {
            var b = intent.extras
            // Toast.makeText(p0,b.getString("message"),Toast.LENGTH_LONG).show()
            val notifyme = Notifications()
            notifyme.Notify(context!!,b.getString("note"),10)
        }else if(intent!!.action.equals("android.intent.action.BOOT_COMPLETED")) {
            val saveData = SaveData(context!!)
            saveData.setAlarm()
        }
    }


}
