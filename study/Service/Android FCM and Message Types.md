# Android FCM and Message Types
> [Android FCM 프로젝트 셋팅]()
> [Android FCM 기본 예제]()
> [Android FCM 클릭 이벤트]()

### 들어가며
Android App 개발 프로젝트를 진행하는 중, Notification 개발을 맡았다. 앱이 `Foreground` 일 때는 내가 원하대로 Notification 클릭 이벤트(특정 화면 호출|이동)가 동작했지만, 앱의 상태가 `Background`인 경우에는 Notification은 잘 왔지만 클릭 이벤트가 동작하지 않았다. `Foreground`상태를 처리하는 예제는 무수히 많았지만, `Background`에서 이벤트처리 관련해서는 정보 찾는데 어려웠어서 `Background`에서의 Notifiaction 클릭 이벤트 처리를 위한 기본 배경 지식을 정리하려 한다. 


## FCM 이란
<p>

Firebase Cloud Messaging (FCM)은 </br>

</p>

## Message Types
<p>

FCM에서는 2가지 타입의 메시지가 있다.
 - <strong>Notification Message:</strong>  `display message`라고도 불리는데, FCM SDK 에서 자동으로 관리된다.
 - <strong>Data Message:</strong> Client App 에서 따로 관리된다.

</p>
<p>

`Notification Message`는 미리 정의된 `user-visible` 키 셋을 갖는다. 반면에 `Data Message`는 사용자가 정의한 `custom key-value`를 갖는다. `Notification Message`는 선택적인 `data payload`를 갖을 수 있다. 

</p>

## Notification Message
<p>

프로그래밍적으로 Notification Message를 보내기 위해서는 Admin SDK 또는 FCM protocols를 이용해야 하는데, notification에 미리 정의된 `key-value` 옵션을 필수적으로 설정해준 후 보내야한다.</br>
예를 들면 아래 JSON 포맷의 코드는 Notifcation Message의 예시이다. 아래와 같이 알림을 보내게 되면 앱의 사용자는 타이틀에는 `Portugal vs. Denmark`가 내용으로는 `great match!`가 전달된다.

``` json
{
  "message":{
    "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
    "notification":{
      "title":"Portugal vs. Denmark",
      "body":"great match!"
    }
  }
}
```

</p>
<p>
앱의 상태가 `Foreground`이라면 `onMessageReceived()` 메서드에서 데이터 값들을 커스텀 하여 알림을 만들 수도 있다.</br>
하지만 `Background`라면 `onMessageReceived()` 메서드가 <strong>호출되지 않고</strong> 안드로이드에서 기본적으로 알림을 만든다.

</p>



### 참고 블로그(문서)
> [[Android]FCM Background Push ](https://solly29.github.io/android/Android-Firebase/)</br>
> [Firebase FCM Notification Message Documents](https://firebase.google.com/docs/cloud-messaging/concept-options#notifications)</br>
