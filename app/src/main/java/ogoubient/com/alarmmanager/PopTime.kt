package ogoubient.com.alarmmanager

import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import kotlinx.android.synthetic.main.pop_time.view.*

class PopTime : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val myView = inflater!!.inflate(R.layout.pop_time, container, false)
        val buDone = myView.buDone as Button
        val tp1 = myView.tp1 as TimePicker
        tp1.setIs24HourView(true)

        buDone.setOnClickListener({
            //This is the way to reference a function that is present , in a different class .
            val ma = activity as MainActivity
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> // Android Version 0
                    ma.setNotificationsO(tp1.hour, tp1.minute)
                Build.VERSION.SDK_INT >= 23 -> ma.setNotifications(tp1.hour, tp1.minute)
                else -> ma.setNotifications(tp1.currentHour, tp1.currentMinute)
            }
            this.dismiss() // we dismiss , the TimePicker , once we done .
        })

        return myView
    }
}