# [Singleton Class in Kotlin](https://blog.mindorks.com/how-to-create-a-singleton-class-in-kotlin)

## 개요
<p>

앱에서 딱 한번만 생성되고 전역에서 사용할 객체가 필요하다면 우린 <strong>Singleton Pattern</strong>을 사용하면 된다.
`Singleton Pattern`은  해당 클래스에서 오직 하나의 객체만을 사용하도록 제한하는 것이다.

그래서  `Singleton Pattern`을 구현하기 위해서는 <strong>Singleton Class</strong>을 먼저 만들어야 한다.

이번 글에서는 Kotlin을 이용해 Singleton Class 만드는 법을 알아 보도록 한다.
</p>

## Singleton Class
<p>
Singleton Class 는 오직 하나의 객체만이 생성되고 어디서나 사용될 수 있는 클래스이다.

NetworkService, DatabaseService 등 오직 하나의 객체만 필요한 경우에 사용된다.

Singleton Class가 사용되는 이유는 일반적으로  객체를 생성하면 시스템 리소스가 객체가 생성되는 만큼 사용되기 때문이다. 그래서 각각의 객체를 생성해 리소스를 사용하기보다는 하나의 객체를 생성해 필요할 때마다 객체를 생성하지 않고 같은 객체를 사용하는 것이다.
</p>

## Properties of Singleton Class
Singleton Class는 일반적으로 아래와 같은 특징을 갖는다.

1. <strong>Only one instance: </strong> Singleton Class는 해당 클래스에 하나의 객체만을 제공한다. 또한 `outer classes` 나 `subclasses`에서 해당 객체 생성을 막는다.
2. <strong>Globally accessible: </strong> Singleton 객체는 전역에서 접근하여 사용할 수 있다.

## Rules for making a class Singleton

1. A private constructor
2. A static reference of its class
3. One static method
4. Globally accessible object reference
5. Consistency across multiple threads

## Singletone Example in Java
``` java
public class Singleton{
	private static Singleton instance = null;

	private Singletone(){
	
	}

	public static Singleton getInstance(){
		if (instance == null){
			synchronized(Singleton.class){
				if (instance == null){
					instance = new Singleton();
				}
			}	
		}
		return instance;
	}
}
```

<p>

쓰레드 간의 간섭이 업도록 인스턴스를 만들 때는  ``synchronized`` 키워드를 사용한다.

</p>

## Singleton Example in Kotlin
<p>

Kotlin 코드도 같다. 아래는 Singleton class 코드 예제이다.

``` kotlin
object Singleton
```

이 다음에는 어떻게 해야할까?
터무니 없겠지만, 위 코드 그대로다. 너무 간단하지 않는가?
좀 더 상세한 예시를 설명하도록 하겠다.

</p>

<p>

코틀린에서 Singleton Class를 구현하기 위해서는 ``object`` 키워드를 사용해야한다. ``object`` 클래스는 `function`, `properties` 그리고 `init`함수를 갖을 수 있다. 
생성자는 `object`에서 필요가 없는데, 만약 몇몇 초기화가 필요한 작업이 있다면``init`` 함수를 대신 사용하면 된다. 
그리하여 객체가 처음 생성될 때, ``init``함수가 동작하여 초기화 작업을 수행한다.

아래 코드는 코틀린을 이용한 Singleton Class 이다.
``` kotlin
object Singleton {

    init{
        println("Singleton class invoked.")
    }

    var variableName = "I am Var"
    fun printVarName(){
        println(variableName)
    }
}

fun main(args: Array<String>){
    Singleton.printVarName()
    Singleton.variableName = "New Name"

    var a = A()
}

class A{
    init{
        println("Class init method. Singleton variableName property : ${Singleton.variableName}")
        Singleton.printVarName()
    }
}
```

<string>Output</string>
```
Singleton class invoked.
I am Var
Class init method. Singleton variableName property : New Name
New Name
```

</p>
