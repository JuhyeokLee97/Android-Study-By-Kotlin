<!-- # 프래그먼트 간 공유 ViewModel -->

# [Android] Shared ViewModel in Kotlin

## 개요

### [Fragment 간 데이터 공유](https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing)
 - 앱을 개발하게 되면 하나의 **Activity**에서 데이터 공유(통신)이 필요한 복수개의 **Fragment**들이 사용되는 경우는 흔히 볼 수 있다.
 - 예를 들어 하나의 화면에서 위아래 2분할 형태로 두개의 Fragments를 사용한다고 했을 때, 하나의 Fragment에서는 유저는 아이템 리스트 중 **선택**할 수 있고, 다른 하나의 Fragment에서는 **선택**된 아이템을 보여주는 경우가 있다.
 - 위와 같은 경우, 2개의 Fragment들은 `interface description`을 정의해야하고, 해당 Activity는 두 Fragment를 같이 바인딩해야하기 때문에 예시는 쉬워보이지만 간단한 문제가 아니다.

Notice that both fragments retrieve the activity that contains them. That way, when the fragments each get the  [`ViewModelProvider`](https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider), they receive the same  `SharedViewModel`  instance, which is scoped to this activity.

This approach offers the following benefits:

-   The activity does not need to do anything, or know anything about this communication.
-   Fragments don't need to know about each other besides the  `SharedViewModel`  contract. If one of the fragments disappears, the other one keeps working as usual.
-   Each fragment has its own lifecycle, and is not affected by the lifecycle of the other one. If one fragment replaces the other one, the UI continues to work without any problems.

### Shared ViewModel

<p>

- **Jetpack.ViewModel**을 사용하면서 우리는 fragments or activities 간에 데이터를 공유할 수 있다. 
- **ViewModel**을  공유하기 위해서는 fragment들에서 같은 **ViewModel**을 이용하면 된다. 그리고 해당 **ViewModel**에 정의되어있는 변수 또는 함수에 자유롭게 접근해서 사용할 수 있다.
</p>
