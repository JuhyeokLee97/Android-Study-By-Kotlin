# WebView 이미지 업로드
### To do
- [ ] activityStartForResult 변경
- [ ] 권한 확인 팝업을 dismiss 한 경우 처리
- [ ] 권한 Granted 확인 함수 작성
- [ ] Web-Html 작성
- [ ] 실행화면

## 개요 - WebChromeClient

<strong>WebCrhomeClient</strong>는 웹 페이지에서 일어나는 액션들에 대한 콜백함수들로 구성되어 있다. 예를 들면 웹에서 새 창을 띄우려거나 파일을 첨부하는 경우가 있다.

``` kotlin
webView.webViewClient = WebViewClient()
```

## WebchromeClient.onShowFileChooser()
``` kotlin
webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                this@MainActivity.filePathCallback = filePathCallback
                navigateGallery(this@MainActivity)
                return true
            }
        }
```

파일 첨부와 같은 액션에 반응하기 위해서는 웹에서의 `<input>` 파일 첨부 태그에 반응하는 함수 `onShowFileChooser()`를 재정의해야한다.
- `onShowFileChooser()` 이란
    - HTML에서 'file' 형태를 갖는 `<input>` 태그에 대한 액션에 반응하는 함수이다.
    - 파일 선택을 취소하기 위해서는 `filePathCallback.onReceiveValue(null)`를 호출해야한다.
    - 반환값은 `true` 를 반환해야한다.   
    
- 파라미터
    - `webView: WebView`: 해당 웹뷰 객체를 의미한다.
    - `fildPathCallback: ValueCallback<Array<Uri>>?`: 업로드 하려는 파일들의 path 리스트를 받아 콜백함수(`filePathCallback.onReceiveValue(value)`를 호출할 수 있다.(단, 업로드를 취소할 경우 `null`을 담아서 콜백함수를 호출해야한다. 그렇지 않으면 이후에 `<input>` 태그의 액션이 반응하지 않는다.
    - `fileChooserParam`: `<input>` 액션을 통해서 선택할 파일의 유형을 명시한다.


### 앱 실행화면

## Code

### 프로젝트 구조

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.run{
            settings.javaScriptEnabled = true
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@MainActivity.filePathCallback = filePathCallback
                    checkPermission(this@MainActivity)
                    return true
                }
            }
        }
    }

    private fun checkPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE

            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                showGalleryPick()
            } else {
                requestPermissions(arrayOf(permission), PERMISSION_REQUEST_CODE)
            }
        } else {
            showGalleryPick()
        }
    }

    private fun showGalleryPick() {
        Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }.let {
            (this as? Activity)?.startActivityForResult(it, FILE_CHOOSER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            FILE_CHOOSER_REQUEST_CODE -> {
                chooseFileUriParseToSend(resultCode, data, filePathCallback)
            }
            else -> {

            }
        }
    }

    private fun chooseFileUriParseToSend(
        resultCode: Int,
        data: Intent?,
        filePathCallback: ValueCallback<Array<Uri>>?
    ) {
        filePathCallback ?: return
        data ?: return filePathCallback.onReceiveValue(null)

        if (resultCode != Activity.RESULT_OK) {
            return filePathCallback.onReceiveValue(null)
        }

        if (data.clipData != null) {
            val clipData = data.clipData!!
            val itemCount = clipData.itemCount

            if (itemCount > 0) {
                val uriList = mutableListOf<Uri>()

                for (i in 0 until itemCount) {
                    clipData.getItemAt(i).uri.run {
                        uriList.add(this)
                    }
                }
                if (uriList.size > 0) {
                    filePathCallback.onReceiveValue(uriList.toTypedArray())
                    return
                }
            }
        } else if (data.data != null) {
            filePathCallback.onReceiveValue(
                WebChromeClient.FileChooserParams.parseResult(
                    resultCode,
                    data
                )
            )
            return
        }

        // null 처리를 하지 않으면 담부터 input 태그를 눌러도 반응이 없음.
        filePathCallback.onReceiveValue(null)
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 2001
        const val FILE_CHOOSER_REQUEST_CODE = 2002
    }
}
```
`onActivityResult()`에서 이미지를 선택한 후, 이미지 데이터를 담아
`chooseFileUriParseToSend()` 함수를 호출해서 선택한 이미지들을 `filePathCallback.onReceivedValue()`를 통해 웹에 데이터를 전송할 수 있다.
