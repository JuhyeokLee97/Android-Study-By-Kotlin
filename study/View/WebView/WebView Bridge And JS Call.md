# WebView Bridge And JS Call

## 개요 
### [WebView 란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/WebView/WebView%20%EB%9E%80.md)
WebView란 프레임워크에 내장된 웹 브라우저 컴포넌트로 View의 형태로 앱에 임베딩당하는 것을 말한다. 즉, WebView는 앱 내에 웹 브라우저를 넣는 것이다. 웹 페이지를 보기 위해서 혹은 앱 안에서 HTML을 호출하여 앱을 구현하는 하이브리드 형태의 앱을 개발하는데 많이 사용된다.


### [간단한 브릿지 개념](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/WebView/WebView%20Bridge.md)

Bridge(브릿지)란 Android와 WebView의 통신을 위해 만들어진 JavaScript용 Interface이다. Web에서 발생하는 이벤트에서 Android 동작(메서드)을 직접적으로 통제할 수 없기 때문에 Bridge라는 통로를 통해 Web에서 Android 동작을 호출한다. Bridge는 WebView에 적용될 Interface 구현체이고, WebView는 객체의 메서드들을 인스턴스를 통해 호출할 수 있다.

### [간단한 JS Call](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/WebView/WebView%20JS%20%ED%98%B8%EC%B6%9C.md)
안드로이드에서 웹뷰를 구현하다 보면, 웹과 통신이 필요한 경우가 있다. 데이터를 수신받아서 네이티브(안드로이드)에서 동작이 수행되어야 하는 경우에는 브릿지를 이용할 수 있다. 반대로 데이터를 송신해야 하는 경우에는 웹에서 구현된 Javascript 함수를 이용할 수 있다.

예를 들어 브릿지를 통해서 네이티브로 다이얼로그를 생성했고, 만약 [동의] 또는 [취소] 버튼을 눌러서 웹에서 동작을 수행해야 할 경우 앱에서는 웹에게 값을 전달해야할 것이다. 이때 콜백 함수처럼 웹에서 구현된 Javascript 함수를 네이티브로 앱에서 호출 할 수 있다.

### 앱 설명
네이티브에서 ID와 PW를 입력받아서 WebView의 Javascript 함수를 통해서 Web으로 값을 전달한다.
- [SEND ID] 버튼 클릭 시, 네이티브에 입력된 ID 값이 웹의 ID 텍스트 박스에 입력된다.
- [SEND PW] 버튼 클릭 시, 네이티브에 입력된 PW 값이 웹의 PW 텍스트 박스에 입력된다.
- [LOGIN] 버튼 클릭 시, 네이티브에 입력된 ID, PW 값이 웹의 ID, PW 텍스트 박스 각각에 입력된다.

웹뷰에서 ID와 PW를 입력받아서 Bridge를 통해서 Web으로 값을 전달받는다.
- [Send ID to Native] 버튼 클릭 시, WebView에 입력된 ID 값이 네이티브 ID ExitTextView 에 입력된다.
- [Send PW to Native] 버튼 클릭 시, WebView에 입력된 PW 값이 네이티브 PW ExitTextView 에 입력된다.
- [Login] 버튼 클릭 시, WebView에 입력된 ID, PW 값이 네이티브 ID, PW 각각의 ExitTextView 에 입력된다.
- [Clear] 버튼 클릭 시, WebView의 텍스트 박스 값이 지워진다.
### 앱 실행영상
<img src="https://user-images.githubusercontent.com/40654227/203548380-e0b68f00-79ca-40ce-bb86-0a2d7d3025a9.gif" height=400/>


## Code
### 프로젝트 구조
<img src="https://user-images.githubusercontent.com/40654227/203549630-7d192154-ab2f-43d0-a385-e5bb383cb1c7.png"/>

