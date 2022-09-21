# ForegroundService Basic Sample

## 개요

### 앱 실행화면

## Code 
### build.gradle(:Module): ViewBinding 설정
``` kotlin 
android {
    ...
    buildFeatures{
        viewBinding true
    }
}
```

### MyForegroundService.kt
``` kotlin
class MyForegroundService : Service() {
    private val CHANNEL_ID = "ForegroundServiceChannel"

    @SuppressLint("RemoteViewLayout")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationMessage = intent.getStringExtra("notificationMessage")

        createNotificationChannel()
        
        /** 알림 클릭 시, 보여질 [pendingIntent] 구현 */
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
            
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
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
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val serviceChannel = NotificationChannel(CHANNEL_ID, channelName, importance)

            /** 알림 채널 등록 */
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
```

### activity_main.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/buttonStartService"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:padding="16dp"
        android:text="Start Service"
        android:textColor="#fff"
        app:layout_constraintBottom_toTopOf="@+id/buttonStopService"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_chainStyle="packed" />
    <Button
        android:id="@+id/buttonStopService"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="24dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        android:padding="16dp"
        android:text="Stop Service"
        android:textColor="#fff"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.5"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/buttonStartService"
        app:layout_constraintVertical_bias="0.218" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.foregroundservicesampleapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonStartService.setOnClickListener { startService() }
            buttonStopService.setOnClickListener { stopService() }
        }

    }

    private fun startService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")

        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        stopService(serviceIntent)
    }
}
```
