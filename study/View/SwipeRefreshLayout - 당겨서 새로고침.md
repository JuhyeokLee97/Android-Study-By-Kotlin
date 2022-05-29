
# RefreshLayout

## 개요
### ``Swiperefreshlayout``를 이용하면 스와이프(드래그)하여 새로고침 UI 패언틀 구현할 수 있다.
- 앱 화면을 위에서 아래로 드래그 하면, ``delayMillis(1.5s)`` 동안 새로고침 UI 패턴이 동작한다.
- 새로고침이 동작하는 동안은 텍스트 내용이 사라졌다가 완료되면 다시 보여진다.
### 실행화면
<p align = "center">
<img src="https://user-images.githubusercontent.com/40654227/170872175-51d63196-e97b-472f-8a5b-248b71d5fce7.gif"
width = 300/>
</p>

## 프로젝트 셋팅
### viewBindnig
``` kotlin
viewBinding{
	enabled true
}
```
위의 코드를 ``build.gradle(:app)`` 파일의 ``android{ }`` 속성 값으로 아래 코드와 같이 삽입한다.
``` kotlin
plugins {  
  id 'com.android.application'  
  id 'kotlin-android'  
}  
  
android {  
  compileSdk 31  
  ...
  viewBinding{  
      enabled true  
  }  
}  
  
dependencies {  
  
  implementation 'androidx.core:core-ktx:1.7.0'  
  implementation 'androidx.appcompat:appcompat:1.4.1'  
  ...
}
```
### SwipeRefreshLayout
<strong>SwipeRefreshLayout</strong>의 종속 항목을 추가하려면, 다음과 같이 앱의 ``build.gradle(:app)``파일에 코드를 추가한다.
``` kotlin
dependencies {
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
}

```

## Code

### build.gradle(:app)
``` kotlin
plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    compileSdk 31

    defaultConfig {
        applicationId "com.example.tempapplication"
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
        enabled true
    }
}

dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
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

    <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">


        <TextView
            android:id="@+id/contentsText"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="SwipeRefreshLayout은 이제 NestedScrollingChild3 및 NestedScrollingParent3을 구현하여 3개의 상위 요소와 하위 요소를 중첩 스크롤해 SwipeRefreshLayout을 통해 소비된 중첩 스크롤 거리를 전달할 수 있습니다. 현재 개발자 코드가 SwipeRefreshLayout.onNestedScroll(View, int, int, int, int, int)을 재정의하는 경우 이 메서드는 더 이상 호출되지 않으므로 대신 SwipeRefreshLayout.onNestedScroll(View, int, int, int, int, int, int[])을 재정의해야 합니다. 마찬가지로 SwipeRefreshLayout.dispatchNestedScroll(int, int, int, int, int[], int)이 더 이상 호출되지 않으며 대신 SwipeRefreshLayout.dispatchNestedScroll(int, int, int, int, int[], int, int[])을 재정의해야 합니다." />

    </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
  
import androidx.appcompat.app.AppCompatActivity  
import android.os.Bundle  
import android.os.Handler  
import androidx.core.os.postDelayed  
import androidx.core.view.isVisible  
import com.example.tempapplication.databinding.ActivityMainBinding  
  
class MainActivity : AppCompatActivity() {  
  
    private lateinit var binding: ActivityMainBinding  
    private val delayMillis = 1500L  
  
  override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        binding = ActivityMainBinding.inflate(layoutInflater)  
        setContentView(binding.root)  
  
        binding.apply {  
  refreshLayout.setOnRefreshListener {  
  contentsText.isVisible = false  
  
  // 새로고침 이후  
  Handler().postDelayed(delayMillis) {  
  contentsText.isVisible = true  
  refreshLayout.isRefreshing = false  
  }  
 } }  }  
}
```
