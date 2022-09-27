# CustomNotification 대응

Android 12 또는 이상 버전에서는 CustomNotification이...

## 개요

**Android 12**는 Custom Norification 모양과 동작이 완전히 바뀌게 되었다.</br>
이전에는 Custom Notification은 전체적인 알림 영역을 사용할 수 있었고 우리가 만든 layout 그리고 style들을 지정할 수 있었지만, 
이로 인해서 사용자에게 혼돈을 주거나 각각의 다른 디바이스에서의 호환성의 문제를 야기하게 되었다.

Android 12를 타겟하고 있는 앱에서는 `custom content view`를 사용하는 Notification들은 더이상 알림의 전체 영역을 사용할 수 없게된다.</br>
대신에 시스템이 표준 템플릿을 적용시킨다.
기본 템플릿은 **Custom Notification**이 다른 Notification과 데코레이션이 같도록 한다. </br>
이러한 동작은 `Notification.DecorationCustomViewStyle`과 거의 동일하다.



[참고](https://developer.android.com/about/versions/12/behavior-changes-12)
### Android 12 구체적 변경사항

- 접힌 상태에서의 콘텐츠 최대 높이는 `48dp` 이고 가로 공간도 줄어들었다.
    - Android 11 이하에서는 접힌 상태에서의 콘텐츠 최대 높이는 `106dp` 이었다.
- 모든 알림이 펼칠 수 있습니다.
    - 기존에 `setCustomContentView` 를 사용했다면, `setBigContentView` 를 사용하여 접힌 상태와 펼쳐진 상태가 일관되도록 하는 것을 권장한다.


### 앱 설명
Foreground Service를 통해서 Notification을 발송하는 앱이다.
Foreground Service를 이용하는 부분이 이해가 안된다면 [여기](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/components/service/ForegroundService%20Basic%20Sample.md)를 참고.

# setCustomContentView 사용 O AND setCustomContentBigView 사용 X
- 확장성이 사라지는 것 같다.
- 접힌 상태에서의 콘텐츠 최대 높이는 `48dp` 이고 가로 공간도 줄어들었습니다.
### 실행화면
<img src="" height=600/>

### notification_foreground.xml
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
            android:textSize="18sp" />

        <TextView
            android:id="@+id/tvNotificationMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_message"
            android:textSize="14sp" />
    </LinearLayout>

</LinearLayout>
```
- `Root.height=48dp` 일부러 고정

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

        val notificationLayout = RemoteViews(packageName, R.layout.notification_foreground).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
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

## setCustomContentView 사용 X AND setCustomContentBigView 사용 O

### 실행화면
<img src="" height=600/>

### notification_foreground_big.xml
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
            android:layout_marginLeft="10dp"
            android:src="@mipmap/ic_launcher" />
    </LinearLayout>

</LinearLayout>
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

        val notificationBigLayout = RemoteViews(packageName, R.layout.notification_foreground_big).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomBigContentView(notificationBigLayout)
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

## setCustomContentView 사용 O AND setCustomContentBigView 사용 O

### 실행화면
<img src="" height=600/>

### notification_foreground.xml
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
            android:textSize="18sp" />

        <TextView
            android:id="@+id/tvNotificationMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:text="notification_message"
            android:textSize="14sp" />
    </LinearLayout>

</LinearLayout>
```

### notification_foreground_big.xml
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
            android:layout_marginLeft="10dp"
            android:src="@mipmap/ic_launcher" />
    </LinearLayout>

</LinearLayout>
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

        val notificationLayout = RemoteViews(packageName, R.layout.notification_foreground).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }
        val notificationBigLayout = RemoteViews(packageName, R.layout.notification_foreground_big).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationBigLayout)
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

<!--
### Origin - MyForegroundService.kt
``` kotlin
package com.example.foregroundservicesampleapplication

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

/**
 * 1. ForegroundService Sample
 * 2. CustomNotification Sample Android 12 or higher
 * 3. Notification 상수 정리
 */
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

        val notificationLayout = RemoteViews(packageName, R.layout.notification_foreground).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }
        val notificationBigLayout = RemoteViews(packageName, R.layout.notification_foreground_big).apply {
            setTextViewText(R.id.tvNotificationTitle, "My Foreground Service")
            setTextViewText(R.id.tvNotificationMessage, notificationMessage)
        }
        // https://developer.android.com/training/notify-user/custom-notification?hl=ko
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            /** [CustomContentView]를 사용하지 않는 경우 ?? */
//            .setContent(remoteViews)
            /** [Style]를 지정해주지 않는다면 [CustomContentView]가 동작하는가? => 됨 */
//            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            /** [notificationLayout] 레이아웃은 최대 높이 48dp를 맞춰야 한다. 맞추지 않는 경우 잘려서 보임 -> 잘려서 보이는 화면도 있으면 좋겠다. */
            .setCustomContentView(notificationLayout)
            /** [CustomContentView] 사용 x AND [CustomContentBigView] 사용 o 인 경우, 기본적으로 어떻게 보일까? => 기본적인 [앱_아이콘], [앱_이름], [시간] 순으로 나온다.*/
            /** [CustomBigContentView]를 설정하지 않으면 확장 기능이 없어질까?? Yes 없어진다!! */
            .setCustomBigContentView(notificationBigLayout)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        /**
         * [START_NOT_STICKY] 와 비슷한 상수값 정리
         * https://developer.android.com/reference/android/app/Service#constants_1
         * */
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

-->
