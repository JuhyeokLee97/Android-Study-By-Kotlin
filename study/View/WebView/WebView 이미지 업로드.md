# WebView 이미지 업로드

## 개요 - WebChromeClient
<strong>WebCrhomeClient</strong> 이란...

### WebChromeClient.onShowFileChooser()
 - params
    - `webView`:
    - `filePathCallback`:
    - `fileChooserParams`:
 - 정의(동작방식)
 
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

- `checkPermission()`: 갤러리 접근 권한 요청
- `showGalleryPick()`: 갤러리 접근 함수
- `onActivityResult()`: 이미지 선택 후 동작
- `chooseFileUriParseToSend()`: ...
