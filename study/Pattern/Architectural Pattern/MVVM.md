# MVVM Architecture Pattern in Android(Kotlin)

## MVVM 이란
## MVVM Layouer
<p>

- <strong>Model:</strong> Model Layer는 데이터 원본의 추상화를 담당한다. 
- <strong>View:</strong> View Layer는 ViewModel에게 유저의 `action`을 알려주는 역할을 담당한다. 그리고 `ViewModel`를 구독하여 UI Data를 관리하고 `application logic`을 담당하지 않는다.
- <strong>ViewModel:</strong> `View`와 관련된 데이터의 흐름을 관장한다. 추가적으로 `Model`과 `ViewModel`을 연결한다.

	<img src="https://media.geeksforgeeks.org/wp-content/uploads/20201002215007/MVVMSchema.png"/>
	
MVVM 패턴은 MVP(Model - View - Presenter) 디자인과 비슷한 점이 있는데, Presenter의 역할을 ViewModel이 한다는 것이다. 하지만 MVP 패턴의 단점이 MVVM을 통해서 아래와 같이 해결되었다.

1. ViewModel은 어떠한 View도 참조하고 있지 않다.
2. View와 ViewModel 사이의 관계는 N:1이 된다. 즉 여러 View에서 하나의 ViewModel을 사용할 수 있다.
3. 더 이상 View에서 Data를 업데이트 하라는 트리거가 필요하지 않다.
  
</p>

## MVVM 프로젝트 셋팅
<p>



</p>
