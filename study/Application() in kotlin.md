
## Application Class란
<p>

전역적으로 앱의 상태를 유지하기 위한 기본 클래스이다.
우리는 Application을 상속받아 우리만의 `ex). MApplication`을 구현할 수 있다. 단, 사용하기 위해서는 `AndroidManifest.xml`의 <application> 태그의 속성인 “android:name”의 값을 우리가 지정한 클래스 이름(`MApplication`)으로 추가해야한다.  마지막으로 <strong>Application() 클래스는 다른 클래스들보다 가장 먼저 생성된다.</strong>

</p>

## Application Class는 가장 먼저 객체화 된다!!
<p>

이러한 특징에 전역적으로 사용할 수 있어서 Application()을 상속받아 나만의 객체를 많들면 다양하게 활용할 수 있는 것 같다.

가장 대표적으로는 <strong>첫 번째 Activity를 만들기 전에 실행해야 하는 특수 작업</strong>이 될 수 있다.


다음에는 Application을 활용한 예제를 정리한 내용이다.

</p>

### [Background vs Foreground 실행(Push 알림, Background vs Foreground)]()

### [kakao Login Init]()
### [FCM device token 확인]()

#### 참고 블로그
> [Application Class란? + 활용](https://uroa.tistory.com/43)</br>
> [Application 클래스란?](https://onlyfor-me-blog.tistory.com/374)
