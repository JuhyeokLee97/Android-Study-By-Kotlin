## 실행화면

![](https://user-images.githubusercontent.com/89065117/146310305-06015e54-ba26-4502-8e7d-eefb7d47e11b.png)![](https://user-images.githubusercontent.com/89065117/146310389-ce76f8da-abe2-41d8-9638-f12de898bf68.png)

## 프로젝트 구조

![](https://user-images.githubusercontent.com/89065117/146310504-9e0653db-0525-4766-bd1c-6c4ce18832bd.png)

## Code - Example

### activity\_main.xml

**공유하기 버튼** 을 만든다.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnShare"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="#BBCCDD"
        android:text="공유하기"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

공유하기 기능을 구현한다.

```
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initShareButton()
    }

    private fun initShareButton() {
        val shareButton = findViewById<AppCompatButton>(R.id.btnShare)
        shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://devgeek.tistory.com" // 전달하려는 Data(Value)
                )
                type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent, null))
        }
    }
}
```
