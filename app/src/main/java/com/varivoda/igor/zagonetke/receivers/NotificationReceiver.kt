package com.varivoda.igor.zagonetke.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.shared.Utils.Companion.getSelectedSound
import com.varivoda.igor.zagonetke.ui.shared.Utils.Companion.getSettings

const val ID = 100

class NotificationReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val i = Intent(context,NavigationActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(context,ID,i,PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setAutoCancel(true)

        if(getSelectedSound(context,context.getString(R.string.choose_sound))!=""){
            builder.setSound( Uri.parse("android.resource://" + context.packageName + "/R.raw." + getSelectedSound(context,context.getString(R.string.choose_sound))))
        }else{
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        }

        if(getSettings(context,context.getString(R.string.enable_notifications_resource))){
            notificationManager.notify(ID,builder.build())
        }


    }

}