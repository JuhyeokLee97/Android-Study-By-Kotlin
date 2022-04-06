# 안드로이드 카카오 SDK V2 로그인 - part1(프로젝트 셋업)

## 1\. 애플리케이션 등록하기

카카오 SDK를 사용하기 위해서는 [Kakao Developers](https://developers.kakao.com/) 공식 페이지에 접속하여 아래 사진과 같이 로그인 하여 우측 상단의 **\[내 애플리케이션\]** 에 들어가서 등록해야 한다.

![카카오 개발자 공식 페이지](https://user-images.githubusercontent.com/40654227/132414972-b7586ff9-5aba-4c92-b28b-856260a893f0.jpg)

**\[내 애플리케이션\]** 에 접속하게 되면 아래와 같은 화면이 나오게 된다. **\[애플리케이션 추가하기\]** 를 클릭하게 하여 **앱 이름** 과 **사업자명** 을 입력하고 저장하면 애플리케이션 등록이 완료된다.

![카카오 개발자 공식 페이지_애플리케이션 추가1](https://user-images.githubusercontent.com/40654227/132415571-3fbf3044-5ec4-46a1-a73b-b1bb692ee1c0.jpg)![카카오 개발자 공식 페이지_애플리케이션 추가2](https://user-images.githubusercontent.com/40654227/132415553-23d8e3f1-3e29-4ff5-a766-adaa1c414eab.jpg)![카카오 개발자 공식 페이지_애플리케이션 추가3](https://user-images.githubusercontent.com/40654227/132415615-bb7c1a31-3b4d-4042-8839-6613af2021e2.jpg)

## 2\. Android 플랫폼 등록

Android App에서 카카오 SDK를 사용하기 위한 애플리케이션 등록을 완료했다. 추가로 사용하는 애플리케이션의 플랫폼을 등록해줘야 한다.  
플랫폼 등록은 **\[내 애플리케이션\] > \[앱 설정\] > \[플랫폼\]** 에서 확인할 수 있다. 위 사진과 같은 페이지에서 해당 프로젝트를 선택하게 되면 아래와 같이 참조할 수 있을 것이다.

![카카오 개발자 공식 페이지_Android 플랫폼 등록1](https://user-images.githubusercontent.com/40654227/132416465-c5a721c6-6301-46f4-baa3-146bda9d1e59.jpg)

### 1\. 패키지명, 마켓 URL 등록

**\[Android 플랫폼 등록\]** 버튼을 눌러주면 **패키지명, 마켓 URL, 키 해시** 값을 입력하라고 나온다. **패키지명, 마켓 URL** 은 Android 프로젝트의 **AndroidManifest.xml** 에서 아래 사진과 같이 확인하고 **패키지명** 에 복사&붙여넣기 해주면 자동으로 **마켓 URL** 이 생성된다.

![카카오 개발자 공식 페이지_Android 플랫폼 등록2](https://user-images.githubusercontent.com/40654227/132417383-19a268ba-f81a-4bae-af11-307fd3ded6df.jpg)

아래는 내 패키지 명이다.

![카카오 개발자 공식 페이지_Android 플랫폼 등록2-2](https://user-images.githubusercontent.com/40654227/132417649-f172d3bb-d938-4033-bd6b-c00b4089796b.jpg)

### 2\. 키 해시 등록

**키 해시** 등록을 위해서는 등록된 앱에서 SDK를 이용하여 API 호출을 통해 확인할 수 있다.  
카카오 SDK를 사용하기 위한 셋팅으로

1.  gradle(Project)에 `maven { url 'https://devrepo.kakao.com/nexus/content/groups/public/’ }`추가
    
    -   일반적으로 build.gradle(Project: )파일에서 **repositories** 에  
        `maven { url https://devrepo.kakao.com/nexus/content/groups/public/’ }`를 추가하는데, gradle v7.0.1를 사용한 프로젝트에서는 제대로 동기화 되지 않아서 **settings.gradle(Project Settings)** 에서 **repositories** 에 추가했다.
    
    ![카카오 개발자 공식 페이지_Android 플랫폼 등록3-1](https://user-images.githubusercontent.com/40654227/132419093-9d1de43a-fe2f-4d55-afc3-a2b89af20057.jpg)
    
2.  gradle(Module)에 **dependencies** 에 `implementation "com.kakao.sdk:v2-user:2.7.0"` 추가한다.  
    
    ![카카오 개발자 공식 페이지_Android 플랫폼 등록3-2](https://user-images.githubusercontent.com/40654227/132419250-e9784ee9-5912-4da2-a44b-803e30c686c7.jpg)
    
3.  인터넷 통신을 위한 AndroidManifest,xml 파일에 `<uses-permission android:name="android.permission.INTERNET" />` 추가한다.  
    
    ![카카오 개발자 공식 페이지_Android 플랫폼 등록3-3](https://user-images.githubusercontent.com/40654227/132419312-7053367e-7c2f-436a-9e4f-db19b8de74e5.jpg)
    
4.  MainActivity.class의 onCreate함수 안에 아래 코드를 삽입한다.
    
    ![카카오 개발자 공식 페이지_Android 플랫폼 등록3-4](https://user-images.githubusercontent.com/40654227/134910630-928fe4e2-6b90-49cf-9c06-2e3b43a267b5.jpg)
    
5.  `var keyHash = Utility.getKeyHash(this) Log.d("KeyHash", keyHash)`

5.  앱 실행하여 Log값 확인 후 **키 해시** 값을 복사하여 **\[Android 플랫폼 등록\] > \[키 해시\]** 에 삽입 후 저장하면 프로젝트 셋팅 완료다.  
    ![카카오 개발자 공식 페이지_Android 플랫폼 등록2](https://user-images.githubusercontent.com/40654227/132417383-19a268ba-f81a-4bae-af11-307fd3ded6df.jpg)
