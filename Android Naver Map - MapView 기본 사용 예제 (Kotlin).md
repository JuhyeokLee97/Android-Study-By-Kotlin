## 개요 - 실행화면

![](https://user-images.githubusercontent.com/40654227/144889272-f6881017-8cc4-4dcc-88f2-5e6bd33024e0.gif)

## 초기 셋팅(NCloud에 Application 등록)

Android 앱에서 **Naver Map** 을 사용하기 위해서는 [https://www.ncloud.com/product/applicationService/maps](https://www.ncloud.com/product/applicationService/maps) 에 접속하여 앱을 등록해야 한다.

![](https://user-images.githubusercontent.com/40654227/144875751-c6722776-79cd-4913-8690-ae5868cfa0ef.png)

-   로그인 후, **이용 신청하기** 버튼을 클릭한다.
-   아래와 같은 페이지로 접속하게 된다.

![](https://user-images.githubusercontent.com/40654227/144876560-28a78362-0887-49bf-abda-707efb2bace3.png)

-   **Application 등록** 버튼을 클릭한다.
-   아래와 같은 페이지로 접속하게 된다.

![](https://user-images.githubusercontent.com/40654227/144877246-1712e42a-60b3-43ee-875e-4771b1ab848c.png)

-   **Application 이름** 에는 앱 이름을 자유롭게 지어주면 된다.(ex. MyNaverMapExample)
-   **Service 선택** 에서 **Maps** > **Mobile Dynamic Map** 을 체크한다.
-   **서비스 환경 등록** 에서 **Android 앱 패기지 이름** 에 작업하려는 프로젝트의 Package 이름을 등록한다.
-   등록을 완료하면 아래 화면과 같이 Application이 등록된 것을 확인할 수 있다.

![](https://user-images.githubusercontent.com/40654227/144878381-26e8c770-b48d-4302-ae8b-fd82a4181f3b.png)

-   **인증 정보** 버튼을 클릭하면 아래와 같은 정보를 확인 할 수 있다.

![](https://user-images.githubusercontent.com/40654227/144878931-7ec2f2f0-4842-408d-832e-750cb33fbc1c.png)

-   **Client ID** 는 이후에 프로젝트 셋팅에서 이용된다.

## 프로젝트 셋팅

### 의존성 추가

NCloud 개발 가이드에서는 의존성 추가에 대해서 아래와 같이 설명되어있다.  
네이버 지도 SDK는 `https://naver.jfrog.io/artifactory/maven/` Maven 저장소에서 배포됩니다. 루트 프로젝트의 `build.gradle`에 저장소 설정을 추가합니다.  
다음은 저장소 설정을 추가한 예제입니다.

```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url 'https://naver.jfrog.io/artifactory/maven/'
        }
    }
}
```

하지만 나의 Android Studio 프로젝트에서는 `build.gradle`에 `allprojects`가 없어서 설정하는데 어려움을 겪었다.  
내가 찾은 해결 방법은 **Gradle Script** > **settings.gradle** 파일에 아래 코드를 넣어줌으로써 해결되었다.

```
maven {
    url 'https://naver.jfrog.io/artifactory/maven/'
}
```

아래는 내 `settings.gradle`이다.

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url 'https://naver.jfrog.io/artifactory/maven/'
        }
    }
}
rootProject.name = "NaverMapExample"
include ':app'
```

그리고 가이드 문서를 따라서 `build.gradle(Module)`에서 dependencies 안에 아래와 같이 작성해주면 된다.

```
dependencies {

    ....

    // 네이버 지도 SDK
    implementation 'com.naver.maps:map-sdk:3.12.0'
}
```

마지막으로 변경내용을 적용하기 위해 **Sync Now** 를 해줌으로써 **의존성 추가** 가 마무리 된다.

## Code

### AndroidManifest.xml

Naver **mapView** 를 사용하기 위해서 Application 등록 후 발급 받은 Client\_ID 값을 아래와 같이 **태그** 에 삽입한다.

![](https://user-images.githubusercontent.com/40654227/144882263-0a2311b8-8149-4504-ad7b-7d36bcc4df2a.png)

### activity\_main.xml

**※ 참고 ※**  
**Naver Map** 을 사용하는 방법은 와 **<com.naver.map.MapView>(맵뷰)** 가 있다.  
를 사용하면 생명주기를 고려하지 않아도 되기 때문에 간단하게 사용하기 편리하다.  
**MapView** 는 생명주기를 직접 설정해야 하기 때문에 단순히 `*.xml` 에 넣어준다고 사용할 수 없다.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.naver.maps.map.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

앞서 말한 **MapView** 를 사용할 때에는 Activity에서 생명주기를 설정해주어야 한다.

```
class MainActivity : AppCompatActivity() {
    lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapView)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
```

#### 똑같이 했음에도 불구하고 네이버 지도가 보이지 않는다면, 가상 디바이스가 아닌 실제 디바이스를 연결하여 빌드해 보시기를 권합니다.
