# `apply` 범위함수

## 범위함수란
범위함수란 영어로 **Scope Funtion**으로 불린다.
함수명 **Scope Funtion**에서 알 수 있듯이 이 범위 내에 함수를 만드는 것이다.
범위(Scope)를 만들어서 그 안에서 람다식을 이용해 로직을 구현할 수 있다.

## `apply` 범위함수란

```kotlin
fun <T> T.apply(block: T.() -> Unit): T
```

`apply` 범위함수에 대해서 Kotlin 공식 문서에서는 다음과 같이 설명하고 있다.
> **The context object** is available as a receiver(`this`). **The return value** is the object itself.
>
> Use `apply` for code blocks that don't return a value and mainly operate on the members of the receiver object.
> The common case for `apply` is the object configuration. Such call can be read as "apply the following assignments to the object."

``` kotlin
val adam = Person("Adam").apply {
    age = 32
    city = "London"        
}
println(adam)   // Person(name=Adam, age=32, city=London)
```
> Having the receiver as the return value, you can easily include `apply` into call chains for more complex processing.

공식 문서에 설명이 정말 잘 되어있는 것 같다.
간단하게 요약한다면 자기 자신을 **receiver**(this)로 받는 코드 블록을 생성하고 자기 자신을 반환한다는 것이 `apply`다.


### 사용법
공식문서에서도 사용법에 대해 좋은 예시가 있지만, 내가 `apply`를 사용하면서 경험상 좋았던 예시와 안좋았던 예시에 대해 다루어보겠다.
#### GOOD

``` kotlin
val intent = Intent(this@MainActivity, ProductInfoActivity::class.java).apply{
    putExtra("userId", userId)
    putExtra("productId", productId)
    /** do something with this intent */
}
startActivity(intent)
```
`Intent`를 생성해서 해당 `Intent`에 데이터를 담거나 특별한 작업들이 필요할 때, 
`Intent`를 생성한 후 `intent.[-]()` 형태와 같이 변수를 반복적으로 쓰기보다는 직관적으로 선언과 동시에 `apply`를 이용해서 정의를 해주면 반복된 코드도 줄이게 되고 코드를 읽기도 편했다.

#### BAD

``` kotlin
binding.apply{
    imageView.load(url)
    textView.text = "test text"
    button.setOnClickListener{
        /** do something */
    }
}
```
`ViewBinding` 또는 `DataBinding`을 사용할 때, ViewController에서 각가의 View들을 초기화할 때 `binding`을 반복적인 사용을 줄이기 위해서 `apply`를 이용해서 했다.
하지만 `apply` 사용의 목적은 `The common case for `apply` is the object configuration.`이고 반환되는 객체를 사용하지도 않기 때문에 좋은 사용은 아니였던 것 같다.
대신 `with` 또는 `run`을 사용하면 괜찮은 것 같다.
