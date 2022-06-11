## Model + View + ViewModel

<p>

<img src="https://media.geeksforgeeks.org/wp-content/uploads/20201002215007/MVVMSchema.png"/>



</p>


- <strong>Model: </strong> 데이터 원본의 추상화를 담당한다.
- <strong>View:</strong>  ViewModel에게 유저의 `action`을 알려주는 역할을 담당한다. 그리고 `ViewModel`를 구독하여 UI Data를 관리하고 `application logic`을 담당하지 않는다.
- <strong>ViewModel:</strong> `View`와 관련된 데이터의 흐름을 관장한다. 추가적으로 `Model`과 `ViewModel`을 연결한다.


## MVVM 특징

<p>

- App의 생명주기 상태가 유지된다.
- UI 요소는 비즈니스 로직과 무관하다.
- 비즈니스 로직과 db 운영과의 종속성이 없다.
- 프로젝트를 이해하고 코드를 읽는 것이 쉽다.

</p>


## MVVM 장점
<p>

- 유지보수: 비즈니스 로직이 View와 독립적으로 돌아가기 때문에 수정보완이 빠르고 다음 버전을 신속하게 출시할 수 있다.
- 확장성: 새 코드를 교체하거나 추가하기 용이하다.
- 테스트 가능성: 핵심 로직같은 것들을 유닛 테스트 하기 쉽다.
- 

</p>

## MVVM 단점

<p>

- 작은 프로젝트(Simple UI)에서는 오버스펙이다.
- 너무 큰 프로젝트인 경우 <strong>ViewModel</strong>을 디자인하기 어렵다.
- 복잡한 <strong>data binding</strong>을 사용할 경우 Debugging이 어렵다.

</p>


#### 참고 블로그
> [Introduction to Model View View Model(MVVM)](https://www.geeksforgeeks.org/introduction-to-model-view-view-model-mvvm/)

