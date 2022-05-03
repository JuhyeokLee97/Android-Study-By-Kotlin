# WebView
## 정의
웹뷰(WebView)란 프레임워크에 내장된 웹 브라우저 컴포넌트로 뷰(View)의 형태로 앱에 임베딩하는 것을 말한다.
즉, WebView는 앱 내에 웹 브라우저를 넣는 것이다.
웹 페이지를 보기 위해서 혹은 앱 안에서 HTML을 호출하여 앱을 구현하는 하이브리드 형태의 애을 개발하는데에도 많이 사용된다.

### 하이브리드 앱

#### 👍 장점
하이브리드 앱은 안드로이드 네이티브 앱 개발에 비해서 개발이 비교적 쉽다.
특히 기기간의 호환성을 해결하기가 상대적으로 편하다.
타 웹 사이트 링크로 가는 기능등을 지원하기 위해서 많이 사용된다.

#### 👎 단점
HTML 기반인 만큼 상대적으로 반응성이 약하고, 애니메이션등의 다양한 UI 효과를 넣기 어렵다.
OS에 맞게 일부 기능들을 제외하고 작게 만든 웹 브라우저로 HTML5 호환성 등 기능의 제약을 많이 가지고 있다.

## 개발 참고 사항

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
