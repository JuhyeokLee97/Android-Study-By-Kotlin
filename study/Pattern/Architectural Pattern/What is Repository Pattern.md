# Repository Pattern 이란

## 개요
[Repository Pattern in Android](https://medium.com/swlh/repository-pattern-in-android-c31d0268118c)
MVVM 아키텍쳐에서 Repository Pattern을 구현하는 것을 정리해보도록 한다..

우리는 Android 에서 **MVVM** 패턴을 사용하면 깨끗하고 좀 더 구조화 된 코드들을 만들 수 있다는 것을 안다.
그 이유는 **MVVM**은 각각의 컴포넌트들을 각각의 역할에 따라 분리할 수 있기 때문이다.

간단하게 **MVVM**은 3가지 주요 컴포넌트 **Model**-**View**-**ViewModel** 로 구성되어 있는데, **View** 는 데이터를 보여주고 사용자와의 상호작용을 관리한다. **ViewModel**은 **View**에서 필요로 하는 데이터를 처리하는 부분으로 데이터를 요청하고 보내는 것을 담당한다. 그리고 **Model**은 데이터를 저장하고 비지니스 로직을 처리한다.

<img src="https://media.geeksforgeeks.org/wp-content/uploads/20201002215007/MVVMSchema.png" align="center"/>

하지만 문제는 만약 내가 **MVVM** 패턴에 맞게 프로젝트를 구현 했다면, 특정 데이터(local or remote database)에 접근하는데 있어서 몇몇 중복되는 코드들이 있을 것이다.
예를 들면 Retrofit을 이용하여 외부 DB에서 데이터를 읽어온다면, 아래와 같은 코드를 **ViewModel**에 구현해야 했다.
``` kotlin
val book = liveData {
	val bookDAO = Retrofit.getClient().create(BookDAO::class.java)
	val book = bookDAO.getBook()
	emit(book)
}
``` 
### 발생 배경
**비즈니스 로직**은 프로그램의 핵심이 되는 요소이며, 비즈니스 로직을 잘 짜야 원하는 결과를 올바르게 도출할 수 있다. 이때 비즈니스 로직은 보통  **데이터베이스나 웹서비스 등의 데이터 저장소**에 접근하게 되는데 이 과정에서 여러 문제가 발생할 수 있다. 주로  _중복되는 코드, 오류를 발생할 가능성이 있는 코드, 오타, 비즈니스 로직 테스트의 어려움_  등이 있다.  

이에 따라 몇 가지 요구사항이 발생하는데,

(1) 비즈니스 로직과 데이터 레이어를  **분리**해야 하고

(2) 중앙 집중 처리 방식을 통해  **일관된 데이터와 로직**을 제공해야 한다는 것이다.

[참고](https://4z7l.github.io/2020/11/24/repository-pattern.html)

---

[참고](https://4z7l.github.io/2020/11/24/repository-pattern.html) 
### 해결책

### Repository Pattern 이란

### 참고
[[Android] Repository Pattern - Heeg's Log](https://heegs.tistory.com/90)
[[안드로이드] Repository 패턴에 대한 고찰](https://vagabond95.me/posts/android-repository-pattern/)
[[Android]MVVM Repository 예제 - velog](https://devvkkid.tistory.com/196)
