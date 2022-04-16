# Android Splash Screen 예제(Kotlin)

## 개요

스플래시 화면을 통해 앱의 고유한 브랜딩을 유지할 수 있다.

### 실행화면

![](https://user-images.githubusercontent.com/40654227/144441351-95a259b1-19b7-4dde-8191-f785325a40b1.gif)

## 프로젝트 구조(이미지)

![](https://user-images.githubusercontent.com/40654227/144438616-03dffa74-14a5-45da-ab57-3099909bd2b0.png)

## Splash Screen Activity 만들기

### SplashScreen.kt

일정 시간 이후, **MainActivity** 로 이동한다.

```
class SplashScreen : AppCompatActivity() {

    private val splashDuration = 1500L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Handler()를 통해서 UI 쓰레드를 컨트롤 한다.
        // Handler().postDelayed(딜레이 시간){딜레이 이후 동작}
        //      postDelayed()를 통해 일정 시간(딜레이 시간)동안 쓰레드 작업을 멈춘다.
        //      {딜레이 이후 동작}을 통해 딜레이 시간 이후, 동작을 정의해준다.
        Handler().postDelayed(splashDuration){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
```

### activity\_splash\_screen.xml

스플래쉬 화면을 만든다.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FF5722"
    tools:context=".SplashScreen">


    <ImageView
        android:layout_width="250dp"
        android:layout_height="300dp"
        android:src="@drawable/ic_android"
        app:layout_constraintBottom_toBottomOf="@id/tvSplashScreen"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tvSplashScreen"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Splash Screen"
        android:textColor="@color/white"
        android:textSize="40sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## 앱 시작 Activity 변경하기

### AndroidManifest.xml

새로 만든 **SplahScreen Activity** 태그 안에 **<intent-filter>** 태그를 넣어준다.

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.splashapplication">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SplashApplication">
        <activity
            android:name=".SplashScreen"
            android:exported="true" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".MainActivity"
            android:exported="true">

        </activity>
    </application>

</manifest>
```

## 스플래쉬 이후 Activity

### MainActivity.kt

스플래쉬 화면 사용 방법만을 설명하려 했기 때문에, 이후 화면에는 EmptyActivity 기본 값으로 되어 있다.

```
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```
