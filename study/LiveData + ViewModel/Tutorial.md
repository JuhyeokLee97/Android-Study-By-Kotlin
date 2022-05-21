# LiveData + ViewModel Tutorial
#### 참고). [ViewModel 이란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/LiveData%20+%20ViewModel/ViewModel.md)
## Jetpack.ViewModel과 LiveData를 이용한 옵저버 패턴 
### 개요
<P>

``LiveData``와 ``ViewModel`` 그리고 ``Observer Pattern``을 이용하여 화면에 보여지는 TextView(UI)의 값을 변경하는데 있어서  <strong>데이터 처리 로직</strong>과 <strong>UI 로직</strong>을 분리한다.

``EditText``에 값을 입력하여 ``더하기``버튼 또는 ``빼기``버튼 클릭 시, 산수 연산(``데이터 처리 로직``)을 ``ViewModel``에서 처리하고 ``UI Controller(View)``에서는 연산 값을 Observing하여 ``ViewModel``에서 ``View``에 UI 처리를 요청하지 않고 ``View``단에서 자동으로 UI를 업데이트 하여 계산된 값을 보여준다.
</P>

#### 실행 영상
<p align="center">
<img src="https://user-images.githubusercontent.com/89065117/169432527-b58b532c-f01d-48bc-95fe-9e5cbaed38a4.gif"/>
</p>

### build.gradle(Module) : ViewBinding 설정
build.gradle(Module) 파일에 안에 ``android { } `` 속성으로 아래 ViewBinding 설정을 위한 코드를 삽입한다.
```
viewBinding{
    enabled = true
}
```
#### build.gradle(Module) 코드 전체
``` xml
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    compileSdk 31

    defaultConfig {
        applicationId "com.example.testlivedataviewmodel"
        minSdk 26
        targetSdk 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

    viewBinding{
        enabled = true
    }
}

dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.6.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/tv_number"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        android:textSize="30dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/et_input_number"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        app:layout_constraintEnd_toStartOf="@id/btn_plus"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_number" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btn_plus"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="더하기"
        app:layout_constraintBottom_toBottomOf="@id/et_input_number"
        app:layout_constraintEnd_toStartOf="@id/btn_minus"
        app:layout_constraintHorizontal_chainStyle="packed"
        app:layout_constraintStart_toEndOf="@id/et_input_number"
        app:layout_constraintTop_toTopOf="@id/et_input_number" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btn_minus"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="빼기"
        app:layout_constraintBottom_toBottomOf="@id/et_input_number"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/btn_plus"
        app:layout_constraintTop_toTopOf="@id/et_input_number" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### NumberViewModel.kt: ViewModel 구현
``` kotlin
enum class ActionType {
    PLUS, MINUS
}

class NumberViewModel : ViewModel() {
    companion object {
        const val TAG = "태그"
    }

    // 내부에서 설정하는 자료형은 Mutable
    // 변경가능하도록 설정
    private val _currentValue = MutableLiveData<Int>()

    // 변경되지 않는 데이터를 가져올 때 이름을 _ 언더스코어 없이 설정
    // 공개적으로 가져오는 변수는 private 이 아닌 `public`으로 외부에서도 접근 가능하도록 설정
    // 하지만 값을 직접 라이브데이터에 접근하지 않고 뷰모델을 통해 가져올 수 있도록 설정
    val currentValue: LiveData<Int>
        get() = _currentValue

    // 초기값 설정
    init {
        Log.d(TAG, "NumberViewModel - 생성자 호풀: ")
        _currentValue.value = 0
    }

    // 뷰모델이 가지고 잇는 값을 변경하는 메서드
    fun updateValue(actionType: ActionType, input: Int) {
        when (actionType) {
            ActionType.PLUS -> _currentValue.value = _currentValue.value?.plus(input)
            ActionType.MINUS -> _currentValue.value = _currentValue.value?.minus(input)
        }
    }

}
```
### MainActivity.kt(UI-Controller): ViewModel + Observing Pattern 
```kotlin
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "로그"
    }

    lateinit var myNumberViewModel: NumberViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myNumberViewModel = ViewModelProvider(this).get(NumberViewModel::class.java)
        myNumberViewModel.currentValue.observe(this) {
            Log.d(TAG, "MainActivity - myNumberViewModel - currentValue 라이브 데이터 값 변경:  $it")
            // UI Update
            binding.tvNumber.text = it.toString()
        }

        binding.apply {
            btnPlus.setOnClickListener {
                val userInput = etInputNumber.text.toString().toInt()
                myNumberViewModel.updateValue(ActionType.PLUS, userInput)
            }
            btnMinus.setOnClickListener {
                val userInput = etInputNumber.text.toString().toInt()
                myNumberViewModel.updateValue(ActionType.MINUS, userInput)
            }
        }
    }
}
```

## 학습
### [Jetpack.ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko)
UI Controller(Activity, Fragment)의 목적
 - 기본적으로 UI 데이터 표시
 - 사용자 작업에 반응
 - 권한 요청과 같은 운영체제 커뮤니케이션

UI Controller 부하
원인: UI Controller에 DB나 Network(server)에서 데이터 로드를 요구하는 경우.
결과: UI를 관리하는 클래스가 너무 커진다. UI 컨트롤러에 과도한 책임을 할당하면 다른 클래스로 작업이 위임되지 않고, 단일 클래스가 혼자서 앱의 작업을 모두 처리하려고 할 수 있습니다. 또한 이런 방법으로 UI 컨트롤러에 과도한 책임을 할당하면 테스트가 훨씬 더 어려워집니다.

=> UI 컨트롤러 로직에서 뷰 데이터 소유권을 분리하는 방법이 훨씬 쉽고 효율적입니다.
