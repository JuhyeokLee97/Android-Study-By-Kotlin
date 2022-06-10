# MVVM Architecture Pattern in Android(Kotlin)

## MVVM 이란
## MVVM Layouer
<p>

- <strong>Model:</strong> Model Layer는 데이터 원본의 추상화를 담당한다. 
- <strong>View:</strong> View Layer는 ViewModel에게 유저의 `action`을 알려주는 역할을 담당한다. 그리고 `ViewModel`를 구독하여 UI Data를 관리하고 `application logic`을 담당하지 않는다.
- <strong>ViewModel:</strong> `View`와 관련된 데이터의 흐름을 관장한다. 추가적으로 `Model`과 `ViewModel`을 연결한다.

	<img src="https://media.geeksforgeeks.org/wp-content/uploads/20201002215007/MVVMSchema.png"/>
	
MVVM pattern has some similarities with the MVP(Model — View — Presenter) design pattern as the Presenter role is played by ViewModel. However, the drawbacks of the MVP pattern has been solved by MVVM in the following ways:

1.  ViewModel does not hold any kind of reference to the View.
2.  Many to 1 relationship exist between View and ViewModel.
3.  No triggering methods to update the View.
</p>

## MVVM 프로젝트 셋팅
<p>



</p>


## 참고자료
> [MVVM (Model View ViewModel) Architecture Pattern in Android](https://www.geeksforgeeks.org/mvvm-model-view-viewmodel-architecture-pattern-in-android/)
