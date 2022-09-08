# Webview mailto link Sample

## 개요
Android WebView를 사용할 때, WebView에서 구현되어 있는 **메일**버튼이 기본적으로는 동작하지 않을 것이다.
그 이유는 `WebViewClient`를 구현하는데 있어 `shouldOverrideUrlLoading()`함수를 **Override**하지 않았기 때문이다.

## Code
### MyWebViewClient 클래스
``` kotlin
class MyWebViewClient(private val mContext: Context): WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        if (url.startsWith("mailto:")){
            val intent = Intent(Intent.ACTION_SENDTO, request.url)
            mContext.startActivity(intent)
            
            return true
        }
        return false
    }
}
```
위와 같이 `WebViewClient()`를 상속받아서 `shouldOverrideUrlLoading`을 **Override**해서 구현하고, 
사용하는 webView.webViewClient에 셋팅해주면 해당 webView에서는 **메일**버튼이 정상 동작하게 된다.
