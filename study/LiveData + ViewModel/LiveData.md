# [LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=eng)
## 개요
 - ``LiveData``는 관찰 가능한 데이터 홀더 클래스이다.
 - 관찰 가능한 일반 클래스와 달리 ``LiveData``는 수명 주기를 인식하는데 즉, Activity, Fragment, Service 등 다른 앱 구성요소의 ``LifeCycle``고려한다.
 - <strong>LifeCycle 을 인식하기 때문에 ``LiveData``는 ``active lifecycle`` 상태에 있는 앱 구성요소 관찰자만 업데이트한다.</strong>

## LiveData의 이점
### UI와 Data 상태값 일치
<p>

LiveData는 Observer Pattern을 따르기 때문에, 데이터가 변경될 때 ``Observer`` 객체에 알린다.</br>
개발자는 구독된 객체가 변경되었을 때, UI를 업데이트하는 방식으로만 고려하면 된다. <strong>그러므로 데이터가 변경될 때 마다 UI를 어떻게 업데이트 시킬 지 고려할 필요가 없다.</strong>

</p>

### 메모리 누수가 없음
Observer는 ``Lifecycle`` 객체에 결합되어 있고 해당 객체의 생명 주기가 끝나면 <strong>자동으로 삭제</strong>가 된다.

### 중지된 Activity로 인한 에러(비정상 종료) 없음
Activity가 back-stack 에 있을 때를 비롯하여 Observer의 Lifecycle이 ``inactive`` 상태일 때 어떠한 LiveData의 이벤트도 받지 않는다.

### Lifecycle을 더 이상 수동으로 처리하지 않음
UI components는 관련 데이터(``LiveData``)를 관찰하기만 할 뿐이지 Observation을 중지하거나 다시 시작하지 않는다.</br>
LiveData는 관련된 ``lifecycle status``의 변경을 관찰하기 때문에 자동으로 관리해준다.

### 언제나 최신의 데이터
만약 lifecycle이 비활성화 되면,  다시 활성화 될 때 최신의 데이터를 받아온다. 예를 들어 백그라운드(background)에 있었던 Activity는 포그라운드(foreground)로 돌아온 직후 최신 데이터를 받는다.

### 자연스러운 configuration 변경
기기 회전과 같은 configuration이 변경으로 인해 Activity 또는 Fragment가 다시 생성되면 사용 가능한 최신의 데이터를 받는다.

