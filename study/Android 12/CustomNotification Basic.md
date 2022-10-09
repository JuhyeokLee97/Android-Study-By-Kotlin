# CustomNotification 대응 - Basic

## 개요

**Android 12**는 Custom Norification 모양과 동작이 완전히 바뀌게 되었다.</br>
이전에는 Custom Notification은 전체적인 알림 영역을 사용할 수 있었고 우리가 만든 layout 그리고 style들을 지정할 수 있었지만, 
이로 인해서 사용자에게 혼돈을 주거나 각각의 다른 디바이스에서의 호환성의 문제를 야기하게 되었다.

Android 12를 타겟하고 있는 앱에서는 `custom content view`를 사용하는 Notification들은 더이상 알림의 전체 영역을 사용할 수 없게된다.</br>
대신에 시스템이 표준 템플릿을 적용시킨다.
기본 템플릿은 **Custom Notification**이 다른 Notification과 데코레이션이 같도록 한다. </br>
이러한 동작은 `Notification.DecorationCustomViewStyle`과 거의 동일하다.

### Android 12 구체적 변경사항

- 접힌 상태에서의 콘텐츠 최대 높이는 `48dp` 이고 가로 공간도 줄어들었다.
    - Android 11 이하에서는 접힌 상태에서의 콘텐츠 최대 높이는 `106dp` 이었다.
- 모든 알림이 펼칠 수 있습니다.
    - 기존에 `setCustomContentView` 를 사용했다면, `setBigContentView` 를 사용하여 접힌 상태와 펼쳐진 상태가 일관되도록 하는 것을 권장한다.

### 앱 설명
Foreground Service를 통해서 Notification을 발송하는 앱이다.
Foreground Service를 이용하는 부분이 이해가 안된다면 
[여기](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/components/service/ForegroundService%20Basic%20Sample.md)를 참고.


### 실행화면
<img src="https://user-images.githubusercontent.com/40654227/194741271-db477d2e-05c5-4834-9079-7130b2365f10.gif" height=550/>


## Code
### build.gradle(Module) - ViewBining, targetSdk
ViewBinding 사용을 위해 `buildFeatures { viewBinding = true }`를 추가한다.
Android 12 대응을 위해 `complieSdk`와 `targetSdk` 값을 31로 지정한다.
``` kotlin
android {
    compileSdk 31

    defaultConfig {
        ...
        targetSdk 31
        ...
    }
    ...
    buildFeatures {
        viewBinding = true
    }
}
```

### GlobalApplication.kt - Create Notification Channel
Application Class를 상속받아 앱이 실행되면 Notification Cannel을 만든다.

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

### MyForegroundService.kt
포그라운드 서비스 구현을 위해 Service Class를 상속받아 Notification 생성을 구현한다.

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

### notification_default.xml - 알림 기본 Layout
``` xml
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
            android:textSize="15sp" />

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

### notification_big.xml - 알림 확장 Layout
``` xml
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
            android:src="@mipmap/ic_launcher" />
    </LinearLayout>

</LinearLayout>
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

### AndroidManifest.xml
포그라운드 서비스와 `GlobalApplication` 사용을 위해 AndroidManifest에 다음 내용을 추가한다.
- `<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>`
- `<application android:name=".GlobalApplication" ... >`
- `<service android:name=".MyForegroundService"/>`

``` xml
<manifest ...>

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>

    <application
        ...
        android:name=".GlobalApplication"
        ...>
        ...
        <service android:name=".MyForegroundService"/>
    </application>

</manifest>
```
