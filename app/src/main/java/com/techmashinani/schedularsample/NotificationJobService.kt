package com.techmashinani.schedularsample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationJobService : JobService() {

    lateinit var mNotifyManager: NotificationManager

    // notification channed ID
    companion object {
        const val PRIMARY_CHANNEL_ID: String = "primary_notification_channel"
        const val NOTIFICATION_ID = 0
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        createNotificationChannel()

        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)
        with(NotificationManagerCompat.from(this)){
            notify(NOTIFICATION_ID, createNotification(pendingIntent).build())
        }
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    private fun createNotificationChannel() {
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PRIMARY_CHANNEL_ID, "Job service notification", NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                enableVibration(true)
                lightColor = Color.RED
                description = "Notifications from job service"
            }

            mNotifyManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(intent: PendingIntent): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).apply {
            setContentTitle("Job Service")
            setContentText("Your job is running")
            setContentIntent(intent)
            setSmallIcon(R.drawable.ic_action_renew)
            priority = NotificationCompat.PRIORITY_HIGH
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setAutoCancel(true)
        }
    }

}