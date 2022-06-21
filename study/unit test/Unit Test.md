# Android Unit Test

### 개요

<p>

모바일 앱 개발을 하는데 있어, MVVM, Jetpack, KMM(Kotlin Multiplatform) 등 다양한 트렌드를 볼 수 있다. 그 중 테스트 기술은 요즘 중요하고 선호하는 것 중 하나이다. 또한 테스트 기술은 개발에 있어여 중요한 요소이다. 그래서 면접에 있어서 `Unit Test`에 대한 지식은 필수가 되기 시작됐다. 

</p>

### Unit Test Case가 필요해?
<p>

위 질문에 대한 답변은, 개발하는데 있어 대부분 필요하다. 
제품 요구 사항을 충족하기위해 빠른 개발 주기를 가져가면서 대부분 `Unit Test` 범위를 벗어난 개발을 하거나, 고려하지 않은 개발을 하고는 한다. 하지만 이제는 많은 기업들이 더욱 완성도 있는 상품(앱)을 개발하기 위해 그리고 더 좋은 코드로 작성하기 위해 `Unit Test`를  작성하며 개발하는 접근 방식을 따르고 있다.

</p>

<p>

`Unit Test`를 고려하고 작성하면서 개발을 진행한다면, 개발자들로 하여금 버그를 해결하는데 도움이 될 것이고, 버그로 인해 기존에 작성한 코드를 수정하는데 있어 이슈가 발생한 곳을 찾는데 용이하여 코드 수정에도 도움이 된다.

</p>

### Unit Test란?
<p>

`Unit Test`란 작성한 코드를 작은 부분으로 나누어 테스트하는 것을 말한다. 우리가 만드는 프로그램은 대게 여러 `class`들을 갖는다. 더 나아가 클래스 내에서는 `Business Logic`을 함수로 나누어 개발을 한다. `Unit`이란 코드의 작은 조각이라고 이야기하는데 이 `Unit`은 `method`, `class` 또는 `component`가 될 수 있다. <strong>`Unit Test`의 목적은 각각의 `Unit`의 Logic을 확인하는 것이다.</strong>

</p>


#### 참고 블로그
> [Android Unit Testing Basics](https://betterprogramming.pub/android-unit-testing-basics-3e7075a432a1)
