# WebView의 JS 함수 호출

## 개요
안드로이드에서 웹뷰를 구현하다 보면, 웹과 통신이 필요한 경우가 있다.
데이터를 수신받아서 네이티브(안드로이드)에서 동작이 수행되어야 하는 경우에는 <strong>브릿지</strong>를 이용할 수 있다.
반대로 데이터를 송신해야 하는 경우에는 웹에서 구현된 Javascript 함수를 이용할 수 있다.

예를 들어 브릿지를 통해서 네이티브로 다이얼로그를 생성했고, 만약 [동의] 또는 [취소] 버튼을 눌러서 웹에서 동작을 수행해야 할 경우 웹이 ...
### 앱 설명
### 실행화면 
<img src="https://user-images.githubusercontent.com/40654227/202837962-d7b7810d-3002-4b91-8add-29a679e086be.gif" width=300/>

## Code

### build.gradle
``` gradle
android {
    ...
    buildFeatures {
        viewBinding = true
    }
    ...
}

```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
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
        with(binding) {
            btnSendId.setOnClickListener {
                webView.loadUrl("javascript:setId('${etId.text}')")
            }

            btnSendPw.setOnClickListener {
                webView.loadUrl("javascript:setPw('${etPw.text}')")
            }

            btnLogin.setOnClickListener {
                webView.loadUrl("javascript:login('${etId}', '${etPw}')")
            }
        }
    }

    private fun initView() {
        initWebView()
    }

    private fun initWebView() {
        with(binding.webView) {
            /** Web에서 구현된 Javascript 동작 허용 */
            settings.javaScriptEnabled = true
            /** Web 연결 */
            loadUrl("file:///android_asset/sample.html")
        }
    }
}
```

### sample.html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
</head>

<body>
<!--<button id="btn">Click me</button>-->
<p>
    <input type="text" name="text_id" id="text_id" placeholder="ID">
    <button id="btn_send_id">Send ID to Native</button>
</p>

<p>
    <input type="text" name="text_pw" id="text_pw" placeholder="Password">
    <button id="btn_send_pw">Send ID to Native</button>
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

function setId(id) {
    text_id.setAttribute('value', id)

}

function setPw(pw) {
    text_pw.setAttribute('value', pw)

}

function login(id, pw){
    setId(id)
    setPw(pw)
}

```
