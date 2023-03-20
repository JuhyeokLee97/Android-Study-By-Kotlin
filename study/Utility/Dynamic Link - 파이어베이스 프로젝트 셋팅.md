# Dynamic Link - 파이어베이스 프로젝트 셋팅

## 개요

프로젝트 셋팅이 귀찮을 수도 있겠지만, 제가 포스팅하던 날을 기준으로 셋팅하는 방법을 이미지 하나 하나 첨부해서 설명되어있으니 천천히 따라해주시면 금방 마무리 될 것입니다~!
저도 처음에 귀찮아서 잘 정리되어 있는 블로그를 참고하여 하려고 여기저기 짧은 블로그를 찾아 다녔지만, 그냥 하나 잡고 잘 따라하면 되더라구요...ㅎㅎ;

## 1. 파이어베이스 프로젝트 생성 in 파이어베이스 콘솔
Firebase Dynamic Link를 생성하고 사용하기 위해서는 [[파이어베이스 콘솔]](https://console.firebase.google.com/)에서 프로젝트를 생성하고 
Dynamic Link 관련된 셋팅과 어플리케이션의 프로젝트에 대한 세팅도 해주어야 합니다.

## 1. 프로젝트 생성

<p>
  
<img width=1000
     src="https://user-images.githubusercontent.com/40654227/226161968-3ed1d7ab-cc85-4e38-b1b2-27581056171f.png"/>

[[파이어베이스 콘솔]](https://console.firebase.google.com/)에 접속하면 위와 같은 화면을 볼 수 있습니다.</br>
위 화면에서 빨깐색 네모 <strong>[프로젝트 추가]</strong>를 클릭하셔서 프로젝트를 생성하실 수 이 있습니다.

</p>

<p>
  
<img width=1000
     src="https://user-images.githubusercontent.com/40654227/226162208-538a364e-6f21-4ead-b4c3-add14d567612.png"/>

  [프로젝트 추가]버튼을 누르면 위와 같은 화면을 맞이하게 될 것입니다.</br>
  그러면 제가 입력한 <strong>"Dynamic-Link_Sample"</strong>과 같이 파이어베이스에서 관리할 프로젝트 이름을 작성해주시고 <strong>[계속]</strong>버튼을 누르셔서 다음 단계로 넘어가시면 됩니다.

  
</p>
  
  
<p>
  
  <img width=1000
       src="https://user-images.githubusercontent.com/40654227/226162369-224ad8d8-94f3-45e2-b6f8-c3ebad298477.png"/>
  
  이전 단계에서 프로젝트명을 잘 입력하셨다면, 위와 같은 화면을 보실 수 있습니다.</br>
  참고로, 저는 해당 파이어베이스 프로젝트(Dynamic-Link-Sample)를 샘플로만 사용하려고 하기 때문에 <strong>"이 프로젝트에서 Google 애널리틱스 사용 설정(권장)</strong>을 해제하고 프로젝트를 만들었습니다.
  
</p>


<p>
  
  <img wdith=1000
       src="https://user-images.githubusercontent.com/40654227/226162544-290b20e2-7286-4a50-b068-0ded1ce01599.png"/>

  마지막으로 위와 같은 화면을 보신다면 <strong>파이어베이스 프로젝트 만들기를 성공하신 겁니다!!</strong></br>
  <strong>[계속]</strong> 버튼을 눌러주시고 다음으로 <strong>파이어베이스 프로젝트를 셋팅</strong>하도록 하겠습니다.

  
</p>

## 2. 파이어베이스 프로젝트 셋팅

이제는 파이어베이스 프로젝트에 제가 만든 Android Project를 셋팅하도록 하겠습니다.</br>
당연히 제가 만든 프로젝트를 파이어베이스 프로젝트에 연결하지 않아도 알아서 되면 말이 안되겠죠~?

<p>
  <img width=1000
       src="https://user-images.githubusercontent.com/40654227/226286730-f5aa5b80-da7d-4334-8666-c80e34abb8b4.png"/>

  
  위(프로젝트 생성)에서 생성한 파이어베이스 프로젝트에 들어오면 위와 같은 화면을 볼 수 있습니다.</br>
  저는 Android Application에서 사용할 것이기 때문에 안드로이드 로고를 클릭하여 프로젝트에 제 앱을 추가하도록 하겠습니다.
  
</p>

<p>

  <img width=1000
       src="https://user-images.githubusercontent.com/40654227/226287646-a33371da-6bbf-42d9-8c43-2a559177db2c.png"/>
  

  안드로이드 로고를 클릭하시면 위와 같이 <strong>[앱 등록]</strong> 내용을 확인하실 수 있습니다.</br>
  저는 제가 미리 생성해둔 Android 프로젝트의 <strong>[패키지 이름](com.devgeek.dynamiclinksample)</strong>과  앱 닉네임을 입력하였습니다.</br>
  여러분도 파이어베이스 프로젝트에 앱을 연결시키기 위해서는 여러분의 프로젝트의 <strong>[패키지 이름]</strong>을 입력하시면 됩니다.</strong></br>
  그리고 <strong>[앱 등록]</strong> 버튼을 누르시고 <strong>[구성 파일 다운로드 후 추가]</strong>으로 넘어가도록 하겠습니다.
  
</p>


<p>
  
  <img width=500
       src="https://user-images.githubusercontent.com/40654227/226289499-5e31fbc9-bf56-424c-8595-6472647e6bf2.png"/>

  <strong>[구성 파일 다운로드 후 추가]</strong>에서는 <strong>'google-service.json'</strong> 파일을 제 Android 프로젝트에 첨부해서 제 프로젝트가 파이어베이스에서 만든 프로젝트를 연동하는 것을 진행합니다.</br>
  그래서 <strong>[google-service.json 다운로드]</strong> 버튼을 클릭하셔서 해당 파일을 다운로드 받아주십니다.</br>
  다음으로 다운로드한 파일을 Android Studio에서 아래 이미지와 같이 프로젝트 뷰로 전환한 다음에 <strong>[app]</strong> 디렉토리에 넣어주시면 됩니다.

  <img width=300
       src="https://user-images.githubusercontent.com/40654227/226289855-d53b8b07-824d-4903-9328-a695e14296c6.png"/>
       
</p>

<p>
  
  <img width=400
       src="https://user-images.githubusercontent.com/40654227/226292785-79f31936-9aea-46f2-8a33-2e0aa14b1f65.png"/>
  
  <strong>[Firebase SDK 추가]</strong>만 잘 마무리하면 파이어베이스 프로젝트와 로컬에 있는 제 프로젝트를 잘 연결시킬 수 있습니다.</br>
  위 이미지처럼 (1)에서 설명하듯이 프로젝트 수준 Gradle 파일(`Gradle Scripts > build.gradle(project:..`)에 `buildScript {...}`내용을 추가합니다.</br>
  그리고 (2)에서 설명하듯이 모듈 수준 Gradle 파일(`Gradle Script > build.gradle(Module:..`)에 `plugins {..}`를 같게 작성해줍니다.
  
  
</p>
