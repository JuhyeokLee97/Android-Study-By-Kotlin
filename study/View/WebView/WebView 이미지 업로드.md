# WebView 이미지 업로드
### To do
- [x] activityStartForResult 변경
- [x] 권한 확인 팝업을 dismiss 한 경우 처리
- [x] 권한 Granted 확인 함수 작성
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
### AndroidManifest.xml: 권한 설정 추가
``` xml
<manifest ...>

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.INTERNET"/>

    <application... >
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    /** 파일 업로드를 위해 갤러리 접근 하여 사진 선택 후 동작([startActivityForeResult] 대체) */
    private val chooseFileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.run {
                chooseFileUriParseToSend(resultCode, data, filePathCallback)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.webView.run {
            settings.javaScriptEnabled = true
            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@MainActivity.filePathCallback = filePathCallback
                    showGalleryPick()
                    return true
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (isGrantedPermissions(grantResults)) {
                showGalleryPick()
            } else {
                // null 처리를 하지 않으면 담부터 input 태그를 눌러도 반응이 없다
                filePathCallback?.onReceiveValue(null)
            }
        }
    }

    /** 권한 허용을 확인하는 함수 */
    private fun isGrantedPermissions(grantResults: IntArray): Boolean {
        if (grantResults.isEmpty()) {
            return false
        }
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /** 권한 허용 상태를 확인하는 함수 */
    private fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 이미지 픽커 호출하는 함수.
     *
     * 마시멜로 버전 이상을 사용하는 앱부터는 접근 권한을 확인하고 허용해야만 사용할 수 있으므로
     * 버전에 따라 미디어 접근 권한 승인 확인 후 이미지 픽거킄 호출한다.을
     * 권한이 거절된 경우에는 권한을 요청한다.
     */
    private fun showGalleryPick() {
        val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        // 마시멜로 버전 이상 && 권한 허용 X
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !checkPermissions(this, permissions)
        ) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        } else {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            // startActivityForResult 대체
            chooseFileLauncher.launch(intent)
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

        // null 처리를 하지 않으면 담부터 input 태그를 눌러도 반응이 없다
        filePathCallback.onReceiveValue(null)
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 2001
    }
}
```
`onActivityResult()`에서 이미지를 선택한 후, 이미지 데이터를 담아
`chooseFileUriParseToSend()` 함수를 호출해서 선택한 이미지들을 `filePathCallback.onReceivedValue()`를 통해 웹에 데이터를 전송할 수 있다.
