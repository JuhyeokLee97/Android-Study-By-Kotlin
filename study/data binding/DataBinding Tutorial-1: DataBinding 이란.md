# Android DataBinding: Tutorial -1: DataBinding

## 개요

<p>

이 **DataBinding Tutorial**에서는 레이아웃의 구성 요소와 직접 상호 작용하고, 값을 할당하고, **Binding Adapter**를 사용하여 `View`에서 발송되는 이벤트를 처리하는 방법을 다룬다.

</p>

<p>

**DataBinding**은 Android Jetpack의 일부이다. DataBinding 라이브러리는 레이아웃의 구성요소(`View`)를  데이터와 쉽게 바인딩 할 수 있는 방법을 제공한다. 구체적으로는 프로그래밍 방식으로 데이터를 뷰에 바인딩하는 대신에 **선언 형식을 이용해서 바인딩 할 수 있다.**

</p>

### 구현 예제 앱 기능

<p>

`SpaceX rockets`, `crew`, `dragons` and `capsules`의 정보를 나타내는 4가지 리스트를 보여준다.

</p>

## 왜 DataBinding을 사용해야해?

<p>

기본적으로 코드와 레이아웃 사이의 상호작용은 ``Activity`` 또는 `Fragment`에서 `findViewById()`를 통해사 사용된다.

최근에는 **ViewBinding**을 사용한 경우, UI 컴포넌트를 참조하고 함수와 리스너를 사용해서 코드와 레이아웃 간의 상호작용을 할 수 있다. 하지만 **ViewBinding**을 이용하더라도 UI 컴포넌트에 접근하고 수정하는데 있어 많은 코드가 발생한다. 

하지만 Android Jetpack 에서 제공하는 **DataBinding 라이브러리**를 사용한다면 레이아웃 파일에서 직접 컴포넌트와 상호작용할 수 있다.

</p>

## DataBinding 이해하기
<p>

**DataBinding**을 이용하면 **Binding expression**을 통해서 해당 View에 값을 할당하기도 하고 이벤트를 붙일 수 있다. Binding expression의 하나의 방식으로는 `android:text"@{viewModel.value}"` 있다. 이 expression은 해당 레이아웃에게 `android:text` 값으로 `viewModel.value`값을 가지라는 표현이다. 레이아웃에 코드 로직을 바인드 하는데 있어 `Object`, `ViewModel` 또는 각각의 `variables`를 사용할 수 있다.

</p>

<p>

**Binding Adapter**는 우리의 코드를 레이아웃에 바인드 할 수 있도록 하는 역할을 한다. **기본 바인딩 어댑터**는 `setter`를 호출하여 `view`에 값을 할당하는 반면, **Advanced 바인딩 어댑터**는 데이터 바인딩을 수행하는 동안 실행할 로직을 추가한다.
아래 다이어그램은 데이터 바인딩 프로세스에서 바인딩 어댑터의 위치를 잘 나타낸다.  

</p>

<p align = "center">
<img src="https://koenig-media.raywenderlich.com/uploads/2021/10/DataBinding.png"/>
</p>


#### 참고
> [Advanced Data Binding in Android: Binding Adapters](https://www.raywenderlich.com/28513564-advanced-data-binding-in-android-binding-adapters#toc-anchor-001)
