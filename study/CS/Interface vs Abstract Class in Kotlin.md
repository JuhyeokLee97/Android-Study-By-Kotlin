# ☕ Interface vs Abstract Class in Kotlin

## 1. 개요

Java 8 이후와 Kotlin 시대의 객체지향 설계에서 `interface`와 `abstract class`의 경계는 이전보다 모호해졌다. Kotlin에서는 인터페이스도 기본 구현(default implementation)을 가질 수 있기 때문이다. 하지만 두 개념은 여전히 **설계 의도와 역할**에서 중요한 차이를 가진다.

---

## 2. 핵심 차이 요약

| 구분               | Interface                        | Abstract Class              |
| ---------------- | -------------------------------- | --------------------------- |
| **주요 목적**        | 역할(계약)을 정의하다                     | 공통 상태와 로직을 제공하다             |
| **상태(필드)**       | 가질 수 없다 (백킹 필드 없음)               | 가능하다 (`val/var` 가짐)         |
| **생성자**          | 없다                               | 있다 (`init`, 불변식 강제 가능)      |
| **상속 가능성**       | 다중 구현 가능하다                       | 단일 상속만 가능하다                 |
| **기본 구현**        | 가능하다 (Kotlin에서는 default body 지원) | 가능하다                        |
| **protected 멤버** | 불가능하다                            | 가능하다                        |
| **사용 예시**        | Repository, UseCase, Listener    | BaseViewModel, BaseActivity |

---

## 3. 상태 보유 차이

```kotlin
interface Clickable {
    val name: String
    fun click()
}

class Button(override val name: String) : Clickable {
    override fun click() = println("$name clicked!")
}

abstract class BaseButton(val name: String) {
    var clickCount: Int = 0

    fun click() {
        clickCount++
        println("[$name] clicked $clickCount times")
    }
}
```

* `interface`의 `val`은 **백킹 필드가 없어** 상태를 저장하지 않는다.
* `abstract class`는 실제 필드(상태)를 가질 수 있어 **공통 상태 관리**에 적합하다.

---

## 4. 다중 구현과 다이아몬드 문제

Kotlin은 **클래스 다중 상속은 금지**하지만 **인터페이스 다중 구현**은 허용한다.

```kotlin
interface A { fun greet() = println("Hello from A") }
interface B : A { override fun greet() = println("Hello from B") }
interface C : A { override fun greet() = println("Hello from C") }

class D : B, C {
    override fun greet() {
        super<B>.greet()
        super<C>.greet()
        println("Hello from D")
    }
}
```

출력 결과:

```
Hello from B
Hello from C
Hello from D
```

> 다중 상속으로 인한 모호성을 해결하기 위해 Kotlin은 `super<Interface>.method()` 문법을 강제한다.

---

## 5. Java 8 Default Method와의 관계

### 💡 도입 배경

* **Java 8**에서 `default` 메서드가 추가된 이유는 기존 인터페이스에 메서드를 추가하더라도 **기존 구현체들이 깨지지 않도록 (binary compatibility 유지)** 하기 위함이다.
* 공식 문서: [Oracle Java Tutorial - Default Methods](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)

### ✅ Java 예시

```java
public interface Loggable {
    default void log(String message) {
        System.out.println("Loggable: " + message);
    }
}

public interface ErrorHandler {
    default void log(String message) {
        System.out.println("ErrorHandler: " + message);
    }
}

public class Repository implements Loggable, ErrorHandler {
    @Override
    public void log(String message) {
        Loggable.super.log(message);
        ErrorHandler.super.log("Recovered after error");
    }
}
```

---

## 6. Binary Compatibility란?

> **Binary Compatibility**란 기존에 컴파일된 바이너리(.class)가 새로운 버전의 코드와 **재컴파일 없이도** 정상 동작하는지를 의미한다.

예를 들어:

```java
// v1
public interface UserRepository {
    void save(User user);
}

// v2
public interface UserRepository {
    void save(User user);
    default void delete(User user) {} // ✅ 기본 구현이 있으면 Binary Compatible
}
```

기존 `UserRepository` 구현체는 **다시 컴파일하지 않아도** 동작한다.

반면 기본 구현이 없다면:

```java
void delete(User user); // ❌ AbstractMethodError 발생
```

> 이게 바로 Java 8이 `default method`를 도입한 핵심 이유이다.

---

## 7. Kotlin에서 Default Implementation 사용 시 주의점

* Kotlin 인터페이스도 기본 구현을 가질 수 있지만, 내부적으로 **`DefaultImpls` 클래스**를 생성한다.
* Kotlin 컴파일러 버전이나 `-Xjvm-default` 설정에 따라 **binary compatibility** 문제가 발생할 수 있다.

따라서 라이브러리나 공용 모듈 설계 시:

* 인터페이스의 기본 구현을 추가할 때는 **하위 호환성에 유의**해야 한다.
* 가능한 경우 **새 인터페이스로 분리**하는 것이 안전하다.

---

## 8. 설계 시 선택 기준

| 질문                          | 선택                             |
| --------------------------- | ------------------------------ |
| 여러 역할을 섞어야 하는가?             | **Interface**                  |
| 공통 상태와 불변식을 관리해야 하는가?       | **Abstract Class**             |
| API 변경 시 호환성을 보장해야 하는가?     | **Interface (default method)** |
| 모든 구현체가 동일한 기본 동작을 가져야 하는가? | **Abstract Class**             |

---

## 9. 결론

`interface`는 **계약(Contract)**, `abstract class`는 **틀(Template)**에 가깝다.

> * 인터페이스는 “무엇을 할 수 있는가”를 정의한다.
> * 추상 클래스는 “어떻게 동작해야 하는가”를 부분적으로 제공한다.

Kotlin에서는 두 개념이 언어적으로 가까워졌지만, 설계 의도는 여전히 다르다.
**계약과 틀의 구분**, 그리고 **호환성에 대한 고려**가 좋은 설계의 핵심이다.
