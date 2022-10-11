# Service 란
Android **Service**에 대해서 공식 문서에서는 다음과 같이 설명되어있다.
> A `Service` is an **application component** that can perform long-running operations in the background.
It does not provide a user interface. Once started, a service might continue running for sometime, even after the user switches to another applicaiton.
Additionally, a component can bind to s a service to interact with it and even perform interprocess communication(IPC).
For example, a service can handle network transactions, play music, perform file I/O, or interact with a content provider, all from the background

내가 이해하기로는 **서비스**란 백그라운드에서 오랜 시간동안 실행될 수 있는 **앱 컴포넌트**이다. 

**서비스**의 특징으로는 일반적으로 UI를 제공하지 않고, 만약 실행되면 사용자가 다른 앱으로 전환하더라도 백그라운드에서 실행될 수 있다.
추가적으로 컴포넌트를 서비스에 바인딩함으로써 상호작용할 수 있고 프로세스 간 통신(IPC)도 수행할 수 있다.
대표적인 예로는 백그라운드에서 숭행되는 네트워크 통신, 노래 재생, 파일 다운로드 등등 있다.

> **Caution**: A service runs in the main thread of its hosting process; 
> the service does **not** create its own thread and does **not** run in a separate process unless you specify otherwise.
> You should run any blocking operations on a separate thread within the servic to avoid Application Not Responding(ANR) errors.

## 서비스의 종류
서비스에는 다음과 같이 3가지 종류가 있다.
### 포그라운드(Foreground)
유튜브 뮤직을 생각하면 **포그라운드 서비스**를 이해하기 쉽다.

**포그라운드 서비스**란 백그라운드에서 실행되는 동작이 사용자에게 알릴 내용이 있는 서비스라고 생각하면 된다.
포그라운드 서비스는 사용자가 앱을 직접적으로 사용하고 있지 않더라도 계속해서 동작하는데, 그래서 사용자에게 해당 서비스가 동작하고 있다는 것을 인지시키기 위해 무조건 **Notification**을 노출시켜야한다.
유튜브 뮤직을 보면 해당 앱으로 음악을 실행하고 앱을 닫거나 스위칭 하더라도 **Notification**을 통해 뮤직 트랙을 확인할 수 있다.

[포그라운드 서비스에 대한 상세 설명](https://developer.android.com/guide/components/foreground-services)

[포그라운드 서비스 기본 예제]()

### 백그라운드(Background)

> A background service performs an operation that isn't directly noticed by the user.
> **백그라운드 서비스**는 사용자에게 즉각적으로 알리지 않아도 되는 동작을 수행한다.
> For example, if an app used a service to compat its storage, that would usually be a background service.

### 바운드(Bound)


[참고](https://developer.android.com/guide/components/services)
