# MVP Basic Sample
## 개요

### [MVP란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/Pattern/Architectural%20Pattern/What%20is%20MVP.md)
Model, View, Presenter로 구성된 아키텍쳐 패턴이다.</br>
<img src="https://user-images.githubusercontent.com/40654227/186917489-5100c0f5-4795-4064-bc38-acde358cbe62.png" width=600 align="center"/>

### 앱 설명
MVP 패턴을 사용하여 `PLUS` 버튼과 `MINUS` 버튼을 눌러 숫자가 보여지는 `TextView`에 증가(감소) 된 값을 변경시킵니다.

### 앱 실행 화면
<img src="https://user-images.githubusercontent.com/40654227/186933560-452a3fd7-cac2-4350-907c-8c2749d67461.gif" height=300/>

### 프로젝트 구조
<img src=""/>

## Code
### MyContract.kt
Contract란...

``` kotlin
interface MyContract {

    interface View{
        /** when changed number is being fetched, set on number_text_view */
        fun showNumberText(number: Int)
    }

    interface Presenter{
        /** when the plus_button is clicked, this method is called */
        fun plusNumber()
        /** when the minus_button is clicked, this method is called */
        fun minusNumber()
    }

    interface Model{
        interface OnCalculatedCallback{
            /** Once this Model's `plusNumber` or `minusNumber` complete its execution, this `onCalculated` is called */
            fun onCalculated(number: Int)
        }
        fun plusNumber(onCalculatedCallback: OnCalculatedCallback)
        fun minusNumber(onCalculatedCallback: OnCalculatedCallback)
    }

}
```

### MyModel.kt: MyContract.Model를 상속받아 구현한 Model 클래스

``` kotlin
class MyModel : MyContract.Model {
    private var number = 0
    /** when user clicks on the `plus` button, this method invoke */
    override fun plusNumber(onCalculateCallback: MyContract.Model.OnCalculatedCallback) {
        number++
        onCalculateCallback.onCalculated(number)
    }

    /** when user clicks on the `minus` button, this method invoke */
    override fun minusNumber(onCalculateCallback: MyContract.Model.OnCalculatedCallback) {
        number--
        onCalculateCallback.onCalculated(number)
    }
    
}
```

### MyPresenter.kt: MyContract.Presenter와 MyContract.Model.OnCalculatedCallback을 상속받아 구현한 Presenter 클래스
``` kotlin
class MyPresenter(_view: MyContract.View, _model: MyContract.Model): MyContract.Presenter, MyContract.Model.OnCalculatedCallback {
    private val view = _view
    private val model = _model

    /** when the `plus` button is clicked, get the number from model and show the number by `this(onCalculated method)` */
    override fun plusNumber() {
        model.plusNumber(this)
    }
    /** when the `minus` button is clicked, get the number from model and show the number by `this(onCalculated method)` */
    override fun minusNumber() {
        model.minusNumber(this)
    }

    override fun onCalculated(number: Int) {
        view.showNumberText(number)
    }
}
```

### MainActivity.kt: MyContract.View를 상속받아 구현한 View 
``` kotlin
class MainActivity : AppCompatActivity(), MyContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MyPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MyPresenter(this@MainActivity, MyModel())

        binding?.let {
            it.btnMinus.setOnClickListener { presenter.minusNumber() }
            it.btnPlus.setOnClickListener { presenter.plusNumber() }
        }
    }

    override fun showNumberText(number: Int) {
        binding.tvNumber.text = number.toString()
    }
}
```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="20dp"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.AppCompatButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:text="MINUS"
        android:id="@+id/btnMinus"/>

    <androidx.appcompat.widget.AppCompatButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:text="PLUS"
        android:id="@+id/btnPlus"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/tvNumber"
        android:text="0"
        android:textSize="20sp"
        app:layout_constraintStart_toEndOf="@id/btnMinus"
        app:layout_constraintEnd_toStartOf="@id/btnPlus"
        app:layout_constraintTop_toTopOf="@id/btnMinus"
        app:layout_constraintBottom_toBottomOf="@id/btnMinus"
        />

</androidx.constraintlayout.widget.ConstraintLayout>
```
