## Builder Pattern 이란
<p>

`Builder Pattern`은 객체지향 프로그래밍에서 다양한 객체 생성 문제에 대한 유연한 솔루션을 제공하기 위해 설계된 GoF 디자인 패턴 중 하나이다. `Builder Pattern`의 목적은 복잡한 객체의 구성을 해당 표현과 분리하는 것이다. 빌더 패턴은 다음과 같은 문제를 해결한다.

</p>

<p>

- 별도의 Builder 객체에서 복잡한 객체의 일부를 만들고 조합하는 것을 캡슐화한다.
- 클래스는 객체를 직접 생성하는 대신 <strong>Builder 객체에게 객체 생성을 위임한다.</strong>

클래스 안에서 직접 복잡한 객체의 대부분을 만들고 조합하는 건 유연하지 않다. 복잡한 객체의 특정 표현을 생성하도록 클래스를 커밋하고 나중에 클래스와 독립적으로 표현을 변경할 수 없도록 한다. 클래스는 복잡한 객체의 다른 표현을 만들기 위해 다른 Builder 객체에게 위임할 수 있다.

</p>

## Builder Pattern 장점
1. 클래스의 내부 표현을 변경할 수 있다.
2. 구성 및 표현을 위한 코드를 캡슐화한다.
3. `construction process`의 단계에 대한 제어를 제공한다.

## Builder Pattern 단점
1. 각 클래스 유형에 대한 고유한 `ConcreteBuilder`를 만들어야 한다.
2. Builder 클래스는 변경 가능해야 한다.
3. 종속성 주입을 방해하거나 복잡하게 만들 수 있다.


## Android Builder Pattern 
1. AlertDialog
2. Retrofit
