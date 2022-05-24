## 개요

### 시나리오

Activity에서 **갤러리 접근** 버튼을 클릭 하면 **접근 권한 확인 후** 디바이스 갤러리에 접근한다. 사진 선택 시, 해당 사진을 상단 ImageView에 보여준다.

### 실행 화면
<p align="center">
  <img src="https://user-images.githubusercontent.com/40654227/165413531-c72342fc-6f2c-4aa6-a48f-00cae52e9227.gif"/>
</p>

## 프로젝트 구조

![](https://user-images.githubusercontent.com/40654227/165413744-289a21f4-d878-437a-99dd-f4d55a52a6bd.png)

### ViewBinding, Coil 사용 - In build.gradle(:app)

**ViewBinding**을 사용하기 위해 `viewBinding { enabled = true }` 를 **build.gradle(:app)**에 추가했다.  
**ImageView**에 이미지 첨부를 위해 **Coil**을 사용했고 의존성으로 `implementation "io.coil-kt:coil:2.0.0-rc03"`를 **build.gradle(:app)**에 추가했다.

```
plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    compileSdk 31

    defaultConfig {
        applicationId "com.example.showgalleryapplication"
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
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    // Coil
    implementation "io.coil-kt:coil:2.0.0-rc03"
}
```

## Code

### AndroidManifest.xml

갤러리 접근 권한을 얻기 위해 `<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />`을 추가한다.

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.showgalleryapplication">

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.ShowGalleryApplication">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="20dp"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/image_view"
        android:layout_width="0dp"
        android:layout_height="500dp"
        android:background="#ffececec"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btn_show_gallery"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#6492EF"
        android:text="사진첨부"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

```
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import coil.load
import com.example.showgalleryapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PICK_IMAGE_FROM_GALLERY = 1000
    private val PICK_IMAGE_FROM_GALLERY_PERMISSION = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowGallery.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 겨우
                ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> showGallery(this@MainActivity)

                // 갤러리 접근 권한이 없는 경우 && 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> showPermissionContextPopup()

                // 권한 요청 하기
                else -> requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE_FROM_GALLERY_PERMISSION)
            }
        }
    }

    private fun showGallery(activity: Activity) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, PICK_IMAGE_FROM_GALLERY)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PICK_IMAGE_FROM_GALLERY_PERMISSION)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    // 사진 선택(갤러리에서 나온) 이후 실행되는 함수
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK)
            data?.let { it -> binding.imageView.load(it.data) }
    }

    // 권한 요청 승인 이후 실행되는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PICK_IMAGE_FROM_GALLERY_PERMISSION ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showGallery(this@MainActivity)
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
```

## 개념 설명

### 권한 요청 워크플로우
<p align="center">
<img src="https://user-images.githubusercontent.com/40654227/165419943-42cae3bb-cdcf-4d86-b0bb-1269d8eda7f7.png"/>
</p>

### ContextCompat.checkSelfPermission(): 앱이 이미 권한이 부여되었는지 확인

사용자가 이미 앱에 특정 권한을 부여했는지 확인하려면 **`checkSelfPermission(Context context, String permission)`** 메서드에 권한을 전달한다. 이 메서드는 앱에 권한이 있는지에 따라서 **PERMISSION\_GRANTED** 또는 **PERMISSION\_DENIED**를 반환한다.

```
ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
```

### shouldShowRequestPermissionRationale(): 권한 요청 전에 권한이 필요한 이유를 사용자에게 설명해야 하는 지에 대한 여부 반환

requestPermissions()를 호출하기 전에 앱에서 권한을 요청하는 이유를 사용자에게 설명하는 것이 좋다. 연구에 따르면 사용자가 앱에서 권한이 필요한 이유를 알고 있을 때 권한 요청을 훨씬 더 편안하게 느낀다고 한다.

**ContextCompap.checkSelfPermission()** 메서드가 **PERMISSION\_DENIED**를 반환하면 **`shouldShowRequestPermissionRationale()`**을 호출해라. 이 메서드가 **true**를 반환하면 교육용 UI를 사용자에게 표시한다. 이 UI에서 사용자가 사용 설정하려는 기능에 특정 권한이 필요한 이유를 설명해야 한다.

### requestPermissions(@NonNull String\[\] permissions, int requestCode): 권한 요청

사용자에게 교육용 UI 가 표시되었거나, **`shouldShowRequestPermissionRationale()`**의 반환 값에서 이번에는 교육용 UI를 **표시하지 않아도 된다고(false) 나타내면 권한을 요청한다**.

**permissions**에 해당하는 권한들을 요청한다. 단, **permissions**에 있는 권한들은 **manifest**에 꼭 추가 되어있어야 한다.

권한 요청이 완료되면 **`onRequestPermissionResult()`** 함수를 호출한다.
