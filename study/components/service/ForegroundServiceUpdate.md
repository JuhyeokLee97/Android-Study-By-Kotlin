# ForegroundServiceUpdate

## 개요

### 앱 설명

### 실행화면

## Code

### AndroidManifest.xml
``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.devgeek.foregroundservicebasicsample">

    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ForegroundServiceBasicSample"
        tools:targetApi="31">
        <service android:name=".MyForegroundService"
                 android:foregroundServiceType="location"/>
       

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>


```

### GlobalApplication.kt
``` kotlin
class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    /**
     * 알림 채널 만들기
     *
     * Android O(API 26)에서 Notification Channel 개념이 추가되었다.
     * 그래서 Android O이상의 디바이스에 Notification을 띄우려먼 먼저 Channel을 생성해야한다.
     * 또한 NotificationChannel은 한 번만 생성하면 되기 때문에 Application()을 상속받는 [GlobalApplication] 에서 생성한다.
     * */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**
             * [CHANNEL_ID]: 고유한 채널 ID
             * [channelName]: 앱 알림 설정에서 확인할 수 있는 알림 채널 이름
             * [importance]: 알림의 중요도 수준
             */
            val channelName = "Foreground Service Channel Name"
            val importance = NotificationManager.IMPORTANCE_NONE
            val serviceChannel = NotificationChannel(CHANNEL_ID, channelName, importance)

            /** 알림 채널 등록 */
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object{
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}
```

### MyForegroundService.kt
``` kotlin
class MyForegroundService : Service() {

    private var countDownTimer: CountDownTimer? = null
    private var limitSec = 60

    override fun onCreate() {
        initCountDownTimer()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        buildNotification()

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

        countDownTimer = object: CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL){
            override fun onTick(millisUntilFinished: Long) {
                updateNotification()
                limitSec--
            }

            override fun onFinish() {}

        }.start()
    }

    private fun updateNotification() {
        // 서비스가 죽은 상태에서 업데이트를 요청하는 경우를 위한 예외처리
        try {
            val mNotificationManager = getSystemService(NotificationManager::class.java)
            mNotificationManager.notify(1, buildNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buildNotification(): Notification {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss")

        /** 알림 클릭 시, 보여질 [pendingIntent] 구현 */
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationTitle = "My Notification Title"
        val notificationMessage = sdf.format(date)

        // todo: Builder에 들어가는 context(this)가 맞는지 확인
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        return notification
    }

    companion object{
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val MILLIS_IN_FUTURE = 60000L
        const val COUNT_DOWN_INTERVAL = 1000L
    }
}
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
        }

    }

    private fun startService(){
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            this.startForegroundService(serviceIntent)
        }else{
            this.startService(serviceIntent)
        }
    }
}
```
