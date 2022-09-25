# ForegroundService Basic Sample

## 개요
### Foreground Service
**Foreground Service**에 대해 개발 공식 문서에서는 다음과 같이 설명한다.

> Foreground services perform operations that are noticeable to user.
>
> Foreground services show a *status bar notification**, so that user are actively aware that your app is performing a task in the foreground and is consuming system resources.
> 
> Device that Android 12 (API level 31) or higher provide a streamlined experience for short-running foreground services. on these devices, the system wait 10 seconds before showing the notification associated with a foreground service.

간단하게 내가 이해하기에는 개발적으로 사용자에게 특정 정보에 대해서 정보 전달을 **알림**(Notification) 방식으로 해야할 때 사용하는 것이 **Foreground Service**인 것 같다.

### 앱 개요
아래에서 만들 앱은 `START SERVICE` 그리고 `STOP SERVICE` 두 개의 버튼으로 foreground service 동작을 수행하도록 한다.
`START SERVICE` 버튼을 누르게 되면, 알림(Notification)으로 앱 아이콘, 메세지 타이틀 그리고 메세지 콘텐츠 3가지 정보를 노출한다. 그리고 알림을 클릭 시, `MainActivity`로 이동하도록 한다.
`STOP SERVICE` 버튼을 누르게 되면, 실행 중이던 foregorund service가 종료되면서 해당 알림이 사라진다.

### 앱 실행화면
<img src="https://user-images.githubusercontent.com/40654227/192124528-277f2f88-ad87-4910-8dd6-ea46c68d75cd.png" height=550 align="left"/>
<img src="https://user-images.githubusercontent.com/40654227/192124526-00cb4f1d-6ab7-4055-b2a7-77166b122d8b.png" height=550 align="center"/>

## Code 
### build.gradle(:Module): ViewBinding 설정
``` kotlin 
android {
    ...
    buildFeatures{
        viewBinding = true
    }
}
```

### MyForegroundService.kt
``` kotlin
class MyForegroundService : Service() {
    companion object{
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val NOTIFICATION_MESSAGE = "notificationMessage"
    }

    @SuppressLint("RemoteViewLayout")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val notificationMessage = intent.getStringExtra(NOTIFICATION_MESSAGE)

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

### Manifest.xml: 알림 권한 허용, Service 등록
알림 권한 허용을 위해서 다음과 같은 코드를 `Manifest`에 추가해준다.

``<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>``


그리고 위에서 만든 `MyForegroundService`를 다음과 같이 `<application>` 안에 등록한다.

``<service android:name=".MyForegroundService"/>``

``` xml
<manifest 
    ... >

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>

    <application
        ... >
        <service android:name=".MyForegroundService"/>

        ...
    </application>

</manifest>
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
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        serviceIntent.putExtra(MyForegroundService.NOTIFICATION_MESSAGE, "Foreground Service Example in Android")

        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        stopService(serviceIntent)
    }
}
```
