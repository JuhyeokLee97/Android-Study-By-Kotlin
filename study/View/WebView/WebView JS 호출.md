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
//            webview.loadUrl("file:///android_asset/sample.html")
            webview.loadUrl("file:///android_asset/sample2.html")

            button.setOnClickListener {
//                webview.loadUrl("javascript:foo()")
                webview.loadUrl("javascript:test_foo('token-123', 'my id is', 'zeor@gmail.com')")

            }
            textview.setOnClickListener{
                webview.loadUrl("javascript:foo3('p1')")
            }

        }

    }

}
```

### html
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
</head>

<body>
<button id="btn">Click me</button>
<p>
    Token
    <button id="token">TOKEN</button>
</p>

<p>
    User ID
    <button id="user_id">USER ID</button>
</p>

<p>
    EMAIL
    <button id="email">EMAIL</button>
</p>


<script src="index.js"></script>
</body>
</html>
```

### js
``` javascript
const btn = document.getElementById('btn');
const token = document.getElementById('token');
const user_id = document.getElementById('user_id');
const email = document.getElementById('email');

// ✅ Change button text on click
btn.addEventListener('click', function handleClick() {
  btn.textContent = 'Button clicked';
});

function foo(){
  btn.textContent = 'Button clicked';
}

function foo2(){
  btn.textContent = 'Click me';
}

function foo3(p1){
  btn.textContent = 'Button clicked';
}

function test_foo(_token, _user_id, _email){
     token.textContent = _token;
     user_id.textContent = 'Button clicked';
     email.textContent = 'Button clicked';

}
```
