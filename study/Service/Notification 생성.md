# Notification 생성

## 개요
Notification 이란...
Notification 사용하는 경우...


## Code

### MyApplication.kt: NotificationChannel 생성
Android 8.0 이상을 사용하는 앱에서는 Notification을 이용한 알림을 생성하기 위해서는 [NotificationChannel]를 필수적으로 시스템에 등록해야한다.
등록하는 방법은 [NotificationChannel] 객체를 생성하여 [NotificationManager]의 [createNotificationChannel] 함수를 이용하여 시스템에 등록할 수 있다.
그리고 등록하기 위해서는 Android 버전을 체크하여 요청하면 된다.

[NotificationChannel]이 생성되지 않은 상태에서는 [Notification]이 생성되지 않기 때문에 앱이 시작하는 시점에서 빠르게 채널을 등록하기를 구글에서는 권장하고있다.
그리고 이미 등록된 [NotificationChannel]은 재등록 하는 동작을 수행하지 않기 때문에 안정성을 위해서 자주 호출하라고 권장한다.
그래서 [Application](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/Utility/Application()%20in%20kotlin.md) 을 상속받는 클래스를 생성하여 채널을 등록하는게 좋다.

``` kotlin
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        setNotificationChannel()
    }

    private fun setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannels = mutableListOf<NotificationChannel>()

            notificationChannels.add(createNotificationChannel(NotificationHelper.NOTIFICATION_CHANNEL, getString(R.string.app_name), getString(R.string.notification_channel_description)))

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.let {
                it.createNotificationChannels(notificationChannels)
            }
        }
    }

    /**
     * [CHANNEL_ID]: 고유한 채널 ID
     * [channelName]: 앱 알림 설정에서 확인할 수 있는 알림 채널 이름
     * [importance]: 알림의 중요도 수준
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String, description: String): NotificationChannel {
        return NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            setDescription(description)
        }
    }
}
```