### 리소스 공유
앱에서 시스템 서비스를 공유할 수 있도록 ``Singleton pattern``을 사용하는 ``LiveData`` 객체를 확장하여 시스템 서비스를 래핑할 수 있다. ``LiveData``가 시스템 서비스에 한 번 연결되면, 해당 리소스가 필요한 모든 Observer가 ``LiveData``를 관찰할 수 있다.</br>
참고). [LiveData 확장](https://developer.android.com/topic/libraries/architecture/livedata?hl=ko#extend_livedata)

## LiveData 사용법
### 실행 영상

<p align="center">
<img src="https://user-images.githubusercontent.com/40654227/170381041-75617335-f013-4ed9-a872-0798212522c5.gif"
     width="270" height="500"/>

</p>
### 개요
1. 특정 객체의 데이터를 보유할 ``LiveData``의 객체를 생성한다.(이 작업은 일반적으로 ``ViewModel``클래스 내에서 이루어진다.)</br></br>

2. ``onChanged()`` 함수를 정의하는 ``Observer`` 객체를 만든다. 이 함수는 ``LiveData`` 객체가 보유한 데이터 변경 시 발생하는 작업을 제어한다.(일반적으로 Activity or Fragment 같은 ``UI Controller``에 ``Observer``객체를 만든다.)</br></br>

3. ``observe()`` 함수를 사용하여 ``LiveData`` 객체에 ``Observer``객체를 연결한다. ``Observe()`` 함수는 ``LifecycleOwner`` 객체를 사용한다. 이렇게 하면 ``Observer`` 객체가 ``LiveData`` 객체를 구독하여 변경사항에 대한 알림을 받는다.(일반적으로 Activity or Fragment 같은 ``UI Controller``에 ``Observer`` 객체를 연결한다.</br></br>

- ``LiveData`` 객체에 저장된 값을 변경하면 ``LifecycleOwner``가 active 상태인 경우 등록된 모든 관찰자에게 트리거가 간다.
- LiveData는 UI Controller에서 변경사항을 구독할 수 있게한다. 즉  LiveData 객체의 데이터가 변경되면, 구독하고 있는 UI를 자동으로 업데이트 시킬 수 있다.

### LiveData Object 만들기

<p>
 
 LiveData는 ``Collection``를 구현하는 ``List``와 같은 객체를 비롯하여 모든 데이터와 함께 사용할 수 있다.</br>
 LiveData 객체는 일반적으로 ``ViewModel`` 객체 내에 저장된다.
 
</p>

### Code
``` kotlin
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NameViewModel: ViewModel() {
    
    // Create a LiveData with a String
    val currentName: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    
    // Rest of the ViewModel...
}
```

><strong>참고</strong></br>
>UI를 업데이트 하는데 사용되는 <strong>LiveData</strong> 객체는 꼭 Activity or Fragment가 아닌 <strong>ViewModel</strong>에 저장되어야한다.</br></br>
><strong>이유</strong></br>
> - Activity와 Fragment 클래스가 너무 커지거나 복잡해지지 않게 하기 위해서. 즉, UI Controller에서는 데이터 표시만을 담당하고 데이터 상태를 관리하지 않게 하기 위해서이다.</br>
> - LiveData 객체를 고유의 Activity or Fragment에 제한하지 않고 분리하여, 앱의 Configuration이 변겨오디더라도 LiveData 객체를 유지할 수 있다.</br>

### LiveData Object 관찰하기
<p>

 대부분 앱 lifecycle 중에서 ``onCreate()`` 에서 ``LiveData`` 객체 관찰을 시작하기에 적절하다.</br></br>
 
 <strong>이유</strong></br>
 - Activity or Fragment에서의 ``onResume()`` 함수에서 중복 호출하지 앟도록 하기 위해서이다.
 - Activity or Fragment의 상태 값이 ``Active``되는 즉시 보여질 수 있는 데이터가 포함되도록 하기 위함이다.
</br></br>
일반적으로 LiveData는 데이터가 변경될 때 ``active observer``에게만 업데이트를 전달한다. </br>
Observer가 ``inactive``에서 ``active`` 상태로 변경될 때에도 업데이트를 전달받는다.</br>
또한 Obserer가 ''inactive''에서 ''active'' 상태로 변경되는게 두 번인경우, 마지막으로 ``active`` 상태가 된 이후 값이 변경된 경우에만 업데이트를 받는다.

</p>

#### Code
``` kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // create NameViewModel
        viewModel = ViewModelProvider(this).get(NameViewModel::class.java)

        // Create the observer with updates the UI
        val nameObserver = Observer<String>{ newName ->
            nameTextView.text = newName
        }

        // Observe the LiveData
        // Passing in this activity as the LifecycleOwner and the observer
        viewModel.currentName.observe(this, nameObserver)
    }
}
```


### LiveData Objects 업데이트하기

<p>

LiveData에는 저장된 데이터를 public 하게 업데이트하는 방법은 없다.</br></br>
``MutableLiveData`` 클래스는 ``setValue(T)`` 그리고 ``postValue(T)``를 public 하게 제공한다.</br></br>
그래서 LiveData의 데이터를 수정하기 위해서는 ''setValue(T)`` 또는 ``postValue(T)``를 사용해야한다.</br></br>
대게 ``MutableLiveData``는 ``ViewModel``에서 정의(사용)하고 ``Observer``에게는 immutable한 ``LiveData``를 준다.</br>

</p>

``` kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create NameViewModel
        viewModel = ViewModelProvider(this).get(NameViewModel::class.java)

        // Create the observer with updates the UI
        val nameObserver = Observer<String>{ newName ->
            binding.nameTextView.text = newName
        }

        // Observe the LiveData
        // Passing in this activity as the LifecycleOwner and the observer
        viewModel.currentName.observe(this, nameObserver)

        // Set Listener ClickEvent To Button For Update LiveData
        binding.button.setOnClickListener {
            val anotherName = "John Doe"
            viewModel.currentName.value = anotherName
        }
    }
}
```

위 코드에서는 ``setValue(T)``방식을 사용했는데, 그 결과 ``numberObserver``가 ``John Doe`` 값과 함께 ``onChanged()``함수를 호출한다.</br></br>
이 예시에서는 버튼이 눌렸을 때만을 보여주는 거지만, ``setValue(T)`` 또는 ``postValue(T)``는 네트워크 요청 또는 DB 로드 완료에 대한 응답으로 ``name``을 업데이하기 위해 호출될 수도 있다.</br></br>
어떠한 경우에도 ``setValue()`` 또는 ``postValue()``를 호출하면 Observer의 트리거가 발동되면서 UI가 업데이트 된다.</br></br>
><strong>참고</strong></br></br>
>기본 쓰레드에서 <strong>LiveData</strong> 객체를 업데이트하려면 ``setValue(T)`` 함소를 호출해야한다.</br>
>코드가 worker 쓰레드에서 실행된다면 대신 ``postValue(T)`` 메서드를 사용하여 <strong>LiveData</strong> 객체를 업데이트할 수 있다.
>
### Coroutines + LiveData
