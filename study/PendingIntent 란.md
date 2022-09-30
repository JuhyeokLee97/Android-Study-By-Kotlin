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
