# 안드로이드 FCM 예제 - Part1 (Firebase 프로젝트 만들기, 앱 등록)

## Firebase 프로젝트 만들기

FCM(Firebase Cloud Messaging)을 사용하기 위해서는 Firebase 프로젝트를 만들고 앱을 등록해야 한다.

1.[Firebase Console](https://console.firebase.google.com/)에 접속하여 로그인 한다.  
2.프로젝트 추가 버튼을 클릭한다.

![Firebase 프로젝트 만들기 1](https://user-images.githubusercontent.com/40654227/135788290-2dddfb41-f364-46eb-a5e2-3698095c58f5.jpg)

  
3.프로젝트 이름을 임의로 작성한다.

![Firebase 프로젝트 만들기 2](https://user-images.githubusercontent.com/40654227/135788362-88dd531a-763e-4983-be7f-4d042faafc18.jpg)

4.개인적인 공부만을 위해서라면 "Google 애널리틱스"를 비활성을 추천한다.

![Firebase 프로젝트 만들기 3](https://user-images.githubusercontent.com/40654227/135788531-ef6f0483-fa1d-463d-b922-a24aba96b4ec.jpg)

## Firebase에 앱 등록하기

1.**프로젝트 개요**에 들어가서 안드로이드 로고를 선택하여 본인의 앱을 추가한다.

![Firebase 앱 등록하기 1](https://user-images.githubusercontent.com/40654227/135788669-8b8cb1cd-ec36-47a1-b08e-257af786d0b3.jpg)

  
2.안드로이드 프로젝트, **AndroidManifest.xml** 에서 **Package** 값을 넣어준다.

![Firebase 앱 등록하기 2](https://user-images.githubusercontent.com/40654227/135788770-b5705800-f028-4c8b-a85a-7ca91e50701e.jpg)![Firebase 앱 등록하기 3](https://user-images.githubusercontent.com/40654227/135788805-e1f4bdcc-47a0-425a-8c60-97e4ebc21d97.jpg)

  
3.구성 파일인 **google-services.json** 파일을 다운로드 한다. 파일을 프로젝트 app 폴더에 넣어준다.

![Firebase 앱 등록하기 4](https://user-images.githubusercontent.com/40654227/135788888-90838368-4251-4142-8d64-8afee859d024.jpg)![Firebase 앱 등록하기 5](https://user-images.githubusercontent.com/40654227/135788911-84060122-3125-485a-8111-1f29307d526a.jpg)

  
4.프로젝트에 **Firebase SDK** 를 추가한다.

![Firebase 앱 등록하기 8](https://user-images.githubusercontent.com/40654227/135789279-dbc97abc-ac2f-4717-8c47-2fd7575231d7.jpg)

  
4-1.**build.gradle(Project)** 에 아래와 같이 추가한다.  
`classpath 'com.google.gms:google-services:4.3.10'`

![Firebase 앱 등록하기 6](https://user-images.githubusercontent.com/40654227/135789018-3e3ec6b0-9102-457f-8b99-21e0b4f8b7f8.jpg)

  
4-2.**build.gradle(Module)** 에 아래와 같이 추가한다.  
`id 'com.google.gms.google-services'`  
`implementation platform('com.google.firebase:firebase-bom:28.4.1')`

![Firebase 앱 등록하기 7](https://user-images.githubusercontent.com/40654227/135789187-c2b2188f-86d8-4ea3-bfba-5852ed001e2d.jpg)

  
4-3.프로젝트 gradle을 동기화 시켜준다.

5.  **Firebase** 에 앱 등록 설정 완료  
    ![Firebase 앱 등록하기 9](https://user-images.githubusercontent.com/40654227/135789887-2e8b950b-3506-41b8-918d-35469a426364.jpg)
