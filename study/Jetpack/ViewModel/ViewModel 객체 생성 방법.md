# ViewModel 객체 생성 

## 개요

<p>

**ViewModel**은 Android Lifecycle aware 객체로, Lifecycle을 고려하여 UI 데이터를 저장하고 관리하도록 설계되어있다.
그렇기 때문에 ViewModel 객체는 `ViewModelProvider`를 통해서 생성하며, 직접 생성자를 호출하여 생성하지 않는다.
  
</p>

## 인자가 없는 ViewModel 객체 생성
### 방법1: `ViewModelProvider`
#### ViewModel
``` kotlin
class MainViewModel : ViewModel() {
    val result = MutableLiveData<String>("init value")
}
```
Activity에서 `ViewModelProvider`를 통해 아래와 같이 ViewModel 객체를 생성할 수 있다.
#### ViewModel 객체 생성
``` kotlin
class MainActivity : AppCompatActivity(){
    ..
    val viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    ..
}
```

`ViewModelProvider(this@MainActivity)`: 파라미터로 MainActivity(View)를 전달함으로써 해당 ViewModel의 lifecycle은 MainActivity를 따른다.
`get(MainViewModel::class.java)`: MainActivity의 Lifecycle를 따르는 ViewModel **MainViewModel**를 생성한다.

---

### 방법2: `by viewModels()`
`by ViewModels()`를 사용하면 `ViewModelProvider`를 사용하지 않고 ViewModel을 **지연 생성**할 수 있다.
#### ViewModel
``` kotlin
class MainViewModel : ViewModel() {
    val result = MutableLiveData<String>("init value")
}
```
Activity에서 `by viewModels()`를 통해 아래와 같이 ViewModel 객체를 생성할 수 있다.
#### ViewModel 객체 생성
``` kotlin
class MainActivity : AppCompatActivity(){
    ..  
    val viewModel: MainViewModel by viewModels()
    ..
}
```
`by`: 코트린의 **위임** 키워드를 활용하여 ViewModel 클래스를 연결한다. 
`by viewModels()`; 해당 ViewModel를 초기화하는 View(Activity or Fragment)의 Lifecycle을 따른다.

---

### 방법3: `by activityViewModels()`
`by activityViewModels()`는 **Fragment**에서만 사용 가능하다.
#### ViewModel
``` kotlin
class MainViewModel : ViewModel() {
    val result = MutableLiveData<String>("init value")
}
```
#### ViewModel 객체 생성
``` kotlin
class MainFragment: Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        ..
        val viewModel: MainViewModel by activityViewModels()
        ..
    }
}

```

`by activityViewModels()`: 해당 **Fragment**의 Root가 되는 Activity의 Lifecycle을 따른다.

## 인자가 있는 ViewModel 객체 생성
### 방법1: `Factory Class`
#### ViewModel + Factory
`ViewModel`은 따로 생성자가 없기 때문에, 인자가 있는 객체를 생성해야할 경우, `ViewModelProvider.Factory`를 정의해주어야 한다.
``` kotlin
class MainViewModel(string: String) : ViewModel() {
    val result = string
}

class MainViewModelFactory(private val string: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){   
            return MainViewModel(string) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
```

#### ViewModel 객체 생성
``` kotlin
class MainActivity : AppCompatActivity(){
    ..
    val viewModelFactory = MainViewModelFractory("result")
    val viewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(MainViewModel::class.java)
    ..
}
```
`ViewModelProvider(_, factory)`: ViewModel을 생성할 때, `ViewModelProvider`에 추가적으로 ViewModelProvider.Factory를 구현한 객체를 담아주면 된다.

<!-- [참고 블로그](https://codechacha.com/ko/android-jetpack-create-viewmodel/) -->
