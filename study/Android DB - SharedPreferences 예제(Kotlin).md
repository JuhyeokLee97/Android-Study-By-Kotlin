## 개요

Android App 개발 중 가벼운 DB가 필요한 경우 **SharedPreferences** 를 사용하기를 추천한다.  
  

Android Developers 공식 문서에서 **SharedPreferences** 를 설명하기를...  
  
**SharedPreferences** >> 저장하려는 키-값(key-value) 컬렉션이 비교적 작은 경우 **SharedPreferences API** 를 사용해야 합니다. **SharedPreferences** 객체는 키-값 쌍이 포함된 파일을 가리키며 키-값 쌍을 읽고 쓸 수 있는 간단한 메서드를 제공합니다. 각 **SharedPreferences** 파일은 프레임워크에서 관리하며 비공개이거나 공유일 수 있습니다.

예제로 **SharedPreferences** 를 이용하여 가상의 사용자의 정보를 저장하여 읽고 쓰기를 하도록 한다.

### 실행화면

![](https://user-images.githubusercontent.com/40654227/140953447-3699408f-63d4-4b33-a944-60cf0e74522d.gif)

## Code - Example

### activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvName"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="10dp"
        android:text="Name: "
        android:textColor="@color/black"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="@id/etName"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/etName" />

    <EditText
        android:id="@+id/etName"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginEnd="20dp"
        android:inputType="textPersonName"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tvName"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias=".3" />

    <TextView
        android:id="@+id/tvMajor"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="10dp"
        android:text="Major: "
        android:textColor="@color/black"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="@id/etMajor"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/etMajor" />

    <EditText
        android:id="@+id/etMajor"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:layout_marginEnd="20dp"
        android:inputType="text"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tvMajor"
        app:layout_constraintTop_toBottomOf="@id/etName" />


    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnSave"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#3F51B5"
        android:text="저장하기"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="@id/etMajor"
        app:layout_constraintStart_toStartOf="@id/etMajor"
        app:layout_constraintTop_toBottomOf="@id/etMajor" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

```
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // "user" 이름을 갖는 SharedPreferences 생성(가져오기)
        val sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
        // user-sharedPreferences 에서 "name" 키로 저장된 값을 name 변수에 저장(단, 저장된 값이 없다면 "DefaultName"을 반환)
        var name = sharedPreferences.getString("name", "DefaultName")
        // user-sharedPreferences 에서 "major" 키로 저장된 값을 name 변수에 저장(단, 저장된 값이 없다면 "DefaultMajor"을 반환)
        var major = sharedPreferences.getString("major", "DefaultMajor")

        val etName: EditText = findViewById(R.id.etName)
        etName.setText(name)

        val etMajor: EditText = findViewById(R.id.etMajor)
        etMajor.setText(major)

        val btnSave: AppCompatButton = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            var etNameText = etName.text.toString()
            var etMajorText = etMajor.text.toString()

            // 입력된 값으로 name, major 값 저장
            sharedPreferences.edit(){
                putString("name", etNameText)
                putString("major", etMajorText)
            }
        }
    }
}
```
