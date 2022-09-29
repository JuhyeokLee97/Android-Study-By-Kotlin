# WebView Bridge

## WebView 란
WebView(웹뷰)란 프레임워크에 내장된 웹 브라우저 컴포넌트로 뷰(View)의 형태로 앱에 임베딩하는 것을 말한다. 즉, WebView는 앱 내에 웹 브라우저를 넣는 것이다. 웹 페이지를 보기 위해서 혹은 앱 안에서 HTML을 호출하여 앱을 구현하는 하이브리드 형태의 애을 개발하는데에도 많이 사용된다.

자세한 내용은 다음을 참고.
[WebView 란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/WebView/WebView%20%EB%9E%80.md)



## WebView Bridge 란

Bridge(브릿지)란 Android와 WebView의 통신을 위해 만들어진 JavaScript용 Interface이다. Web에서 발생하는 이벤트에서 Android 동작(메서드)을 직접적으로 통제할 수 없기 때문에 **Bridge**라는 통로를 통해 Web에서 Android 동작을 호출한다. **Bridge**는 WebView에 적용될 Interface 구현체이고, WebView는 객체의 메서드들을 인스턴스를 통해 호출할 수 있다.

## Basic Sample
### 개요
### 앱 설명
간단하게 Bridge를 구현하여 Web에 있는 버튼을 클릭하면 Android에서는 Toast 메시지를 노출되도록 한다.

#### 실행화면
<img src="https://user-images.githubusercontent.com/40654227/192780407-497f6e1a-c2a1-42d2-b9fa-230a42553bef.gif" height=550/>

### Code

#### build.gradle(:Module): ViewBinding 사용을 위한 
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
            addJavascriptInterface(WebAppInterfaceImpl(), "DevGeek")
            loadUrl("file:///android_asset/sample.html")
        }
    }

    inner class WebAppInterfaceImpl{
        /** 웹 페이지에서 `toast` 버튼을 눌렀을 때 동작하는 함수 */
        @JavascriptInterface
        fun showToast(message: String){
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
    
}
```

- `WebAppInterface Class`: WebView에 연결시켜줄 JavaScriptInterface 구현체를 정의한다. Bridge를 통해 실행될 함수는 클래스 안에서 정의한다.
  - @JavaScriptInterface: 브릿지함수를 명시하는 주석(어노테이션)
- `addJavascriptInterface(WebAppInterfaceImpl(), "DevGeek")`
  - "DevGeek": Web에서 브릿지를 구분할 수 있도록 **브릿지명**을 지정해준다.

#### asset/sample.html: 임시 Web 페이지 만들기
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

#### asset 디렉토리 만들기

아래 그림과 같이 `Project > app > New > Folder > Assets Folder` 를 통해서 디렉토리를 만든다.

<img height=500 src="https://user-images.githubusercontent.com/40654227/193006901-cccce46b-e022-41fc-8cc0-1742debc94f3.png" />
