# Null Safety

## Nullable types and non-null types
Kotlin 타입 시스템은 **null 참조의 위험성**을 제거하는데 초점이 맞혀있다

Java를 포함하여 많은 프로그래밍 언어에서 가장 흔한 **pitfall** 중 하나는 `null` 값을 갖는 멤버에 접근함으로써 `null reference exception`
을 초래하는 것이다. Java 에서는 이런 경우를 `NullPointException` 이나 줄여서 `NPE`로 부른다.

