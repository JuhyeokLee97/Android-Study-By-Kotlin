## 앱 매니페스트 수정

포그라운드 앱의 알림 수신, 데이터 페이로드 수신, 업스트림 메시지 전송 등을 수행하기 위해서 서비스를 확장한다.

### FirebaseMessagingServiceUtil.kt

```
package com.example.myfcmexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceUtil: FirebaseMessagingService(){
    val TAG = "FirebaseMessagingServiceUtil.class"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: ${token}")
    }

    /**
     * 디바이스가 FCM을 통해서 메시지를 받으면 수행된다.
     * @remoteMessage: FCM에서 보낸 데이터 정보들을 저장한다.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // FCM을 통해서 전달 받은 정보에 Notification 정보가 있는 경우 알림을 생성한다.
        if (remoteMessage.notification != null){
            sendNotification(remoteMessage)
        }else{
            Log.d(TAG, "수신 에러: Notification이 비어있습니다.")
        }
    }

    /**
     * FCM에서 보낸 정보를 바탕으로 디바이스에 Notification을 생성한다.
     * @remoteMessage: FCM에서 보
     */
    private fun sendNotification(remoteMessage: RemoteMessage){
        val id = 0
        var title = remoteMessage.notification!!.title
        var body = remoteMessage.notification!!.body

        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_ONE_SHOT)

        val channelId = "Channel ID"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(id, notificationBuilder.build())
    }
}
```

### AndroidManifest.xml에 service 추가

아래의 이미지와 같이 **application** 태그 안에 **service** 태그를 넣어준다.  
인터넷을 사용해야 하기 때문에 앱의 인터넷 사용을 허용해준다.

![앱 매니페스트 수정-1](https://user-images.githubusercontent.com/40654227/136282007-65642b6c-241f-488c-b8df-d07e6e8b4994.jpg)

```
<uses-permission android:name="android.permission.INTERNET"/>
```

```
<service
    android:exported="false"
    android:name=".FirebaseMessagingServiceUtil">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

## FCM Token 등록

### Device FCM Token 확인

FCM SDK는 앱을 처음 시작할 때 클라이언트 앱 인스턴스용 토큰을 생성한다.

단일 기기를 타겟팅하거나 기기 그룹을 만들려면 FirebaseMessagingService를 확장하고 onNewToken을 재정의해서 토큰에 접근해야한다.

**MainActivity.kt**

```
package com.example.myfcmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity.class"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getFCMToken()
    }

    private fun getFCMToken(): String?{
        var token: String? = null
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result

            // Log and toast
            Log.d(TAG, "FCM Token is ${token}")
        })

        return token
    }
}
```

실제 Device를 연결하여 앱을 실행하면 아래와 같이 로그를 통해 Token 값을 확인할 수 있다.

![앱 매니페스트 수정-2](https://user-images.githubusercontent.com/40654227/136369463-c68ee96b-51d6-4a0f-892c-26eb8cca6791.jpg)

### Device Token 등록

파이어베이스 콘솔에 접속하여 해당 프로젝트에 접속한다.  
아래와 같이 카테고리에서 **참여** > **Cloud Messaging** 로 접속한다.

![앱 매니페스트 수정-3](https://user-images.githubusercontent.com/40654227/136369822-3ce5a783-e878-43ee-b1d3-b9aa3494e887.jpg)

다음으로 **Send your first message** 버튼을 클릭한다.

![Device Token 등록-2](https://user-images.githubusercontent.com/40654227/136370201-2954d751-b3ef-49ae-a1a6-fe09a712ae02.jpg)

그럼 아래와 같은 화면을 보게 될 것이다.  
**알림 제목** 에 예시로 "title", **알림 텍트스** 에는 예시로 "body"를 넣는다.  
다음으로 **테스트 메시지 전송** 버튼을 클릭한다.

![Device Token 등록-3](https://user-images.githubusercontent.com/40654227/136370627-b40461c6-3323-45f6-b513-3f347da48cff.jpg)

이미지와 같이 토큰을 추가해야 한다.  
**FCM 등록 토큰 추가** 를 클릭하여

![Device Token 등록-4](https://user-images.githubusercontent.com/40654227/136370916-982fecbc-7b49-4e93-9678-4a7302dcf60f.jpg)

앱 로그에서 확인했던 토큰 값을 넣어준다.

![Device Token 등록-5](https://user-images.githubusercontent.com/40654227/136371049-2da58fc5-fd4b-475a-bd6d-eb1efe62330a.jpg)

마지막으로 사용할 토큰을 체크한 후 **테스트** 버튼을 클릭하게 되면 등록된 Device에 **remoteMessage** 로 데이터가 전송되며 해당 Device는 Notification을 받는다.
