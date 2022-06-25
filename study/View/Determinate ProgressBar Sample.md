# Determinate ProgressBar Sample

  > [Android ProgressBar 란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/ProgressBar%20in%20Kotlin.md)</br>
  > [Android Indeterminate ProgressBar 구현 예제](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/View/Indeterminate%20ProgressBar%20In%20Kotlin.md)
# Android ProgressBar

<p>
<strong>ProgressBar</strong>는 사용자에게 작업 진행률을 나타내는 사용자 인터페이스 컨트롤이다. 예를 들면 인터넷에서 파일을 다운로드하거나 업로드 할 때, 우리는 <strong>ProgressBar</strong>을 통해서 작업 진행 상황을 파악할 수 있다.

</p>

<p>

<Strong>ProgressBar</strong>에는 2가지 모드가 있다.
- **Determinate ProgressBar**
- Indeterminate ProgressBar

</p>

## Determinate ProgressBar

<p align="center">
 <img src="https://user-images.githubusercontent.com/40654227/174306867-48ad270b-fc73-40eb-8ffa-462248049abe.png" width=250/>
</p>

<p>

일반적으로, 우리는  **Determinate progress**  모드를 사용한다. 왜냐하면 이 progressbar는 작업의 진행정도를 퍼센티지(%)와 같이 나타내기 때문이다.

</p>

## Determinate ProgressBar 구현하기

### 개요

<p>
  
  Determinate ProgressBar를 사용하는 간단한 앱을 만들어보려고 한다.</br>
  이 앱은 **Determinate ProgressBar**를 사용하여 `Download` 버튼과 `Upload` 버튼을 클릭 하면, 다운로드(업로드)가 진행되는 상태를 임의로 나타낸다.
  
</p>

#### 실행화면

<p align="center">

<img src="https://user-images.githubusercontent.com/40654227/175757385-1ff61c64-c611-4445-8917-f26cadba08a6.gif" height=400/>


</p>

### Build.gradle(:Module): ViewBinding 추가

<p>
  
  ViewBindg 사용을 위해 `build.gradle(:Module)`파일에서 `android { }` 태그의 속성 값으로 아래와 같이 `ViewBinding`을 허용해준다.
  
</p>

``` kotlin

android {
    ...
    
    viewBinding{
      enabled = true
    }  
    
    ...
}
```

### activity_main.xml: ProgressBar View를 생성한다.

<p>
  
  - ProgressBar 사용을 위해 해당 XML파일에 View를 추가한다.
  - ProgressBar 진행 정도를 나태내기 위해 TextView를 추가한다.
  - ProgressBar 동작 수행 트리거를 위한 Download(Upload) Button을 추가한다.
  
</p>

``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingVertical="100dp"
    tools:context=".MainActivity">

    <LinearLayout
        android:id="@+id/downloadLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="20dp"
        android:orientation="horizontal"
        android:visibility="invisible"
        tools:visibility="visible">

        <ProgressBar
            android:id="@+id/progressDownload"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_weight="4"
            android:max="100"
            tools:progress="60" />

        <TextView
            android:id="@+id/textViewDownload"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_weight="1"
            android:gravity="center"
            android:text="0"
            android:textColor="@color/black"
            android:textSize="20sp"
            tools:text="100" />
    </LinearLayout>

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnDownload"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="40dp"
        android:layout_marginVertical="20dp"
        android:text="DOWNLOAD" />

    <LinearLayout
        android:id="@+id/uploadLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="20dp"
        android:layout_marginTop="100dp"
        android:orientation="horizontal"
        android:visibility="invisible"
        tools:visibility="visible">

        <ProgressBar
            android:id="@+id/progressUpload"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_weight="4"
            android:max="100"
            tools:progress="40" />

        <TextView
            android:id="@+id/textViewUpload"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_weight="1"
            android:gravity="center"
            android:textColor="@color/black"
            android:textSize="20sp"
            tools:text="100" />
    </LinearLayout>

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnUpload"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="40dp"
        android:layout_marginVertical="20dp"
        android:text="UPLOAD" />


</LinearLayout>
```

### MainActivity.kt: 로직 구현

<p>

``` kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devgeek.determinate_porgressbar_sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Download Button Click Listener */
        binding.apply {
            btnDownload.setOnClickListener {
                btnDownload.isClickable = false
                btnDownload.text = "DOWNLOADING..."
                downloadLayout.visibility = View.VISIBLE

                var progress = 0
                Thread(Runnable {
                    while (progress < 100) {
                        progress += 1

                        /** Update UI */
                        runOnUiThread {
                            progressDownload.progress = progress
                            textViewDownload.text = progress.toString()
                        }
                        Thread.sleep(50)
                    }

                    /** Done Download */
                    btnDownload.isClickable = true
                    btnDownload.text = "DOWNLOAD"
                    downloadLayout.visibility = View.INVISIBLE
                }).start()
            }
        }

        /** Upload Button Click Listener */
        binding.apply {
            btnUpload.setOnClickListener {
                btnUpload.isClickable = false
                btnUpload.text = "UPLOADING..."
                uploadLayout.visibility = View.VISIBLE

                var progress = 0
                Thread(Runnable {
                    while (progress < 100) {
                        progress += 1

                        /** Update UI */
                        runOnUiThread {
                            progressUpload.progress = progress
                            textViewUpload.text = progress.toString()
                        }
                        Thread.sleep(50)
                    }

                    /** Done Uploading */
                    btnUpload.isClickable = true
                    btnUpload.text = "UPLOAD"
                    uploadLayout.visibility = View.INVISIBLE
                }).start()
            }
        }
    }
}
```


</p>
