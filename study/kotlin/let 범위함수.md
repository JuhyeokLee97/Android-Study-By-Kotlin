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

### 첫 번째 - 기본 사용 방법

> `let` can be used to invoke one or more functions on results of call chains.
> For example, the following code prints the results of two operaions on a collction:


**간단하게 말하면 람다식을 사용할 블록 안에 복수개의 함수들을 호출할 수 있다는 것이다.**

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


### 두 번째 - 람다식 대신 `::` 사용
> if the code block contains a single function with `it` as an argument, 
> you can use the method reference(`::`) instead of the lambda

<strong>간단하게 말하면 블록안에 들어가는 코드가 `it`을 매개변수로 받는 함수 한줄이라면 람다식 대신 `::`를 사용할 수 있다는 것이다.</strong>

``` kotlin
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let(::println)    // [5, 4, 4]
```

#### Example In Android
개인적으로 개발하면서 Activity 전환을 할 때 자주 사용했다.</br>
예를 들어 `Intent`를 만들고 `startActivity`를 이용해서 Activity를 전환한다고 한다면 다음과 같이 `let`을 사용한다.

``` kotlin
Intent(Intent(this, MainActivity::class.java)).apply { 
    putExtra("String", "string")
    putExtra("Integer", 0)
    putExtra("Boolean", false)
}.let(::startActivity)
```

### 세 번째 - `non-null`
> `let` is often used for executing a code block only with **non-null** values. 
> To perform actions on a non-null object, use the **safe call operator** `?.` on it and call `let` with the action in its lambda.

<strong>간단하게 말하면 `let`을 사용하려는 Nullable한 객체에 `?.` 연산자를 사용하여 `let`을 호출하면 non-null인 경우만 코드블럭에 있는 람다식이 수행된다는 것이다.</strong>

``` kotlin
val str: String? = "Hello"
val length = str?.let {
        println("let() called on $it")
        processNonNullString(it)        // OK: 'it' is not null inside '?.let { }'
        it.length
}
```

#### Example In Android
개인적으로 개발하면서 이미지 로드하는 작업에서 많이 사용했다.</br>
예를 들면 앱의 사용자가 **마이페이지**와 관련된 서비스를 사용한다면 사용자 정보가 보여져야할테고 그 때 사용자의 프로필 이미지를 로드할 경우,
사용자의 프로필 이미지가 등록된 경우가 아닐 때 `null`이라고 기획하여 **non-null**인 경우에만 이미지를 로드하고 아니면 기본 이미지로 대체했다.
다음은 위에서 말한 부분을 `?.` 연산자와 `let`을 이용하여 구현한 코드이다.
``` kotlin
data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val profileImage: String?
)

--------------------------------

// bind user profileImage
user.profileImage?.let{
    binding.ivProfile.load(it)
}

```

UserDataClass Nullable 처리
 
### 마지막
> Another case for using `let` is introducing local variables with a limited scope for improving code readability.
> To define a new variable for the context object, provide its name as the lambda argument
> so that int can be used instead of the default it.

``` kotlin
val numbers = listOf("one", "two", "thre", "four")
val modifiedFirstItem = numbers.first().let { firstItem ->
        println("The first item of the list is '$firstItem'")
        if (firstItem.length >= 5)
            firstItem
        else
            "!" + firstItem + "!"
}.uppercase()

println("First item after modifications: '$modifiedFirstItemd'")

// ## Output ##
// The first item of the list is 'one'
// First item after modifications: '!ONE!'
```
## Android App 개발에서 사용되는 부분 추가하자.

[공식문서 - Scope function:let](https://kotlinlang.org/docs/scope-functions.html#let)
