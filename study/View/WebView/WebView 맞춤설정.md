# WebView 맞춤 설정

## [WebChromeClient](https://developer.android.com/reference/android/webkit/WebChromeClient?hl=ko)
<p>
  
  <strong>WebCrhomeClient</strong>는 웹 페이지에서 일어나는 액션들에 대한 <strong>콜백함수</strong>들로 구성되어 있다. 예를 들면 웹에서 새 창을 띄우려거나 파일을 첨부하는 경우가 있다.

 
</p>

``` kotlin
webView.webViewClient = WebViewClient()
```

 - `public void onProgressChanged(WebView view, int newProgress)`
 - `public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)`
 
- [`onShowFileChooser()`: 웹뷰에서 이미지 업로드 callback]()


## [WebViewClient](https://developer.android.com/reference/android/webkit/WebViewClient?hl=ko)
<p>
  <strong>WebViewClient</strong>를 사용한 탐색 오류 또는 양식 제출 오류 등 콘텐츠 렌더링에 영향을 미치는 이벤트 처리. 
  이 서브클래스를 사용하여 URL 로드를 가로챌 수도 있습니다.
 
</p>

``` kotlin
webView.webChromeClient = WebChromeClient()
```

## [WebSettings](https://developer.android.com/reference/android/webkit/WebSettings?hl=ko)
WebSettings를 수정하여 자바스크립트 사용 설정.

``` kotlin
webView.settings.javaScriptEnabled = true
```
