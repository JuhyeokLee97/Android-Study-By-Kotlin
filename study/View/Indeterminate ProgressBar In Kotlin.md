
# Android ProgressBar in Kotlin

<p>
<strong>ProgressBar</strong>는 사용자에게 작업 진행률을 나타내는 사용자 인터페이스 컨트롤이다. 예를 들면 인터넷에서 파일을 다운로드하거나 업로드 할 때, 우리는 <strong>ProgressBar</strong>을 통해서 작업 진행 상황을 파악할 수 있다.


</p>

<p>

<Strong>ProgressBar</strong>에는 2가지 모드가 있다.
- Determinate ProgressBar
- <strong>Indeterminate ProgressBar</strong>

## Indeterminate ProgressBar

<p align="center">
 <img src="https://user-images.githubusercontent.com/40654227/174316120-a35538f9-e431-4647-a4c1-90c27f838571.png" width=250/>
</p>

<p>

<strong>Indeterminate ProgressBar</strong>는 아쉽게도 작업의 진행률을 확인할 수는 없다. 그래서 대게 진행정도를 파악할 수 없을 때 사용한다. 예를들면 API 통신을 통해 데이터를 읽어 올 때, 즉 서버에서 데이터를 받는 정도를 파악할 수 없는 경우 사용한다.

</p>

## Indeterminate ProgressBar 구현하기

### 개요

<p>
  
  Indeterminate ProgressBar를 사용하는 간단한 앱을 만들어보려고 한다.</br>
  이 앱은 일정시간동안 ProgressBar가 동작하고 사라진다. 마치 앱이 로딩 작업을 진행하고 그 이후에 데이터를 보여주는 것처럼 동작하도록 했다.
  
</p>

#### 실행화면

<p align="center">

<img src="https://user-images.githubusercontent.com/40654227/174484431-b73a0fd7-c40f-4ba2-b312-1ddc7dcf5217.gif" height=400/>


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
  
  ProgressBar 사용을 위해 해당 XML파일에 View를 추가한다.
  
</p>

``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textViewIsLoading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Loading..."
        android:textColor="@color/black"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="@id/progressBar"
        app:layout_constraintStart_toStartOf="@id/progressBar"
        app:layout_constraintTop_toBottomOf="@id/progressBar" />
</androidx.constraintlayout.widget.ConstraintLayout>

```

### MainActivity.kt

``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delayMillis = 5000L
        Handler(Looper.myLooper()!!).postDelayed({
            /** `delayMillis` 이후에 수행할 동작 */
            binding.progressBar.isVisible = false
            binding.textViewIsLoading.text = "DONE"
        }, delayMillis)
    }


}
```
