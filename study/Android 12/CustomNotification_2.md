## buld.gradle(Module)
``` kotlin
android {
    compileSdk 31

    defaultConfig {
        ...
        minSdk 21
        targetSdk 31
        ...
    }

    buildFeatures{
        viewBinding = true
    }
}
```

## AndroidManifest.xml
``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.devgeek.customnotification">

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>

    <application
        ...
        android:name=".GlobalApplication"
        ...
        tools:targetApi="31"
        ...         >
      
        ...
        <service android:name=".MyForegroundService"/>
    </application>

</manifest>
```

## GlobalApplication.kt
``` kotlin
class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
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

    companion object{
        const val CHANNEL_ID = "Android_12_custom_noti"
    }
}
```

## notification_default.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:orientation="horizontal">

    <ImageView
        android:layout_width="44dp"
        android:layout_height="44dp"
        android:layout_gravity="center_vertical"
        android:layout_marginLeft="10dp"
        android:src="@mipmap/ic_launcher" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="10dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvNotificationTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_title"
            android:textColor="@color/black"
            android:textSize="18sp" />

        <TextView
            android:id="@+id/tvNotificationMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_message"
            android:textSize="12sp" />
    </LinearLayout>

</LinearLayout>
```

## notification_big.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tvNotificationTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_title"
            android:textColor="@color/black"
            android:textSize="18sp" />

        <TextView
            android:id="@+id/tvNotificationMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_message"
            android:textSize="14sp" />

        <ImageView
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:layout_marginTop="10dp"
            android:layout_gravity="center"
            android:layout_marginLeft="10dp"
            android:src="@mipmap/ic_launcher" />
    </LinearLayout>

</LinearLayout>
```

## MyForegroundService.kt
``` kotlin
class MyForegroundService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationTitle = intent.getStringExtra(NOTIFICATION_TITLE)
        val notificationMessage = intent.getStringExtra(NOTIFICATION_MESSAGE)

        val notification = createNotification(
            title = notificationTitle!!,
            message = notificationMessage!!,
            PendingIntent.getActivity(
                this,
                REQUEST_CODE,
                Intent(this, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )
        )

        startForeground(SERVICE_ID, notification)

        return START_NOT_STICKY
    }

    @SuppressLint("RemoteViewLayout")
    private fun createNotification(
        title: String,
        message: String,
        pendingIntent: PendingIntent
    ): Notification {

        val notificationLayout = RemoteViews(packageName, R.layout.notification_default).apply {
            setTextViewText(R.id.tvNotificationTitle, title)
            setTextViewText(R.id.tvNotificationMessage, message)
        }

        val notificationBigLayout = RemoteViews(packageName, R.layout.notification_big).apply {
            setTextViewText(R.id.tvNotificationTitle, title)
            setTextViewText(R.id.tvNotificationMessage, message)
        }

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationBigLayout)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

    }

    companion object {
        const val REQUEST_CODE = 0
        const val SERVICE_ID = 1

        const val NOTIFICATION_MESSAGE = "notificationMessage"
        const val NOTIFICATION_TITLE = "notificationTitle"
    }
}
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
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

## MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            buttonStartService.setOnClickListener { startService() }
            buttonStopService.setOnClickListener { stopService() }
        }

    }

    private fun startService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java).apply {
            putExtra(MyForegroundService.NOTIFICATION_TITLE, "DevGeek - 푸시 알림")
            putExtra(MyForegroundService.NOTIFICATION_MESSAGE, "Android 12 - Changed Custom Notification")
        }

        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        stopService(serviceIntent)
    }
}
```






