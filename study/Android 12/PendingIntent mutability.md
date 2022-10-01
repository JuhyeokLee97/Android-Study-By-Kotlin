# PendingIntent Mutability
안드로이드 앱의 targetSdk 버전이 `12`라면 무조건 `PendingIntent` 객체에 **mutability**를 명시해야한다. 

## PendingIntent 란

``` kotlin
class PendingIntent: Parcelable
```

공식문서에는 다음과 같이 설명되어있다.
> A description of an Intent and target action to perform with it. Instances of this class are created with `getActivity`, `getActivities`,
 `getBroadcast` and `getService`; the returned object can be handed to other applications so that they can perform the action you described on your
beharlf at a later time...

자세한 설명은 [PendingIntent 란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/PendingIntent%20%EB%9E%80.md)을 참고.

## Mutability
`PendingIntent`에 **mutabiliy**를 명시하기 위해서는 `PendingIntent.FLAG_MUTABLE` or `PendingIntent.FLAG_IMMUTABLE` flag를 사용하면 된다.

만약 `PendingIntent`를 생성하는데 **mutability**를 명시하지 않았다면 시스템에서 `IllegalArgumentException`을 던지고 아래와 같은 **Locat Message**가 보일 것이다.
```
PACKAGE_NAME: Targeting S+ (version 31 and above) requires that one of \
FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.

Strongly consider using FLAG_IMMUTABLE, only use FLAG_MUTABLE if \
some functionality depends on the PendingIntent being mutable, e.g. if \
it needs to be used with inline replies or bubbles.
```

## Immutable PendingIntent 만들기
대부분의 경우 아래와 같이 **immutable** `PendingIntent`을 만들것이다. `PendingIntent`가 **imuutable**한 경우에는 다른 앱에서 해당 intent를 수정할 수 없다.
``` kotlin
val pendingIntent = PendingIntent.getActivity(applicationContext, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

```

[참고](https://developer.android.com/guide/components/intents-filters#DeclareMutabilityPendingIntent)

