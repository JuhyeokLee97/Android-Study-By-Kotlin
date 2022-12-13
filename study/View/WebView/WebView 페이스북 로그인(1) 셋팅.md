# WebView 페이스북 로그인 셋팅

## 개요 - Deprecating support for FB Login authentication on Android embedded browsers
Android 임베디드 브라우저(웹 보기)에서 피싱 시도가 증가하는 현상을 모니터링하였으므로 8월부터 Android 임베디드 브라우저에서 FB 로그인 인증을 더 이상 지원하지 않습니다. 이 날짜 이전에는 악성 활동을 예방하기 위해 위험도가 높다고 판단한 특정 사용자가 임베디드 브라우저에서 Facebook 로그인에 액세스하지 못하도록 차단할 것입니다.

현재 앱이 Android의 임베디드 브라우저에서 Facebook 로그인을 표시할 경우 SDK를 사용하고, 버전 8.2 이상으로 업데이트하고, 로그인 중에 발생하는 모든 로그인 동작 재정의를 삭제해야 합니다(즉, LoginBehavior.WEB_VIEW_ONLY 사용). 앱이 SDK 버전 8.2 이상을 사용하는 경우 다른 방법을 통해 사용자를 인증하는 여러 가지 수단을 활용합니다. 예를 들어 푸시 알림을 보내 사용자의 신원을 인증하는 옵션(일명 '비밀번호 없는 플로')이나 사용자에게 Chrome 브라우저(Chrome 사용자 지정 탭) 또는 Facebook Android 앱(일명 'Android App Switch')에서 로그인을 완료하도록 요청하는 옵션이 있습니다. 이러한 대체 인증 방법은 더욱 안전한 옵션일 뿐만 아니라 사용자가 더 이상 비밀번호를 직접 입력하여 로그인할 필요가 없기 때문에 사용자 환경을 개선하고 전환율을 높입니다.

이러한 접근 방식으로도 대체 방법을 통해 사용자를 인증하지 못하는 경우가 있을 수 있습니다. 이 경우 사용자는 Android 웹 보기에서의 로그인이 차단됩니다. 이때 다른 기기를 사용하여 로그인하는 것이 좋습니다.

여러분의 파트너십에 감사드리며 Facebook은 앞으로도 플랫폼 보안에 계속 투자할 것입니다.

