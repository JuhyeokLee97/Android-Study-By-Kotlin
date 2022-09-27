# 범위 함수(Scope Function)
[참고](https://www.digitalocean.com/community/tutorials/kotlin-let-run-also-apply-with)</br>
[참고](https://blog.yena.io/studynote/2020/04/15/Kotlin-Scope-Functions.html)

## Scope Function 이란

## let
``` kotlin
fun <T, R> T.let(block: (T) -> R): R
```
> Calls the specified function block with this value as its argument and returns its result.

`let` 함수는 매개변수화된 타입 T의 확장 함수이다. 자기 자신을 받아서 **R**을 반환하는 람다 식으로, 자기 자신 **T**를 입력을 받고 블럭 함수의 마지막 코드를 반환값으로 **R**을 반환한다. 여기서는 Person 클래스의 확장 함수로 사용되어 `person.let`의 형태가 가능해진다.

``` kotlin
val person = Person("", 0)
val resultIt = person.let {
    it.name = "James"
    it.age = 56
    it // (T)->R 부분에서의 R에 해당하는 반환값.
}

val resultStr = person.let {
    it.name = "Steve"
    it.age = 59
    "{$name is $age}" // (T)->R 부분에서의 R에 해당하는 반환값.
}

val resultUnit = person.let {
    it.name = "Joe"
    it.age = 63
    // (T)->R 부분에서의 R에 해당하는 반환값 없음
}

println("$resultIt")
println("$resultStr")
println("$resultUnit")


// Person(name=James, age=56)
// Steve is 59
// kotlin.Unit
```
블럭의 마지막 코드에 따라서 `let`의 return 값 형태도 달라지는 것을 확인할 수 있다.

또한 `T?.let{ }` 형태도 볼 수 있는데, 자기 자신(**T**)을 매개변수로 입력받아 사용되는 `let`에서 자기 자신(**T**)이 non-null 일 때만 블럭 안의 코드들이 수행된다.
다시 말해서 **T**가 `nullable` 타입이라고 할 때, non-null 일 때만 수행되야 하는 코드를 굳이 `if (T != null) { ... }`로 분기하지 않고 `T?.let`을 통해서 처리할 수 있다. 

## with
``` kotlin
fun <T, R> with(receiver: T, block: T.() -> R): R
```
`with`는 자기 자신을 매개변수로 받는 `let`과 달리 이란 함수이기 때문에 파라미터 `receiver: T`를 직접 입력 받고, 받은 객체를 사용하기 위한 두 번째 파라미터 `block: T.() -> R`를 받는다. `T.()`를 람다 리시버라고 하는데, 입력을 받으면 함수 내에서 `this`를 사용하지 않고도 입력받은 객체(`receiver: T`)의 속성을 변경할 수 있다.

즉, 다음 예제에서 `with(T)` 타입으로 Person을 받으면 `{ }` 내의 블럭 안에서 곧바로 Person의 속성값인 `name`이나 `age`에 접근할 수 있다.
추가적으로 **`with`는 non-null 객체를 사용하고 블럭 안에 return 값이 필요하지 않을 때 사용한다.** 그래서 주로 객체의 함수(변수)를 여러 개 호출할 때 그룹화하는 용도로 활용된다.

``` kotlin
val person = Person("James", 56)
with(person) {
    println(name)
    println(age)
    //자기자신을 반환해야 하는 경우 it이 아닌 this를 사용한다
}

// James
// 56
```
