
## Application Class란
<p>

전역적으로 앱의 상태를 유지하기 위한 기본 클래스이다.
우리는 Application을 상속받아 우리만의 `ex). MApplication`을 구현할 수 있다. 단, 사용하기 위해서는 `AndroidManifest.xml`의 <application> 태그의 속성인 “android:name”의 값을 우리가 지정한 클래스 이름(`MApplication`)으로 추가해야한다.  마지막으로 <strong>Application() 클래스는 다른 클래스들보다 가장 먼저 생성된다.</strong>

</p>

## Application Class는 가장 먼저 객체화 된다!!
<p>

이러한 특징에 전역적으로 사용할 수 있어서 Application()을 상속받아 나만의 객체를 많들면 다양하게 활용할 수 있는 것 같다.

가장 대표적으로는 <strong>첫 번째 Activity를 만들기 전에 실행해야 하는 특수 작업</strong>이 될 수 있다.


다음에는 Kotlin으로 Application을 활용한 예제를 정리한 내용이다.

</p>

## Background vs Foreground 실행(Push 알림, Background vs Foreground)
<p>
  
  Push 알림을 구현하게 되면, Background 상태에서의 액션과 Foreground 상태에서의 액션을 구분해야할 때가 있다.
  예를 들어 Foreground 상태에서는 앱의 특정 Activity를 실행시킬 수 있지만, Background 상태에서는 Splash 화면을 보여준 이후에 특정 Activity로 이동하도록 구현해야할 수도 있다. 이때 앱의 상태값을 확인하여 분기처리 할 수 있는데, Application 클래스를 상속받는 클래스를 통해 상태값을 관리할 수 있다.
  
</p>

#### MyApplication.kt: Application, Livecycleobser 상속
<p>

  `Application 상속`: Application Class 사용을 위해 상속 받아 구현한다.
  `LifecycleObserver 상속`: 앱의 상태 값(`Foreground/Background`)을 관리하기 위해 상속 받아 구현한다.

</p>  

``` kotlin
class MyApplication : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() { isForeground = false }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() { isForeground = true}
    
    companion object {
        var isForeground = false
    }
}
```

<p>
  
  ``ProcessLifecycleOwner.get().lifecycle.addObserver(this)``를 통해 앱의 생명주기 옵저빙을 추가한다.
  그리고 ``isForeground`` 변수를 통해 앱의 ``Foreground/Background``상태 값을 구분하여 로직을 처리할 수 있다.

</p>
  
