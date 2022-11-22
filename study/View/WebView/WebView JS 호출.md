# WebView의 JS 함수 호출

## 개요
안드로이드에서 웹뷰를 구현하다 보면, 웹과 통신이 필요한 경우가 있다.
데이터를 수신받아서 네이티브(안드로이드)에서 동작이 수행되어야 하는 경우에는 <strong>브릿지</strong>를 이용할 수 있다.
반대로 데이터를 송신해야 하는 경우에는 웹에서 구현된 Javascript 함수를 이용할 수 있다.

예를 들어 브릿지를 통해서 네이티브로 다이얼로그를 생성했고, 만약 [동의] 또는 [취소] 버튼을 눌러서 웹에서 동작을 수행해야 할 경우 앱에서는 웹에게 값을 전달해야할 것이다.
이때 콜백 함수처럼 웹에서 구현된 Javascript 함수를 네이티브로 앱에서 호출 할 수 있다.

### webView.loadUrl("javascript:function_name(params)")
안드로이드에서 웹뷰에 연결된 웹의 Javascript 함수를 호출하기 위해서는 `webView.loadUrl("javascript:함수이름(파라미터)")` 형태로 `loadUrl` 함수를 통해 Javascript 함수를 담아 호출할 수 있다.


### 앱 설명

상단에 있는 네이티브 영역에서 입력한 `ID`, `PW`를 각각의 버튼을 통해서 웹으로 전달한다.
- `SEND ID`: 버튼을 클릭한 경우, 웹에 `ID` 값을 전달한다. 웹에서는 전달 받은 값을 ID 텍스트 박스에 보여준다.
- `SEND PW`: 버튼을 클릭한 경우, 웹에 `PW` 값을 전달한다. 웹에서는 전달 받은 값을 Password 텍스트 박스에 보여준다.
- `LOGIN`: 버튼을 클릭한 경우, 웹에 `ID`, `PW` 값을 모두 전달한다. 웹에서는 전달 받은 값을 ID, PW 텍스트 박스에 보여준다.


### 실행화면 
<img src="https://user-images.githubusercontent.com/40654227/202837962-d7b7810d-3002-4b91-8add-29a679e086be.gif" width=300/>

## Code
### 프로젝트 구조
<img width="254" alt="프로젝트 구조" src="https://user-images.githubusercontent.com/40654227/203304711-f04fd4b4-4029-4d54-bcdc-e72da1ddeca3.png">

### build.gradle: ViewBinding 설정
`ViewBinding`을 사용하기 위해서 `build.gradle`에서 아래와 같이 설정해준다.
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
#### Description...
``` kotlin
btnSendId.setOnClickListener {
    webView.loadUrl("javascript:setId('${etId.text}')")
}
```
`SEND ID` 버튼을 클릭한 경우, 웹뷰에서 정의되어 있는 `setId()` 함수를 호출한다.

``` kotlin
btnSendPw.setOnClickListener {
    webView.loadUrl("javascript:setPw('${etPw.text}')")
}
            
```
`SEND PW` 버튼을 클릭한 경우, 웹뷰에서 정의되어 있는 `setPw()` 함수를 호출한다.

``` kotlin
btnLogin.setOnClickListener {
    webView.loadUrl("javascript:login('${etId}', '${etPw}')")
}
```
`LOGIN` 버튼을 클릭한 경우, 웹뷰에서 정의되어 있는 `login()` 함수를 호출한다.

### sample.html
Android Studio에서 `assets/sample.html` 파일을 생성하기 위해서는 [여기](https://devgeek.tistory.com/92)를 참고하면 된다.
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
</head>

<body>

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
Android Studio에서 `assets/index.js` 파일을 생성하기 위해서는 [여기](https://devgeek.tistory.com/92)를 참고하면 된다.
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
