# ViewModel 생성 방법 

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
#### ViewModel 생성
``` kotlin
class MainActivity : AppCompatActivity(){
    ...
    val viewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    ...
}
```

`ViewModelProvider(this@MainActivity)`: 파라미터로 MainActivity(View)를 전달함으로써 해당 ViewModel의 lifecycle은 MainActivity를 따른다.
`get(MainViewModel::class.java)`: MainActivity의 Lifecycle를 따르는 ViewModel **MainViewModel**를 생성한다.

### 방법2: `by ViewModels()`

### 방법3: `by activityViewModels()`

### 방법4: `ViewModelProvider(application: Application)`

## 인자가 있는 ViewModel 객체 생성
### 방법1: `Factory Class`
