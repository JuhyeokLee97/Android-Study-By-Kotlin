# Spannable Click Event Example

## 개요
**Spannable**과 `ClickableSpan`을 이용하여 텍스트 뷰의 특정 문구를 눌렀을 때, 심어둔 링크를 바탕으로 웹페이지를 열도록 한다.
여기서는 "안드로이드 개발을 공부하는 신입 개발자입니다. 궁금하시다면 여기로 오시면 제 블로그를 방문하실 수 있습니다." 문장에서 `여기`에 블로그 링크를 심어서 동작하도록 해보겠다.

### 앱 실행 화면
![Spannable-Click-Event](https://user-images.githubusercontent.com/40654227/192087178-0a4eb208-e413-45f5-8c94-9ec7f657bcc4.gif)

## Code
### build.gradle(Module): ViewBinding 사용
``` kotlin
android {
    ...
    buildFeatures{
        viewBinding = true
    }
    ...
}
```

### activity_main.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="안드로이드 개발을 공부하는 신입 개발자입니다. 궁금하시다면 여기로 오시면 제 블로그를 방문하실 수 있습니다."
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.core.text.toSpannable
import com.example.testspannableapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = binding.textView.text
        val targetStr = "여기"
        val startIdx = text.indexOf(targetStr)
        val endIdx = startIdx + targetStr.length
        // Span에 들어갈 clickableSpan 객체 생성
        val mClickableSpan = object: ClickableSpan(){
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = this@MainActivity.getColor(R.color.blue)
                textPaint.isUnderlineText = true
            }

            override fun onClick(v: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                startActivity(browserIntent)
            }
        }

        val spannable = text.toSpannable().apply{
            // 폰트 설정
            setSpan(StyleSpan(Typeface.BOLD), startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 클릭 이벤트 설정
            setSpan(mClickableSpan, startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.textView.apply{
            this.text = spannable
            this.movementMethod = LinkMovementMethod()
        }

    }
}

```

- `mClickableSpan`: **ClickableSpan**은 추상클래스이기 때문에 추상함수인 `onClick`을 정의한 객체를 만들어 사용해야한다. 
  - `onClick(v: View)`: `targetStr`을 클릭했을 때, 동작될 기능을 구현한다.
  - `updateDrawState(textPaint: TextPaint)`: `targetStr`에 추가할 스타일을 정의한다.

- `spannable`: `text`를 spannable 타입으로 변경하여 추가할 span들을 설정한 값(Spannable)을 저장한다.

- `this.movementMethod = LinkeMovementMethod()`: TextView에 클릭 이벤트가 허용될 수 있도록 설정한다.

