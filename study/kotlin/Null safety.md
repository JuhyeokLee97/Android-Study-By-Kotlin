# Null Safety

## Nullable types and non-null types
Kotlin 타입 시스템은 **null 참조의 위험성**을 제거하는데 초점이 맞혀있다

Java를 포함하여 많은 프로그래밍 언어에서 가장 흔한 **pitfall** 중 하나는 `null` 값을 갖는 멤버에 접근함으로써 `null reference exception`
을 초래하는 것이다. Java 에서는 이런 경우를 `NullPointException` 이나 줄여서 `NPE`로 부른다.

Kotlin에서 `NPE(NullPointException)`이 발생하는 경우는 아래와 같다.

- `throw NullPointerException()`을 명시적으로 호출하는 경우.
- `!!`를 사용한 경우

...


Kotlin에서는 **타입 시스템**이 참조 변수의 `nullable` 형태를 판단한다. 다시 말해 해당 변수가 `null`를 갖을 수 있는지 또는 `null`값을 갖을 수 없는지를 판단한다. 예를 들어 설명한다면 일반적으로 `String` 타입의 변수는 `null`를 갖을 수 없다.

``` koltin
var a: String = "abc" // Regular initialization means non-null by default
a = null // Compile Error
```

위에서 `null` 값을 갖도록 허용하기 위해서는 직접 변수를 선언할 때 타입에 nullable 하다고 명시적으로 `String?`으로 선언해야한다.
``` kotlin
var b: String? = "abc"  // can be set to null
b = null // OK
print(b)
```

이제는 만약 `a`에 접근하게 되면 **NPE**를 발생시킬 위험성이 없음을 보장하기 때문에 자유롭게 `a`를 사용할 수 있다.
``` kotlin
val l = a.length
```

하지만 만약 `b`에 접근하려고 한다면 non-null이 보장되지 않기 때문에 `b`를 그냥 사용할 경우 컴파일러에서 에러를 나타낼 것이다.
``` kotlin
val l = b.length  // error: variable `b` can be null
```

하지만 우리는 `b`와 같은 변수나 객체에 접근할 수 있어야 한다.</br>
그래서 아래에서는 몇 가지 방법들을 설명하려고 한다.

## Checking for null in conditions
먼저 우리는 명시적으로 `b`가 `null`인지 체크할 수 있다.
``` kotlin
val l = i f (b != null) b.length else -1
```
그러면 컴파일러는 우리가 `null` 체크에 대한 정보를 트랙킹하여서 `b`가 non-null 인 경우 `b`의 `length`를 호출한다.
조금더 복잡하게 **nullable**에 대해 체크한다면 다음과 같이 할 수 있다.
``` kotlin
val b: String? = "kotlin"
if (b != null && b.length > 0) {
    print("String of length ${b.length}")
} else {
    print("Empty string")
}
```

## Safe calls
nullable한 변수에 접근할 수 있는 다른 방법은 안전한 호출(**safe call**) 연산자 `?.`를 사용하는 것이다.
``` kotlin
val a = "Kotlin"
val b: String? = null
print(b?.length)
print(a?.length)  // Unnecessary safe call
```

`print(b?.length)`에서 `b`가 `null`이기 때문에 **safe call** 연산자 `?.` 덕분에 `length`를 호출하지 않고 `b?`에서 `null`를 반환한다.


**safe call** 연산자는 체인형태에서 유용하다. 예를 들어 `Bob`라는 회사원이 있는데 그는 특정 부서(`department`)에 속해 있고 그 부서에는 부서장(`head`)이 있고 부서장의 이름(`name`)을 알아야 한다몀 다음과 같이 코드를 작성해야 할 것이다.
``` kotlin
bob?.department?.head?.name
```

이런 체인형태의 코드에서 만약 어느 한 속성값이 `null`이라면 `null`를 반환하게 된다.

만약 non-null인 경우에만 변수(객체)를 이용하여 연산을 수행한다면, 우리는 `let` 연산자와 함께 **safe call** 연산자 `?.`를 사용할 수 있다.
``` kotlin
val listWithNulls: List<String?> = listOf("Kotlin", null)
for (item in listWithNulls) {
    item?.let { println(it) } // prints Kotlin and ignores null
}
```

그리고 **safe call**은 변수에 값을 할당할 때, 좌항(저장될 변수)에서도 사용될 수 있다.
만약에 체인 형태로 된 리시버(변수)에 `null`를 포함하고 있다면 값 할당은 무시하게 된다. 우항에 있는 코드는 동작은 무시된다.
``` kotlin
// If either 'person' or 'person.department' is null, the function is not called
person?.department?.head = mansgersPool.getManager()
```


## Elvis Operator
우리가 nullable를 참조해야하는 경우, `b`처럼, 우리는 "`b`가 `null`이 아니라면 이것을 `null`이면 `non-null` 값을" 형태로 처리할 수 있다.
``` kotlin
val l: Int = if (b != null) b.length else -1
```

하지만 `if` 표현식을 사용하지 않는 대신에 우리는 ***Elvis Operator***(연산자) `?:`를 이용할 수 있다.
``` kotlin
val l = b?.length ?: -1
```

`?:` 연산자의 왼쪽에 있는 값이 `null`이 아니라면 ***Elivis*** 연산자는 왼쪽(`b.length`)에 있는 값을 반환한다.
반면에 왼쪽에 있는 값이 `null`이라면 오른쪽(`-1`)에 있는 값을 반환한다.
여기서 주의해야 할 것은 ***Elvis*** 연산자(`?:`) 우측에 나오는 표현식은 좌측에 있는 값이 `null`인 경우에만 연산된다.
























