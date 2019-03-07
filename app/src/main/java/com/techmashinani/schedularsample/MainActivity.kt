package com.techmashinani.schedularsample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mJobScheduler: JobScheduler? = null
     companion object {
         const val JOB_ID = 0
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        setup()
    }

    private fun setup() {
        btn_schedule.setOnClickListener { scheduleJob() }
        btn_cancel.setOnClickListener { cancelAll() }
    }

    private fun scheduleJob() {
        val serviceName = ComponentName(applicationContext, NotificationJobService::class.java)
        val jobBuilder = JobInfo.Builder(JOB_ID, serviceName).apply {
            setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        }
        mJobScheduler?.schedule(jobBuilder.build())
        Toast.makeText(this, "Job Scheduled, job will run when " +
                "the constraints are met.", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAll() {
        if(mJobScheduler != null) {
            mJobScheduler?.cancelAll()
            mJobScheduler = null
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
