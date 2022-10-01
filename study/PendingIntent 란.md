<!-- 
# PendingIntent 란

Intent는 AAC(Android Application Component)를 실행하기 위해 안드로이드 시스템에게 알려주는 역할을 하는 객체이다.
이러한 Intent에 대해 추가적인 기능을 더 붙이고, Intent가 정의된 어플리케이션이 아닌, 다른 어플리케이션에서 Intent를 실행하도록 하는 것이 **PendingIntent**이다.

## PendingIntent.Flag를 이해하기 위한 개념
PenddingIntent 자체는 안드로이드 시스템에서 관리되는 토큰에 대한 레퍼런스 값을 가지고 있는 객체이다. 이 토큰은 PendingIntent에 포함된 Intent의 동작을 수행하기 위한 데이터를 갖고 있다.
``` kotlin
PendingIntent(
  intent: Intent,
  tokenRef: Long
)
```

PendingIntent 객체를 만드는 순간 안드로이드 시스템에 등록되기 때문에, 이를 등록한 프로세스가 종료된 이후에도 다른 프로세스에서 PendingIntent를 실행할 수 있다.

그리고 나중에 PendingIntent를 만들었던 프로세스가 재 실행되어 동일한 PendingIntent를 만들게 되면 동일한 토큰 레퍼런스 값을 갖게 된다.
참고로 PendingIntent가 동일하다고 판다하는 조건은 same operation, same intent action, data, categories,components, same flags 이다.

이러한 특징 때문에 **Intent의 extra 데이터만 변경하여 다른 PendingIntent를 만드는 것은, PendingIntent 자체에 대한 변화가 없기 때문에 결국 처음 시슴템에 등록한 토큰값을 갖은 PendingIntent를 재 호출하는 것이 된다.

-->

# PendingIntent 란


### 어디서 PendingIntent를 사용했나?
나는 PendingIntent를 **Notification**을 구현하는 중 학습하는 중 처음으로 사용하게 되었다.
FCM을 통해서 푸시 알림을 구현하는데 FCM 사용에 대해서만 초점을 맞추어서 당시에는 Notification에 들어가는 PendingIntent에는 크게 신경쓰지 않고 구글링하여 사용했었다.
단지 PendingIntent는 단어 뜻 그대로 **대기하고 있는 Intent**로써 Notification을 클릭했을 때, 동작하는 Intent 명시 정도로만 이해했다.


하지만 최근에 Android 12(targetSdk 31) 대응에 대해 학습하던 중, PendingIntent의 (im)mutablility를 명시하지 않으면 안된다는 내용을 보고 PendingIntent에 대해서 알아보게 되었다.

## PendingIntent 란?

``` kotlin
class PendingIntent: Parcelable
```

공식문서에는 다음과 같이 설명되어있다.
> A description of an Intent and target action to perform with it. Instances of this class are created with `getActivity`, `getActivities`,
 `getBroadcast` and `getService`; the returned object can be handed to other applications so that they can perform the action you described on your
beharlf at a later time...

내가 이해하기에는 PendingIntent는 Intent에 대한 설명과 수행되어야 하는 동작을 포함하고 있는 객체인 것 같다.

PendingIntent에 대해 구글링 중 좋은 정리된 글을 찾았는데 거기에서의 내용을 참고하여 정리하면 PendingInten의 개념은 다음과 같다.

**Pending**이란 사전적으로 '보류', '임박'이라는 뜻을 가지고 있다. `PendingIntent`는 가지고 있는 `Intent`**를 당장 수행하지 않고 특정 시점에 수행하도록 하는 특징**을 갖는 객체이다.
**특정 시점**이라고 하면 보통 해당 앱이 구동(사용)되고 있지 않을 때를 이야기하는 것 같다.

예를 들면 우리가 흔히 사용하는 카카오톡이 있다. 내가 유튜브(**특정 시점**)를 시청 중, 카카오톡을 사용하고 있지 않음, 친구에게 카카오톡 메시지가 왔다고 푸시 알림이 왔다. 나는 답을 하기 위해서 직접
`카카오톡 -> 채팅 목록 -> 친구 채팅방` 형태로 수행하지 않고 간단히 `푸시 알림 클릭`만으로 앞에서 말한 플로우를 직접 수행하지 않고 친구와의 채팅방에 입장할 수 있다.

위의 예시가 사용자 입장에서는 매우 당연하게 느껴지겠지만, 여기에 `PendingIntent`의 특징이 숨겨져있다.</br>
공식문서에서는 다음과 같이 설명하고 있다.
> By giving a PendingIntent to another applicaiton, you are granting it the right to perform the operation you have specified as if the other application was yourself (with the same permissions and identity)

`PendingIntent`을 생성한 주체(어플리케이션)가 다른 어플리케이션에게 주체의 `PendingIntent`의 `Intnet`를 수행할 수 있도록 권한을 부여한다는 것이다. 
다시 말해서 `PendingIntnet`를 카카오톡 푸시 알림에 첨부함으로써, 현재 카카오톡이 켜져있지 않은 상황에서도 푸시 알림을 눌러 카카오톡에서 만든 `Intent`를 수행할 수 있는 것이다.


### PendingIntent 사용은 어디서?

`PendingIntent`의 사용은 아래 3가지가 대표적인 경우인 것 같다.
- `Notification`으로 `Intent` 작업 수행
- 바탕화면 위젯에서 `Intent` 작업 수행
- `AlarmManager`를 통해 지정된 시간에 `Intent` 작업 수행

### PendingIntent 생성은 어떻게?

Component의 유형마다 `PendingInten` 생성자를 호출하는 방식이 다르다.

#### Activity
``` kotlin
PendingIntent.getActivity(context: Context, requestCode: Int, intent: Intent, flags: Int)
```

#### Service
``` kotlin
PendingIntent.getService(context: Context, requestCode: Int, intent: Intent, flags: Int)
```

#### BroadcastReceiver
``` kotlin
PendingIntent.getBroadcast(context: Context, requestCode: Int, intent: Intent, flags: Int)
```

