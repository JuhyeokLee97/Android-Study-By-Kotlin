# 안드로이드 카카오 SDK V2 로그인 - part2(코드)

## 1\. 카카오 로그인 API 사용을 위한 설정

**\[내 애플리케이션\]** > **\[제품 설정\]** > **\[카카오 로그인\]** 에서 **카카오 로그인** 을 활성화 한다.

![카카오 로그인 API 사용을 위한 설정 1-1](https://user-images.githubusercontent.com/40654227/134912420-ecbb8093-bba9-46f1-a7dd-f00c8950a101.jpg)

**\[내 애플리케이션\]** > **\[앱 설정\]** > **\[요약 정보\]** 에서 네이트브 앱 키를 복사해서 아래와 같이 작성한다.

![카카오 로그인 API 사용을 위한 설정 1-2](https://user-images.githubusercontent.com/40654227/134914043-8f1597ee-d9ce-4b10-b5c3-75552b33de4f.jpg)![카카오 로그인 API 사용을 위한 설정 1-3](https://user-images.githubusercontent.com/40654227/134914692-363ad8a5-c410-404f-b04c-c136710c0676.jpg)

## 코드 작성

1.  GlobalApplication.kt (Application을 상속받는 클래스) 생성한다.  
    아래와 같이 onCreate함수 내에 KakaoSdk.init()을 실행한다.
2.  `class GlobalApplication: Application() { override fun onCreate() { super.onCreate() KakaoSdk.init(this, getString(R.string.kakao_app_key)) } }`
3.  **AndroidManifest.xml** 파일에서 **application** 태그 속성 값으로 `android:name=".GlobalApplication"`을 추가한다.

![카카오 로그인 구현 1-2](https://user-images.githubusercontent.com/40654227/134916324-ff0a240a-83fb-4041-88e8-e9b2e7282c49.jpg)

3.  아래와 같이 **AndroidManifest.xml** 파일에서 **application** 태그 안에 코드를 추가하여 **Redirect URI** 설정한다.  
    (예를 들어 네이티브 앱 키가 '123456789'라면 'kakao123456789'를 입력한다.)  
    ![카카오 로그인 구현 1-3](https://user-images.githubusercontent.com/40654227/134917385-8dc61d31-708b-42fd-a1e9-8094d6823b4c.jpg)

```
<activity android:name="com.kakao.sdk.auth.AuthCodeHandlerActivity"  
  android:exported="true">  
     <intent-filter> 
         <action android:name="android.intent.action.VIEW" />  
         <category android:name="android.intent.category.DEFAULT" />  
         <category android:name="android.intent.category.BROWSABLE" />  

          <!-- Redirect URI: "kakao{NATIVE_APP_KEY}://oauth" -->  
          <data android:host="oauth"  
          android:scheme="kakaoadf1f3ccd6e66fea62118ef585896c09" />  
    </intent-filter>
</activity>
```

4.  화면에 버튼을 만든다.  
    **\[activity\_main.xml\]**
    
    ```
    <?xml version="1.0" encoding="utf-8"?> <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto" xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent" android:layout_height="match_parent" tools:context=".MainActivity"> <androidx.constraintlayout.utils.widget.ImageFilterButton android:id="@+id/btn_kakao_login" android:layout_width="wrap_content" android:layout_height="wrap_content" android:src="@drawable/kakao_login_large_narrow" app:layout_constraintBottom_toBottomOf="parent" app:layout_constraintEnd_toEndOf="parent" app:layout_constraintStart_toStartOf="parent" app:layout_constraintTop_toTopOf="parent" app:layout_constraintVertical_bias=".7" /> </androidx.constraintlayout.widget.ConstraintLayout>
    ```
    
5.  로그인 로직을 구현한다.  
    **\[MainActivity.kt\]**

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.constraintlayout.utils.widget.ImageFilterButton
        android:id="@+id/btn_kakao_login"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/kakao_login_large_narrow"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias=".7" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
