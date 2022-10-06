# `let` 범위 함수

## 범위함수란
범위함수란 영어로 Scope Funtion으로 불린다. 함수명 Scope Funtion에서 알 수 있듯이 이 범위 내에 함수를 만드는 것이다. 
범위(Scope)를 만들어서 그 안에서 람다식을 이용해 로직을 구현할 수 있다.

## let 범위함수
``` kotlin
fun <T, R> T.let(block: (T) -> R): R
```

`let` 범위함수에 대해서 Kotlin 공식 문서에서는 다음과 같이 설명하고 있다.
> **The context object** is available as an argument(`it`). **The return value** is the lambda result.

공식 문서에서는 `let` 함수에 간단하게 설명되어있다. 
자기 자신을 **argument**(`it`) 파라미터로 받는 코드 블록을 생성하고 코드 블록 안에서의 람다의 결과를 반환하는 것이 `let`이다.

그리고 `let` 함수의 사용방법에 따라 4가지로 나누어 설명되어있다.

### 첫 번째

> `let` can be used to invoke one or more functions on results of call chains.
> For example, the following code prints the results of two operaions on a collction:

#### let 사용 전
``` kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
val resultList = numbers.map { it.lengt }.filter { it > 3 }
println(resultList)   // [5, 4, 4]
```

#### let 사용 후
``` kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let {
    print(it)   // [5, 4, 4]
    // and more function calls if needed
}   
```



**간단하게 말하면 람다식을 사용할 블록 안에 복수개의 함수들을 호출할 수 있다는 것이다.**
## Android App 개발에서 사용되는 부분 추가하자.

### 두 번째
> if the code block contains a single function with `it` as an argument, 
> you can use the method reference(`::`) instead of the lambda

``` kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let(::println)    // [5, 4, 4]
```

## 설명 추가
## Android App 개발에서 사용되는 부분 추가하자.

[공식문서 - Scope function:let](https://kotlinlang.org/docs/scope-functions.html#let)