### Index
1. [build.gradle(:app): ViewBinding 사용](#buildgradleapp-viewbinding-사용)
2. [AndroidManifest.xml: Internet 권한 허용](#androidmanifestxml-internet-권한-허용)
3. [MyBridge.kt: 브릿지 정의](#mybridgekt-브릿지-정의)
4. [sample.html: 웹 화면](#samplehtml-웹뷰에-보여질-화면)
5. [index.js](#indexjs)
6. [activity_main.xml: 네이티브 + 웹뷰 화면](#activity_layoutxml)
7. [MainActivity.kt](#mainactivitykt)

### build.gradle(:app): ViewBinding 사용
``` kotlin
android {
    ...
    buildFeatures {
        viewBinding = true
    }
    ...
}
```

### AndroidManifest.xml: Internet 권한 허용
``` xml
<manifest ...>

    <uses-permission android:name="android.permission.INTERNET"/>

    <application
      ...
    </application>

</manifest>
```

### MyBridge.kt: 브릿지 정의
``` kotlin
class MyBridge(
    private val receivedId: ((id: String) -> Unit)? = null,
    private val receivedPw: ((pw: String) -> Unit)? = null,
    private val receivedLogin: ((id: String, pw: String) -> Unit)? = null
) {

    companion object {
        const val BRIDGE_NAME = "_BRIDGE_NAME_"
    }

    /** Web에 입력된 ID 값을 받아온다. */
    @JavascriptInterface
    fun receivedId(id: String) {
        Handler(Looper.getMainLooper()).post {
            receivedId?.invoke(id)
        }
    }

    /** Web에 입력된 PW 값을 받아온다. */
    @JavascriptInterface
    fun receivedPw(pw: String) {
        Handler(Looper.getMainLooper()).post {
            receivedPw?.invoke(pw)
        }
    }

    @JavascriptInterface
    fun receivedLogin(id: String, pw: String) {
        Handler(Looper.getMainLooper()).post {
            receivedLogin?.invoke(id, pw)
        }
    }
}
```
- `BRIDGE_NAME`: 프론트에서 브릿지를 호출할 때, 브릿지 함수임을 구분하기 위한 
- `@JavascriptInterface`: 해당 어노테이션을 명시해야 웹에서 브릿지 함수를 인식하고 사용할 수 있다.
- `Handler(Looper.getMainLooper()).post{ ... }`: 브릿지 함수를 실행 시킬 때는 하나의 스레드를 생성하여 동작하도록 해야합니다. 그러지 않으면 가끔 동작하지 않는 경우가 있습니다.
    - `Java exception was raised during method invocation` 다음과 같은 에러 로그가 웹에서 발견될 수 있다.

### sample.html: 웹뷰에 보여질 화면
``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
</head>

<body>

<p>
    <input type="text" name="text_id" id="text_id" placeholder="ID">
    <button id="btn_send_id">Send ID to Native</button>
    <button id="btn_clear_id">CLEAR</button>
</p>

<p>
    <input type="text" name="text_pw" id="text_pw" placeholder="Password">
    <button id="btn_send_pw">Send ID to Native</button>
    <button id="btn_clear_pw">CLEAR</button>
</p>

<p>
    <button id="btn_login">LOGIN</button>
</p>

<script src="index.js"></script>
</body>
</html>
```

### index.js
``` javascript
const text_id = document.getElementById("text_id")
const text_pw = document.getElementById("text_pw")
const btn_send_id = document.getElementById("btn_send_id")
const btn_clear_id = document.getElementById("btn_clear_id")
const btn_send_pw = document.getElementById("btn_send_pw")
const btn_clear_pw = document.getElementById("btn_clear_pw")
const btn_login = document.getElementById("btn_login")

function setId(id) {
    text_id.setAttribute('value', id)
}

function setPw(pw) {
    text_pw.setAttribute('value', pw)
}

function login(id, pw) {
    setId(id)
    setPw(pw)
}

btn_send_id.addEventListener('click',function handleClick() {
    var id = text_id.value
    window._BRIDGE_NAME_.receivedId(id)
})

btn_clear_id.addEventListener('click',function handleClick() {
    text_id.value=''
})

btn_send_pw.addEventListener('click',function handleClick() {
    var pw = text_pw.value
    window._BRIDGE_NAME_.receivedPw(pw)
})

btn_clear_pw.addEventListener('click',function handleClick() {
    text_pw.value=''
})

btn_login.addEventListener('click',function handleClick() {
    var id = text_id.value
    var pw = text_pw.value
    window._BRIDGE_NAME_.receivedLogin(id, pw)
})

```

### activity_layout.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:background="#ECECEC"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <LinearLayout
        android:id="@+id/linearLayout"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!-- Input ID layout -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:layout_width="40dp"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="ID"
                android:textSize="20dp" />

            <EditText
                android:id="@+id/etId"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Input ID"
                android:padding="10dp"
                android:textSize="20dp" />
        </LinearLayout>

        <!-- Input PW Layout -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <TextView
                android:layout_width="40dp"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:text="PW"
                android:textSize="20dp" />

            <EditText
                android:id="@+id/etPw"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Input Password"
                android:padding="10dp"
                android:textSize="20dp" />
        </LinearLayout>

        <!-- Buttons Layout -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <Button
                android:id="@+id/btnSendId"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="5dp"
                android:layout_weight="1"
                android:text="SEND ID" />

            <Button
                android:id="@+id/btnSendPw"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="5dp"
                android:layout_weight="1"
                android:text="SEND PW" />

            <Button
                android:id="@+id/btnLogin"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_margin="5dp"
                android:layout_weight="8"
                android:text="Login" />

        </LinearLayout>

    </LinearLayout>

    <WebView
        android:id="@+id/webView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/linearLayout" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initClickListener()
    }

    private fun initClickListener() {
        binding.run {
            btnSendId.setOnClickListener {
                webView.loadUrl("javascript:setId('${etId.text}')")
            }

            btnSendPw.setOnClickListener {
                webView.loadUrl("javascript:setPw('${etPw.text}')")
            }

            btnLogin.setOnClickListener {
                webView.loadUrl("javascript:login('${etId.text}', '${etPw.text}')")
            }
        }
    }

    private fun initView() {
        binding.webView.run {
            loadUrl("file:///android_asset/sample.html")
            setWebContentsDebuggingEnabled(true)
            settings.javaScriptEnabled = true
            addJavascriptInterface(
                MyBridge(
                    receivedId = this@MainActivity::receivedId,
                    receivedPw = this@MainActivity::receivedPw,
                    receivedLogin = this@MainActivity::receivedLogin
                ), MyBridge.BRIDGE_NAME
            )
        }
    }

    private fun receivedId(id: String){
        binding.etId.setText(id)
    }

    private fun receivedPw(pw: String){
        binding.etPw.setText(pw)
    }

    private fun receivedLogin(id: String, pw:String){
        binding.etId.setText(id)
        binding.etPw.setText(pw)
    }
}
```
- `initView()`: 웹뷰에 필요한 설정값을 초기화 한다.
    - `addJavascriptInterface(bridgeObject, bridgeName)`: WebView에 브릿지를 심는다(명시한다).
        - bridgeObject: 브릿지로 동작할 함수들이 정의되어있는 객체
        - bridgeName: 프론트에서 브릿지를 호출할 때, 브릿지 함수임을 구분하기 위한 네이밍

- `webView.loadUrl("javascript:setPw('${etPw.text}')")`
    - `loadUrl()`: 해당 함수를 통해서 웹에 정의되어 있는 Javascript 함수(`setPw(string)`)를 호출할 수 있습니다.
    - JavasCript 함수를 호출하기 위해서는 String으로 `javascript:함수이름('파라미터1', ... )` 넣어주면 된다.
         
