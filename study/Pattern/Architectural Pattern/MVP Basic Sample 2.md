# MVP Basic Sample

## 개요

### [MVP란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/Pattern/Architectural%20Pattern/What%20is%20MVP.md)

Model, View, Presenter로 구성된 아키텍쳐 패턴이다.</br>
<img src="https://user-images.githubusercontent.com/40654227/186917489-5100c0f5-4795-4064-bc38-acde358cbe62.png" width=600 align="center"/>

### 앱 설명
MVP 아키텍쳐 패턴을 사용하여 `명언보기` 버튼을 누르면 명언을 랜덤하게 보여준다. 
단 명언을 불러오는데 사용자 경험을 고려하여 실행중임을 명시하기 위해 **프로그래스바** 를 나타낸다.

### 앱 실행 화면
<img src="https://user-images.githubusercontent.com/40654227/187018130-91b3e9f0-7484-41b3-b486-ab3e388b6e2f.gif" height=600/>


## Code
### 프로젝트 구조

### build.gradle(:app): ViewBinding 세팅
``` xml
android {
    ...
    buildFeatures{
        viewBinding true
    }
    ...
}
```

### Contract.kt
Contract Interface는 각각의 레이어가 통신할 수 있도록 다시 말해서 `View`-`Presenter`과 `Presenter`-Model`간에 연결을 설계하기 위해서 필요하다. 
</br>

Contract Interface는 `Model`, `View` 그리고 `Presenter`에서 사용될 추상함수를 포함해야한다.

``` kotlin
interface Contract {
    interface View {
        /** when next random famous saying is being fetched, display progress bar */
        fun showProgress()
        /** when next random famous saying has been fetched, hide progress bar */
        fun hideProgress()
        /** string is set on famous_saying_text_view */
        fun setString(string: String)
    }

    interface Presenter {
        /** when the show_famous_saying_button is clicked, this method is called */
        fun onShowButtonClick()
    }

    interface Model{
        interface OnClickListener{
            /** Once this Model's `getNextFamousSaying` complete its execution, this `onClick` is called */
            fun onClick(string: String)
        }
        fun getNextFamousSaying(onClick: Contract.Model.OnClickListener)
    }
}
```

### FamousSayingModel.kt
``` kotlin
class FamousSayingModel : Contract.Model {
    private val arrayList = arrayListOf<String>(
        "세상에서 가장 현명한 사람은\n모든 사람으로부터 배우는 사람이다\n가장 사랑받는 사람은 칭찬하는 사람이다\n가장 강한 사람은 감정을 조절할 줄 아는 사람이다\n\n-탈무드-",
        "좋은 성과를 얻으려면\n한 걸음 한 걸음이 힘차고 충실하지 않으면 안 된다\n\n-단테-",
        "성공의 비결은 단 한 가지,\n잘 할 수 있는 일에 광적으로 집중하는 것이다\n\n-톰 모나건-",
        "하루에 3시간을 걸으면\n7년 후에 지구를 한 바퀴 돌 수 있다\n\n-사무엘 잭슨-",
        "진정으로 웃으려면 고통을 참아야 하며\n나아가 고통을 즐길 줄 알아야 한다\n\n-찰리 채플린-",
        "단순하게 살아라\n현대인은 쓸데없는 절차와 일 때문에\n얼마나 복잡한 삶을 살아가는가?\n\n-이드리스 샤흐-",
        "먼저 자신을 비웃어라\n다른 사람이 당신을 비웃기 전에\n\n-엘사 맥스웰-",
        "절대 어제를 후회하지 마라\n인생은 오늘의 나 안에 있고\n내일은 스스로 만드는 것이다\n\n-L.론허바드-"
        )

    /** when user clicks on the `show_famous_saying` button, this method invoke and take a delay of 1200 milliseconds to display new Famous Saying */
    override fun getNextFamousSaying(listener: Contract.Model.OnClickListener) {
        Handler().postDelayed({
            listener.onClick(getRandomString())
        }, 1500L)
    }

    /** get string randomly from the `arrayList` */
    private fun getRandomString(): String {
        val random = Random(System.currentTimeMillis())
        val pos = random.nextInt(arrayList.size)

        return arrayList[pos]
    }
}
```

### MainPresenter.kt
``` kotlin
class MainPresenter(mainView: Contract.View, famousSayingModel: Contract.Model): Contract.Presenter, Contract.Model.OnClickListener {
    private val view = mainView
    private val model = famousSayingModel

    /** when the `show_famous_saying` button is clicked, get string from model and display progress */
    override fun onShowButtonClick() {
        model.getNextFamousSaying(this)
        view.showProgress()
    }

    /** this method will display the string(famous saying) and then hide progress */
    override fun onClick(string: String) {
        view.setString(string)
        view.hideProgress()
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
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvHead"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="5dp"
        android:text="MVP Pattern Sample"
        android:textSize="30sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tvSubHeading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:text="오늘의 명언"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvHead" />

    <TextView
        android:id="@+id/tvFamousSaying"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textColor="@color/black"
        android:textSize="18sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@id/btnShowFamousSaying"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvSubHeading" />

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        app:layout_constraintBottom_toTopOf="@id/btnShowFamousSaying"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tvSubHeading" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnShowFamousSaying"
        android:layout_width="0dp"
        android:layout_height="56dp"
        android:layout_margin="20dp"
        android:background="#3F51B5"
        android:text="명언보기"
        android:textColor="@color/white"
        android:textSize="20sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding

    private val model = FamousSayingModel()
    private val presenter = MainPresenter(this, model)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShowFamousSaying.setOnClickListener { presenter.onShowButtonClick() }
    }

    override fun showProgress() {
        binding.progressBar.isVisible = true
    }

    override fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    override fun setString(string: String) {
        binding.tvFamousSaying.text = string
    }
}
```
