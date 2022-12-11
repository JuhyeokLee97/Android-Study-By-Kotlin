# Fragment 생명주기

## Fragment
<p>

- `Fragment`는 앱의 UI의 재사용 가능한 부분을 나타낸다. 
- `Fragment`는 <strong>자체 레이아웃</strong>을 정의 및 관리하고 <strong>자체 수명 주기</strong>를 보유하며 <strong>자체 입력 이벤트</strong>를 처리할 수 있다. 
- `Fragment`는 자체적으로 수행될 수 없으며 activity 또는 다른 fragment에서 호스팅 되어야 한다.

</p>

## Fragment lifecycle
<p>

각각의 `Fragment` 객체는 각자의 생명주기를 가지고 있다. 사용자가 앱을 사용할  때, `fragment`의 생명주기는 추가, 제거 및 화면에 들어가거나 나올 때 다양한 상태로 전환된다.

</p>

<p align="center">

<img src="https://developer.android.com/images/guide/fragments/fragment-view-lifecycle.png" width=400/>

</p>

### onCreate()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onCreate()|-|

- Fragment를 생성하면서 넘겨준 값들이 있다면, 여기서 변수에 할당하면 된다.
- <strong>UI(View)는 초기화 할 수 없다.</strong>

### onCreateView()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onCreateView()|INITIALIZED|

- Fragment에서 UI를 그릴 때 호출되는 콜백 함수이다.
- `LayoutInflate`를 수행하는 곳이다.
- View 객체를 얻을 수 있어, View들을 초기화 할 수 있다.
- <strong>반환 값으로 정상적인 Fragment View 객체를 제공해야지만 View Lifecycle(INITIALIZED)가 생성된다.</strong>

### onViewCreated()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onViewCreated()|INITIALIZED|

- `onCreateView()`를 통해 반환된 View 객체는 `onViewCreated()`의 인자로 전달된다.
- `onCreateView()`를 통해서 View Lifecycle 값이 `INITIALIZED` 되었기 때문에 <strong>View의 초기값 설정, LiveData Observing, Adapter 할당은 여기서 해주는 것이 적절하다.</strong>

### onViewStateRestored()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onViewStateRestored()|CREATED|

- fragment의 view들이 모두 생성된 이후에 실행된다.
- 이전의 view의 state 값들이 저장된다.
- View Lifecycle 상태 값: `INITIALIZED` -> `CREATED`

### onStart()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|STARTED|onStart()|STARTED|

- Fragment가 사용자에게 보여질 수 있을 때 호출된다.
- View Lifecycle 상태 값: `CREATED` -> `STARTED`

### onResume()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|RESUMED|onResume()|RESUMED|

- 사용자와 Fragment가 상호작용 할 수 있는 상태일 때 호출된다.
- <strong>`onResume()`이 호출되지 않은 시점에서는 입력을 시도하거나 포커스를 설정하는 등의 작업을 임의로 하면 안된다.</strong>
- View Lifecycle 상태 값: `STARTED` -> `RESUMED`

### onPause()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|STARTED|onPause()|STARTED|

- 사용자가 Fragment를 떠나기 시작했지만 Fragment는 여전히 visible일 때 onPause()가 호출된다.
- View Lifecycle 상태 값: `RESUMED` -> <strong>`STARTED`</strong>


### onStop()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onStop()|CREATED|

- Fragment가 더이상 화면에 보여지지 않게 되면 onStop()이 호출된다.
- 부모 액티비티(프래그먼트)가 중단될 때, 상태가 저장될 때 호출된다.
- View Lifecycle 상태 값: `STARTED` -> `CREATED`

### onDestoryView()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|CREATED|onDestoryView()|DESTROYED|

- 모든 exit animation, transaction이 완료되고 Fragment가 화면으로부터 벗어났을 경우 호출된다.
- 가비지 컬렉터에 의해 수거될 수 있도록 Fragment View에 대한 모든 참조가 제거되어야 한다.
- View Lifecycle 상태 값: `CREATED` -> `DESTROYED`

### onDestroy()
|Fragment Lifecycle|Fragment Callback| View Lifecycle|
|:--:|:--:|:--:|
|DESTROYED|onDestroy()|-|

- Fragment가 제거되거나, FragmentManager가 destroy 됐을 경우, `onDestroy()` 함수가 호출된다.
- Fragment Lifecycle의 끝을 알린다.


#### 참고 자료
> [의외로 잘 모르는 Fragment의 Lifecycle](https://readystory.tistory.com/199) </br>
> [프래그먼트 생명주기](https://velog.io/@evergreen_tree/Android-%ED%94%84%EB%9E%98%EA%B7%B8%EB%A8%BC%ED%8A%B8-%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0)
