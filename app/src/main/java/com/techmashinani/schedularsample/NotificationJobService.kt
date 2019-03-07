package com.techmashinani.schedularsample

import android.app.job.JobParameters
import android.app.job.JobService

class NotificationJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

}