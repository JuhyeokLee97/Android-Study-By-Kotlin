## MyContract.kt
``` kotlin
interface MyContract {

    interface View{
        fun showNumberText(number: Int)
    }

    interface Presenter{
        fun plusNumber()
        fun minusNumber()
    }

    interface Model{
        /** 계산이 완료 된 후, 실행되는 callback 함수 */
        interface OnCalculatedCallback{
            fun onCalculated(number: Int)
        }
        fun plusNumber(onCalculatedCallback: OnCalculatedCallback)
        fun minusNumber(onCalculatedCallback: OnCalculatedCallback)
    }

}
```

## MyModel.kt
``` kotlin
class MyModel : MyContract.Model {
    private var number = 0
    override fun plusNumber(onCalculateCallback: MyContract.Model.OnCalculatedCallback) {
        number++
        onCalculateCallback.onCalculated(number)
    }

    override fun minusNumber(onCalculateCallback: MyContract.Model.OnCalculatedCallback) {
        number--
        onCalculateCallback.onCalculated(number)
    }
    
}
```

## MyPresenter.kt
``` kotlin
class MyPresenter(_view: MyContract.View, _model: MyContract.Model): MyContract.Presenter, MyContract.Model.OnCalculatedCallback {
    private val view = _view
    private val model = _model

    override fun plusNumber() {
        model.plusNumber(this)
    }
    override fun minusNumber() {
        model.minusNumber(this)
    }

    override fun onCalculated(number: Int) {
        view.showNumberText(number)
    }
}
```

## MainActivity.kt
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

## activity_main.xml
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
