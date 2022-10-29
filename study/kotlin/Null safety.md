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

ㅏ
