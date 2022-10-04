``` kotlin
package com.example.foregroundservicesampleapplication

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * 1. ForegroundService Sample
 * 2. CustomNotification Sample Android 12 or higher
 * 3. Notification 상수 정리
 * 4. updateNotification
 */
@RequiresApi(Build.VERSION_CODES.M)
class MyForegroundService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"
    private var countDownTimer: CountDownTimer? = null
    private var limitSec = 60

    override fun onCreate() {
        initCountDownTimer()
    }
    @SuppressLint("RemoteViewLayout")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForeground(1, buildNotification())

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun initCountDownTimer(){
        if (countDownTimer != null){
            try {
                countDownTimer!!.cancel()
            }catch (e: Exception){
                e.printStackTrace()
            }
            countDownTimer = null
            limitSec = 60
        }

        countDownTimer = object: CountDownTimer(60000L, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                updateNotification(0)
                limitSec--
            }

            override fun onFinish() {
            }

        }.start()
    }

    private fun updateNotification(steps: Int) {
        // 서비스가 죽은 상태에서 업데이트를 요청하는 경우를 위한 예외처리
        try {
            val mNotificationManager = getSystemService(NotificationManager::class.java)
            mNotificationManager.notify(1, buildNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildNotification(): Notification{
//        val notificationMessage = intent.getStringExtra("notificationMessage")
        val notificationMessage = "notificationMessage"

        createNotificationChannel()

        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss")
        /** 알림 클릭 시, 보여질 [pendingIntent] 구현 */
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationLayout = RemoteViews(packageName, R.layout.notification_foreground).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, sdf.format(date))
        }
        val notificationBigLayout = RemoteViews(packageName, R.layout.notification_foreground_big).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }
        // https://developer.android.com/training/notify-user/custom-notification?hl=ko
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationBigLayout)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()

        return notification
    }
    /** 알림 채널 만들기 */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**
             * [CHANNEL_ID]: 고유한 채널 ID
             * [channelName]: 앱 알림 설정에서 확인할 수 있는 알림 채널 이름
             * [importance]: 알림의 중요도 수준
             */
            val channelName = "Foreground Service Channel Name"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val importance = NotificationManager.IMPORTANCE_NONE
            val serviceChannel = NotificationChannel(CHANNEL_ID, channelName, importance)

            /** 알림 채널 등록 */
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
```
