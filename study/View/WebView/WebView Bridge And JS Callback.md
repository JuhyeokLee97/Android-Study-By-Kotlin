# WebView Birdge And JS Callback

## 개요 
### 간단한 브릿지 개념 + 링크
### 간단한 JS Callback 개념 + 링크
### 앱 설명
### 앱 실행영상
<img src="https://user-images.githubusercontent.com/40654227/203548380-e0b68f00-79ca-40ce-bb86-0a2d7d3025a9.gif" height=400/>


## Code
### 프로젝트 구조
<img src="https://user-images.githubusercontent.com/40654227/203549630-7d192154-ab2f-43d0-a385-e5bb383cb1c7.png"/>

### Index
1. [build.gradle(:app): ViewBinding 사용](#buildgradleapp-viewbinding-사용)
2. AndroidManifest.xml: Internet 권한 허용
3. MyBridge.kt: 브릿지 정의
4. sample.html: 웹 화면
5. [index.js](#indexjs)
6. activity_main.xml: 네이티브 + 웹뷰 화면
7. MainActivity.kt

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
