# WebView ProgressBar 구현

## 개요
### ProgressBar
ProgressBar는 사용자에게 작업 진행률을 나타내는 사용자 인터페이스 컨트롤이다.</br>
예를 들면 인터넷에서 파일을 다운로드하거나 업로드 할 때, 우리는 ProgressBar을 통해서 작업 진행 상황을 파악할 수 있다.</br>
ProgressBar에 대한 자세한 내용은 다음을 참고하면 도움이 될 것이다.
- [ProgressBar 란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/ProgressBar%20in%20Kotlin.md)
- [Determinate ProgressBar Sample](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/Determinate%20ProgressBar%20Sample.md)
- [Indeterminate ProgressBar In Kotlin](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/Indeterminate%20ProgressBar%20In%20Kotlin.md)

### 앱 설명
이 앱은 안드로이드에서 제공하는 WebView를 통해 페이지를 로드할 때, ProgressBar를 통해 사용자에게 실행중임을 나타낸다.</br>
여기서는 2가지 ProgressBar를 사용한다.</br>
- 진행중임을 나타내는 원형 ProgressBar (중앙)
- 진행률을 나타내는 막대 ProgressBar (상단)

앱은 간단하게 { 웹뷰, 버튼1, 버튼2 }로 구성되어 있다.</br>
 - 웹뷰는 기본적으로 블로그를 로드한다.
 - 버튼1("GO TO HOME")는 블로그 페이지를 로드한다.
 - 버튼2("GO TO GOOGLE")는 구글 홈페이지를 로드한다.
 
 버튼 클릭을 통해 페이지를 로드할 때, 각각의 프로그레스를 사용자에게 노출한다.

### 실행 영상
 <img src="https://user-images.githubusercontent.com/40654227/209145105-7a11c1a1-9036-43ff-884a-9d3eaac41bd4.gif" width=400/>



## Code

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ProgressBar
        android:id="@+id/progressHorizontal"
        style="@style/Widget.AppCompat.ProgressBar.Horizontal"
        android:layout_width="0dp"
        android:layout_height="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <WebView
        android:id="@+id/webView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/progressHorizontal" />

    <ProgressBar
        android:id="@+id/progress_circular"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnGoToHome"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="GO TO HOME"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@id/btnGoToGoogle"
        app:layout_constraintStart_toStartOf="parent" />

    <Button
        android:id="@+id/btnGoToGoogle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="GO TO GOOGLE"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/btnGoToHome" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initWebView()
        binding.btnGoToGoogle.setOnClickListener {
            loadGoogle()
        }

        binding.btnGoToHome.setOnClickListener {
            loadHome()
        }
    }

    private fun initWebView() {
        binding.webView.run {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    showProgress()
                    return super.shouldOverrideUrlLoading(view, url)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    hideProgress()
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    setProgressValue(newProgress)
                }
            }

            loadUrl("https://devgeek.tistory.com/")
        }
    }

    private fun showProgress() {
        binding.run {
            progressCircular.isVisible = true
            progressHorizontal.isVisible = true
            progressHorizontal.progress = 0
        }
    }

    private fun hideProgress() {
        binding.run {
            progressCircular.isVisible = false
            progressHorizontal.isVisible = false
        }
    }

    private fun setProgressValue(progress: Int) {
        binding.progressHorizontal.progress = progress
    }

    private fun loadGoogle() {
        binding.webView.loadUrl("https://google.com")
    }

    private fun loadHome() {
        binding.webView.loadUrl("https://devgeek.tistory.com/")
    }
}
```
