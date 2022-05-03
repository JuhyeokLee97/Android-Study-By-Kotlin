## 개요

### 시나리오

`배송업체`와 `송장번호`를 입력 후, `배송조회` 버튼 클릭 시 **네이버**에서 택배의 배송 상태를 추적한다.

### 실행영상
<p align="center">
    <img src="https://user-images.githubusercontent.com/40654227/166145994-f6e7a6ff-8687-4862-b6dd-9a10f30f4d1b.gif"/>
</p>

## 프로젝트 구조

![](https://user-images.githubusercontent.com/40654227/166146068-8115ce6a-7926-43e1-868a-d0e3621b8835.png)

### ViewBinding, Coil 사용 - In build.gradle(:app)

**ViewBinding**을 사용하기 위해 `viewBinding { enabled = true }` 를 **build.gradle(:app)**에 추가했다.

## Code

### AndroidManifest.xml
``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.searchdeliveryinfoapplication">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SearchDeliveryInfoApplication"
        android:usesCleartextTraffic="true">
        <activity
            android:name=".SearchDeliveryInfoActivity"
            android:exported="false" />
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
### activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_company"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="배송업체"
        android:textColor="#1c1c1c"
        android:textSize="14sp"
        app:layout_constraintBottom_toBottomOf="@id/et_company"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/et_company" />

    <EditText
        android:id="@+id/et_company"
        android:layout_width="0dp"
        android:layout_height="40dp"
        android:layout_marginHorizontal="10dp"
        android:hint="배송업체를 입력해주세요"
        android:padding="8dp"
        android:textColor="@color/black"
        android:textSize="13sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tv_number"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_number"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="송장번호"
        android:textColor="#1c1c1c"
        android:textSize="14sp"
        app:layout_constraintBottom_toBottomOf="@id/et_number"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/et_number" />

    <EditText
        android:id="@+id/et_number"
        android:layout_width="0dp"
        android:layout_height="40dp"
        android:layout_marginHorizontal="10dp"
        android:hint="송장번호를 입력해주세요"
        android:padding="8dp"
        android:textColor="@color/black"
        android:textSize="13sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tv_number"
        app:layout_constraintTop_toBottomOf="@id/et_company" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btn_search_delivery_info"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#00C73C"
        android:text="배송조회"
        android:textColor="@color/white"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/et_number" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 배송조회 버튼 클릭 리스너
        binding.apply {
            btnSearchDeliveryInfo.setOnClickListener {
                if (etCompany.text.isNullOrEmpty() || etNumber.text.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "배송업체 또는 송장번호를 입려해주세요.", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val intent = Intent(this@MainActivity, SearchDeliveryInfoActivity::class.java)
                val deliveryCompany = etCompany.text.toString()
                val trackingNumber = etNumber.text.toString()
                intent.putExtra("deliveryCompany", deliveryCompany)
                intent.putExtra("trackingNumber", trackingNumber)

                startActivity(intent)
            }
        }
    }
}
```

### activity\_search\_delivery\_info.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".SearchDeliveryInfoActivity">

    <WebView
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:id="@+id/web_view"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### SearchDeliveryInfoActivity.kt

```
class SearchDeliveryInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchDeliveryInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDeliveryInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchDeliveryBaseUrl ="https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="
        var deliveryCompany = intent.getStringExtra("deliveryCompany")
        var trackingNumber = intent.getStringExtra("trackingNumber")

        binding.webView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled=true
            loadUrl("$searchDeliveryBaseUrl$deliveryCompany+$trackingNumber")
        }
    }
}
```

## WebView 

### 인터넷 접근 권한 허용
``AndroidManifest.xml`` 파일에서 ``manifest`` 태그 안에 ``<uses-permission android:name="android.permission.INTERNET" />``를 추가해야 앱에서 인터넷을 통신을 사용할 수 있다.

### 에러: ERR_CLEARTEXT_NOT_PERMITTED
#### 원인
웹뷰가 실행되었지만, ``ERR_CLEARTEXT_NOT_PERMITTED``와 같은 에러가 발생할 때가 있다. 그 이유는 URL을 통해서 웹 페이지를 로드하는데 ``https``가 아닌 ``http`` 프로토콜을 사용하여 보안이 취약하기 때문이다. </br>
#### 해결방법
이를 해결하기 위해서는 ``AndroidManifest.xml`` 파일에서 ``application``태그 속성으로 ``android:usesCleartextTraffic="true"``를 추가해야 한다.

``` xml
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.SearchDeliveryInfoApplication"
        android:usesCleartextTraffic="true">
```

###  에러: 앱이 아닌 기본 웹 어플리케이션에서 열리는 웹 페이지
#### 해결방법
내가 만든 WebView의 속성 값 중 webViewClient를 안드로이드 SDK에서 제공하는 WebViewClient로 덮어씌운다.
``` kotlin
binding.webView.webViewClient = WebViewClient()
...
binding.webView.loadUrl("https://google.com")
```

### 에러: 웹뷰에서 보여지는 웹 페이지의 버튼 작동 X
#### 원인
``Android``에서는 기본적으로 보안을 위해 ``JavaScript``를 허용하지 않는다.

#### 해결방법
WebView를 초기화 할 때, ``JavaScript`` 사용을 허용한다.
``` kotlin
binding.webView.settings.javaScriptEnabled = true
```
