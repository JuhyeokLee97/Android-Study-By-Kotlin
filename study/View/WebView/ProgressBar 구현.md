# WebView ProgressBar 구현

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