## 목차
1. [페이스북 앱 만들기](#1-페이스북-앱-만들기)</br>
  1.1. [앱 만들기](#1-앱-만들기)</br>
  1.2. [앱 유형 입력](#2-앱-유형-입력)</br>
  1.3. [앱 상세정보 입력](#3-앱-상세정보-입력)</br>
  1.4. [앱 상세 이동](#4-앱-상세-이동)</br>
  1.5. [페이스북 로그인 설정](#5-페이스북-로그인-설정)</br>
2. [Facebook SDK 등록](#2-facebook-sdk-등록)</br>
3. [리소스 및 Manifest 설정](#3-리소스-및-manifest-설정)</br>
4. [패키지 이름 및 기본 Activity, 페이스북 앱과 연결](#4-패키지-이름-및-기본-activity-페이스북-앱과-연결)</br>
5. [개발용(디버그) 해시 키 등록](#5-개발용디버그-해시-키-등록)</br>
6. [페이스북 로그인 콜백 추가(FacebookLoginProvider.kt)](#6-페이스북-로그인-콜백-추가facebookloginproviderkt)</br>
7. [페이스북 로그인 구현(MainActivity.kt)](#7-페이스북-로그인-구현mainactivitykt)</br>
  
## 1. 페이스북 앱 만들기
페이스북 로그인을 구현하기 위해서는 [Meta for Developers](https://developers.facebook.com/)페이지에서 개발자 계정으로 **페이스북 앱**을 만들어야 한다.</br>
페이스북 앱 만들기 과정은 간단하니 아래 과정을 천천히 따라하면 된다.

### 1. 앱 만들기
[Meta for Developers](https://developers.facebook.com/)에 접속하여 먼저 로그인을 한다.</br>
그리고 아래 이미지에서와 같이 오른쪽 상단 <strong>[내 앱]</strong>으로 접속한다.</br>

<img src="https://user-images.githubusercontent.com/40654227/205607411-daf63644-149b-4ae3-9b85-b12cab14478c.png" width=600 /></br>

그러면 다음 이미지처럼 내 앱의 목록과 <strong>[앱 만들기]</strong> 버튼을 확인할 수 있다.</br>
기존에 만든 앱을 사용할 수 있지만, 새로운 앱을 만들어 사용하도록 한다.</br>
<strong>[앱 만들기]</strong> 버튼을 클릭한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205608729-fe44d286-cade-4d7a-a70b-024f3b3490c0.png" width=600/>

### 2. 앱 유형 입력

아래와 같은 앱 유형 선택 화면에서는 페이스북 로그인을 위한 간단한 앱이기 때문에, <strong>[없음]</strong>을 선택한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205609114-3bd9dc19-8d3f-4477-9f32-fe1d78cdaee2.png" width=600/></br>


### 3. 앱 상세정보 입력
다음으로는 앱 상세 정보를 입력한다. </br>
[앱 이름], [앱 연락처 이메일]에 정보를 입력하고 [비지니스 계정]은 특별히 작성할 필요는 없다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205609638-bd257bf4-b412-4415-9260-569354f1d6c7.png" width=600/></br>

### 4. 앱 상세 이동
다시 개발제 폐이지로 이동하여 <strong>[내 앱]</strong>을 클릭한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205610116-680893de-8cb8-4fe9-93ac-23657a4a82bb.png" width=600/></br>

그러면 다음과 같이 위 과정에 생성한 앱을 확인할 수 있다.</br>
해당 앱을 클릭하여 <strong>앱 대시보드</strong>로 이동한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205610546-770f9afa-93a1-4640-816a-47e2f9cf5efa.png" width=600/></br>

### 5. 페이스북 로그인 설정
아래 이미지처럼 만든 앱에 대한 대시정보를 확인할 수 있다.</br>
<strong>[제품 추가]</strong> 카테고리에서 <strong>[Facebook 로그인] > [설정]</strong>으로 이동한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205610732-5d49e446-d50c-4703-834c-e33938fe5016.png" width=600/></br>

그리고 <strong>[Android]</strong>을 선택해준다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205611105-5e9d7d76-2a60-4261-98a9-1c840240ba96.png" width=600/></br>

## 2. Facebook SDK 등록

<p>
  페이스북 로그인을 사용할 프로젝트에 Facebook SDK를 등록한다.
</p>

1. 프로젝트에서 `build.gradle(Project:..)` 또는 `settings.gradle`에서 다음과 같이 `repositories {}` 섹션에 `mavenCentral()` 저장소가 입력되어 있는지 확인한다.
``` kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "FbLoginWebViewSample"
include ':app'

```

2. 프로젝트에서 `build.gradle(Module:..)`에서 `dependencies{}` 섹션에 ` implementation 'com.facebook.android:facebook-login:latest.release'
` 구현문을 추가하여 최신 버전의 Facebook 로그인 SDK 종속성을 추가한다. [최신버전확인](https://developers.facebook.com/docs/android/)
``` kotlin

dependencies {
    ...
    // Facebook Login SDK
    implementation 'com.facebook.android:facebook-login:15.1.0'
}
```

## 3. 리소스 및 Manifest 설정

1. `/app/res/values/strings.xml`에 다음과 같이 문자열을 저장한다.

``` xml
    <string name="facebook_app_id">534473875260631</string>
    <string name="fb_login_protocol_scheme">fb1234</string>
    <string name="facebook_client_token">de63a367348bba44f51fbf1ae48ce9d2</string>
```

각각의 정보는 아래 이미지처럼 페이스북 개발자 계정으로 만든 앱의 <strong>[설정] > [고급 설정]</strong>에서 확인할 수 있다.
<img src="https://user-images.githubusercontent.com/40654227/205615647-b018b22d-ede3-4d65-b732-2b47f7aa1f52.png" width=600/>

2. `/app/manifest/AndroidManifest.xml`설정
2.1. `meta-data` 요소를 `앱 ID`와 `클라이언트 토큰`을 `application` 요소에 다음과 같이 추가한다.
``` xml

<application android:label="@string/app_name" ...>
    ...
   	<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>
   	<meta-data android:name="com.facebook.sdk.ClientToken" android:value="@string/facebook_client_token"/>
    ...
</application>

```

  2.2. Facebook Login 관련 Activity와 Chrome Custom Tab 관련 Activity를 추가한다.
    
    ``` xml
    <application ...>
        <activity android:name="com.facebook.FacebookActivity"
            android:configChanges=
                    "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
            android:label="@string/app_name" />
        <activity
            android:name="com.facebook.CustomTabActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="@string/fb_login_protocol_scheme" />
            </intent-filter>
        </activity>
    </applicaton>
    ```

  2.3. 인터넷 권한 설정을 추가한다.
    
    ``` xml
    <manifest ...>

        <uses-permission android:name="android.permission.INTERNET"/>
        <application...>
        </application>
    </manifest>
    ```

이렇게 **리소스 및 Manifest**를 설정하고 앱을 빌드해본다.

### 4. 패키지 이름 및 기본 Activity, 페이스북 앱과 연결
1. 프로젝트의 패키지 이름을 등록한다.
프로젝트 패키지 이름과 Activity를 등록하기 위해서는 <strong>[페이스북 앱] > [설정] > [기본 설정] </strong>에서 아래와 같이 <strong>+ 플랫폼 추가</strong> 버튼을 이용하여 Google Play를 추가한다.
<img src="https://user-images.githubusercontent.com/40654227/205625891-ab80cd3f-bb51-4e13-a9e3-5352e517b75a.png" width=600/>
<img src="https://user-images.githubusercontent.com/40654227/205626467-bbe3c290-e1d2-49de-b2aa-5831fee8ff11.png" width=600/>
<img src="https://user-images.githubusercontent.com/40654227/205626577-b7b94f8e-43c0-4f8f-a9d5-5065acb2b014.png" width=600/>



프로젝트에서 `AndroidManifest.xml`에서 `<manifest package="..."/>` package를 가져와 다음과 같이 저장한다.</br>
그리고 앱 시작의 기본 Activity인 `MainActivity`를 패키지명 뒤에 붙여 저장한다.</br>
<img src="https://user-images.githubusercontent.com/40654227/205626905-cbbdb68e-407e-4e8d-82dd-a60e19cbe5dc.png" width=600/>

### 5. 개발용(디버그) 해시 키 등록
안드로이드 앱과 Facebook 간의 통신의 진실성 보장을 위해 개발 환경(ex. Android Studio)에 대한 Android 키 해시를 제공해야한다.</br>
개발(디버그) 키 해시 생성을 위해서는 개발 환경의 운영체제에 따라 다음과 같다.</br>
<strong>[Mac OS]</strong>
- Java Development Kit의 키 및 인증 관리 도구인 Keytool이 필요하다.
- 개발(디버그) 키 해시 생성을 위해서는 Android Studio에서 터미널 창을 열고 다음 명령어를 입력하여 실행한다.
``` terminal    
keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
```

</br>

<strong>[Windows]</strong>
- Java Development Kit의 키 및 인증 관리 도구 Keytool이 필요하다.
- Google Code Archive의 Windows용 OpenSSL 라이브러리 [openssl-for-windows](https://code.google.com/archive/p/openssl-for-windows/downloads)가 필요하다.
- 개발 키 해시 생성을 위해서는 Android Studio에서 터미널 창을 열고 다음 명령어를 입력하여 실행한다.

``` terminal    
keytool -exportcert -alias androiddebugkey -keystore "C:\Users\USERNAME\.android\debug.keystore" | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" sha1 -binary | "PATH_TO_OPENSSL_LIBRARY\bin\openssl" base64     
```

명령어를 실행하면 개발 환경에 고유한 <strong>28자 키 해시</strong>가 생성된다.</br>
생성된 해시를 복사하여 다음과 같이 페이스북 앱 <strong>[설정] > [기본 설정]</strong>에서 <strong>[Android] > [키 해시]</strong>에 등록한다.</br></br>
<img src="https://user-images.githubusercontent.com/40654227/206138409-5e47b8ed-a94b-4d9e-8c73-efd1ca4ec801.png" width=600/>
