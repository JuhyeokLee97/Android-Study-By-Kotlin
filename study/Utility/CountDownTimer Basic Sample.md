# CountDownTimer란

## 샘플 앱 개요
### 개요
### 실행영상
<img src="https://user-images.githubusercontent.com/40654227/188886108-ba3fcc9a-c7d3-4454-a5af-8f4a5f711dd8.gif" height=600/>


### Code

#### build.gradle(:Module): ViewBinding 설정
``` kotlin

android {
    ...
    buildFeatures{
        viewBinding true
    }
}
```

#### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#e0e0e0"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvCountDown"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="지문 확인까지 5초 남았습니다."
        android:textColor="@color/black"
        android:textSize="16sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnRequestFingerPrint"
        android:layout_width="230dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:text="지문 입력 요청하기"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvCountDown" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### MainActivity.kt
``` kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.devgeek.countdowntimersample.databinding.ActivityMainBinding

const val MILLIS_IN_FUTURE = 5000L
const val COUNT_DOWN_INTERVAL = 1000L
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var countDownTimer: CountDownTimer? = null
    private var limitSec = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCountDownTimer()
        binding.btnRequestFingerPrint.setOnClickListener { initCountDownTimer() }
    }

    private fun initCountDownTimer(){
        if (countDownTimer != null){
            try {
                countDownTimer!!.cancel()
            }catch (e: Exception){
                e.printStackTrace()
            }
            countDownTimer = null
            limitSec = 5
        }

        countDownTimer = object: CountDownTimer(MILLIS_IN_FUTURE, COUNT_DOWN_INTERVAL){
            override fun onTick(millisUntilFinished: Long) {
                val text = "${limitSec}초 남았습니다."
                binding.tvCountDown.text = text
                limitSec--
            }

            override fun onFinish() {
                binding.tvCountDown.text = "시간이 지났습니다.\n다시 지문 입력 요청해주세요."
            }

        }.start()
    }
}
```
