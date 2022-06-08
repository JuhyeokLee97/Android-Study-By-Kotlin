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

FCM에서는 2가지 타입의 메시지를 보낼수 있다.
 - <strong>Notification Message:</strong>  `display message`라고도 불리는데, FCM SDK 에서 자동으로 관리된다.
 - <strong>Data Message:</strong> Client App 에서 따로 관리된다.

</p>
<p>

`Notification Message`는 미리 정의된 `user-visible` 키 셋을 갖는다. 반면에 `Data Message`는 사용자가 정의한 `custom key-value`를 갖는다. `Notification Message`는 선택적인 `data payload`를 갖을 수 있다. 

</p>
## Data vs Notification 차이
