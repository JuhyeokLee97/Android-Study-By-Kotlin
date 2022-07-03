## JUnit Annotation 이란

<p>

- **JUnit Annotations**는 메타 데이터를 나타내는 특별한 문법 형태이다.
- 코드의 가독성과 구조를 나타내기에 용이하다.
- 변수, 인자(파라미터), 패키지, 함수 그리고 클래스에 사용될 수 있다.
- 자바 코드의 가독성과 간소화를 위해 ``JUnit4``에 도입되었다.
- ``JUnit4``와 ``JUnit3``의 큰 차이점은 ``JUnit4``는 **Annotation** 기반이라는 것이다.

</p>

## Annotation 정리

This annotation can be used if you want to execute some statements after each [Test Case](https://www.guru99.com/test-case.html) for e.g resetting variables, deleting temporary files ,variables, etc.
|Annotations|Description|
|--|--|
|[@Test]()|테스트를 수행할 함수를 명시한다. `org.junit.TestCase`에서 변경되었다.|
|[@Before]()|각각의 테스트 케이스를 수행하기전에 동작하도록 명시한다. 각 테스트를 수행하기 전에 조건을 설정해야 할 때 사용한다.|
|[@BeforeClass]()|모든 테스트 전에 테스트 클래스를 생성하면서 특정 동작을 수행하도록 명시한다.|
|[@After]()|This annotation can be used if you want to execute some statements after each [Test Case](https://www.guru99.com/test-case.html) for e.g resetting variables, deleting temporary files ,variables, etc.|
|[@AfterClass]()|This annotation can be used if you want to execute some statements after all test cases for e.g. Releasing resources after executing all test cases.|
|[@Ignores]()|This annotation can be used if you want to ignore some statements during test execution for e.g. disabling some test cases during test execution.|
|[@Test(timeout=500)]()|This annotation can be used if you want to set some timeout during test execution for e.g. if you are working under some SLA (Service level agreement), and tests need to be completed within some specified time.|
|[@Test(expected=IllegalArgumentException.class)]()|This annotation can be used if you want to handle some exception during test execution. For, e.g., if you want to check whether a particular method is throwing specified exception or not.|


#### 참고
[Unit Annotations Tutorial with Example: What is @Test and @After](https://www.guru99.com/junit-annotations-api.html)
