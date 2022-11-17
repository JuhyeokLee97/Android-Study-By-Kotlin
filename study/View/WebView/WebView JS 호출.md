## WebView의 JS 함수 호출

## Code

### MainActivity.kt
``` kotlin
lass MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.run {
            webview.settings.javaScriptEnabled = true
            webview.addJavascriptInterface(WebAppInterface(), "BlackJin")
            webview.loadUrl("file:///android_asset/sample.html")

            button.setOnClickListener {
//                webview.loadUrl("javascript:plus_num(" + edittext.text + ")")
                webview.loadUrl("javascript:alert(안녕)")
            }
        }

    }


    inner class WebAppInterface {
        @JavascriptInterface
        fun getDoubleNum(num: Int) {
            binding.textview.text = num.toString()
        }

        @JavascriptInterface
        fun getResetNum() {
            binding.textview.text = "0"
        }
    }
}
```

### html
```html
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
</head>
<body>
화면에 입력한 숫자의 곱하기 2의 값이 화면에 출력됩니다.
<input type="button" value="Send Toast Message" onClick="plus_num(2)"/>

<script type="text/javascript" src="exam_script.js"></script>
</body>
</html>
```

### js
``` javascript
var test_script = {
    function plus_num(num) {
        var result = num * 2
        BlackJin.getDoubleNum(result)
    }
    plus_num: function(num){
        try{
            var result = num * 2
            BlackJin.getDoubleNum(result)
        }catch(err){
            console.log(">> [exam_script.plus_num()] " + err)
        }
    }
```
