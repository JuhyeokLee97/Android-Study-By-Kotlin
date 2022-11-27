# context vs requireContext

## 개요
**Fragment**를 사용할 때면 Context를 필요로 하는 경우가 자주 발생한다.
아주 간단한 예로는 문자열 파일에 접근한다거나, 다른 Activity로 이동하는 등 다양하게 사용될 수 있다.

### Context 란

## context in Fragment
개인이 생성한 **Fragment**에서 `context`를 호출하면 내부적으로는 **Fragment Class**에 정의되어 있는 `public Context getContext()` 함수가 호출된다.
`getContext()` 함수를 살펴보면 다음과 같다.
``` java
@Nullable
public Context getContext() {
    return mHost = null ? null : mHost.getContext();
}
```
`mHost`는 00 이고, 00이 `null`이 아닌 경우 그것의 context를 반환한다.
하지만 해당 함수는 **Nullable**로 `mHost`가 `null`인 경우, `null`을 반환한다. 그러므로 해당 함수를 호출해 사용하는 부분(Fragment)에서는 **Nullable**을 고려한 로직을 작성해야 한다.
`null`인 상황을 고려하지 않고 개발하게 되면 이후에 `null`이 들어오면 앱이 죽을 수도 있다.


## requireContext in Fragment
개인이 생성한 **Fragment**에서 `requireContext()`를 호출하면 내부적으로 **Fragment Class**에 정의되어 있는 `public final Context requireContext()` 함수가 호출된다.
`requireContext()` 함수를 살펴보면 다음과 같다.
``` java
@NonNull
public final Context requireContext() {
    Context context = getContext();
    if (context == null) {
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }
    return context;
}
```

위에서 설명한 `getContext()` 함수를 이용해서 context 값을 읽어온다.
하지만 해당 함수는 **NonNull**로 context가 `null`인 경우, 자체적으로 `IllegalStateException`을 발생시킨다.
그러므로 자체적으로 **Nullable**에 방어할 수 없게 된다.

## context VS requireContext
`requireContext()`에서도 `context`를 이용해서 context 값을 반환하지만, `context`는 **Nullable**하기 때문에 개발자가 방어코드를 작성할 수 있지만, 
`requireContext()`는 **NonNull**이기 때문에 개발자가 방어코드를 작성할 수 없어서 context가 `null`인 경우 `IllegalStateException`을 발생시키고 앱이 죽게된다.

결론적으로 context를 읽어오는 방식은 같지만, 개발자 입장에서는 **Nullable** 방어를 통해 앱이 비정상적으로 죽지 않게 하기 위해서는 context를 읽어올 때에는 `requireContext()`보다는 `context`를 사용하는게 안전한 것 같다.
