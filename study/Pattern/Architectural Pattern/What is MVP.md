# MVP 패턴이란?
**Model**, **View**, **Presenter**로 구성된 아키텍쳐 패턴이다.

## 등장 배경
처음에 안드로이드 개발을 시작하는 사람이라면 앱에서 사용되는 (비지니스)로직들이 모두 Activity(Fragment)에 작성할 것이다. 
이런 개발 접근 방식은 `Activity` 가 거의 모든 UI와 데이터 처리가지 하게 된다.
나아가 이런 개발 방식은 유지보수와 확장을 어렵게 만든다.
유지보수, 가독성, 확장성 그리고 리팩토링에 유연하게 대처하기 위해서 개발자들은 각각의 코드들이 좋은 레이어단위로 구분되는 걸 선호한다.

아키텍쳐 패턴을 적용함으로써, 우리는 코드를 분리할 수 있다.
MVP(Model-View-Presenter) 아키텍쳐는 많이 사용되는 아키텍쳐 패턴 중 하나이고 프로젝트를 조직할 때도 용이하다.

MVP(Model-View-Presenter)는 전통적인 MVC(Model-View-Controller) 아키텍쳐 패턴의 대체안으로 나타났다.
만약 MVC를 아키텍쳐 패턴으로 사용한다면 다음과 같은 단점이 있다.

- 대부분의 중요한 비지니스 로직이 Controller에서 구현된다. 앱을 개발하면서 이 파일은 점점 더 커지고 코드를 유지하기가 어려워진다.
- 데이터 처리와 UI 처리가 밀접하게 연결되어 있기 때문에 Controller와 View 레이어는 같은 Activity(Fragment)에서 구현될 수 밖에 없다. 이로 인해서 앱을 버그나 수정사항이 생겼을 때 대처하기가 어렵다.
- 유닛 테스트하기가 어렵다.

**MVP**의 등장배경은 MVC 디자인 패턴에서는 UI(View)와 비지니스 로직이 의존적이여서 수정이나 기능추가 등이 어려운 점을 해결할 수 있는,
 즉 UI(**View**)와 비지니스 로직(**MODEL**)을 분리하고 그 사이를 관리하는 **Presenter**로 구성된 **MVP** 디자인 패턴이 나왔다.
 
MVP 패턴은 위와 같은 MVC의 단점을 해결하고 더 쉬운 방법으로 프로젝트 구조를 관리할 수 있게한다.
MVP가 아키텍쳐 패턴으로 많이 사용되는 이유는 모듈성과 테스트 가능성 그리고 클린하고 유지보수 쉽게 할 수 있는 코드베이스를 제공하기 때문이다.

## MVP 구성요소
MVP 패턴은 위와 같은 MVC의 단점을 해결하고 더 쉬운 방법으로 프로젝트 구조를 관리할 수 있게한다.
MVP가 아키텍쳐 패턴으로 많이 사용되는 이유는 모듈성과 테스트 가능성 그리고 클린하고 유지보수 쉽게 할 수 있는 코드베이스를 제공하기 때문이다.
다음은 MVP를 구성하고 있는 중요한 3가지 요소에 대한 설명이다.

### Model: 데이터를 저장하는 레이어
   - 비지니스 로직 처리를 담당한다.
   - DB, Netwrok 통신을 담당한다.
### View: UI 레이어
   - 데이터의 시각화를 담당한다.
   - 사용자의 액션을 받아 Presenter에 알려준다.
### Presenter: Model과 View 사이의 매개체
   - Model로부터 데이터를 가져와서 UI에 보여줄 수 있도록 데이터 가공한다.
   - View의 상태를 관리한다.
   - 사용자로부터 발생한(View Action) 액션을 받는다.

## MVP 아키텍쳐 특징
1. 각 레이어간의 통신(View-Presenter, Presenter-Model)은 interface를 통해서 이루어진다.
2. 하나의 Presenter 클래스는 오직 하나의 View만 관리한다. 즉, View와 Presenter 관계는 1:1이다.
3. Model과 View는 절대 서로 의존성을 갖지 않는다.
#### 참고 블로그
[MVP (Model View Presenter) Architecture Pattern in Android with Example](https://www.geeksforgeeks.org/mvp-model-view-presenter-architecture-pattern-in-android-with-example/)
