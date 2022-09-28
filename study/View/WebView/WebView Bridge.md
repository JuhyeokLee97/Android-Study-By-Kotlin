# WebView Bridge

## WebView 란

## WebView Bridge 란

## Basic Sample

### 개요 

#### 실행화면
<img src="https://user-images.githubusercontent.com/40654227/192780407-497f6e1a-c2a1-42d2-b9fa-230a42553bef.gif" height=550/>


### Code

#### build.gradle(:Module)
``` kotlin
android {
    ...
    buildFeatures{
        viewBinding = true
    }
    ...
}
```
#### activity_main.xml: WebView 그리기
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <WebView
        android:id="@+id/webView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
#### MainActivity.kt: WebView 셋팅, JavaScriptInterface Class 구현
``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.run{
            /** 웹에서의 JavaScript 기능을 사용할 수 있도록 활성화한다. */
            settings.javaScriptEnabled = true

            /** `DevGeek` 이름으로 JavascriptInterface를 추가한다. */
            addJavascriptInterface(WebAppInterface(), "DevGeek")
            loadUrl("file:///android_asset/sample.html")
        }
    }

    inner class WebAppInterface{
        /** 웹 페이지에서 `toast` 버튼을 눌렀을 때 동작하는 함수 */
        @JavascriptInterface
        fun showToast(message: String){
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
    
}
```

#### asset/sample.html
``` html
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
</head>
<body>
<p>
    로딩이 완료된 웹뷰 입니다.
</p>
<input type="button" value="Send Toast Message" onClick="showAndroidToast('Hello Black Jin World!')"/>
<script type="text/javascript">
    function showAndroidToast(toast) {
        DevGeek.showToast(toast);
    }

</script>
</body>
</html>
```

[WebView와 브릿지를 사용해 통신하는 방법 한 번에 정리하기](https://kotlinworld.com/364)</br>
[웹뷰와 앱간의 통신 예제](https://black-jin0427.tistory.com/272)
