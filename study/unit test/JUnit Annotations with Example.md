> [Unit Test란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/unit%20test/Unit%20Test.md)</br>
> [Unit Testing 기본 구현](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/unit%20test/Unit%20Testing%20%EA%B8%B0%EB%B3%B8%20%EA%B5%AC%ED%98%84%20in%20Kotlin.md)

## JUnit Annotation 이란

<p>

- **JUnit Annotations**는 메타 데이터를 나타내는 특별한 문법 형태이다.
- 코드의 가독성과 구조를 나타내기에 용이하다.
- 변수, 인자(파라미터), 패키지, 함수 그리고 클래스에 사용될 수 있다.
- 자바 코드의 가독성과 간소화를 위해 ``JUnit4``에 도입되었다.
- ``JUnit4``와 ``JUnit3``의 큰 차이점은 ``JUnit4``는 **Annotation** 기반이라는 것이다.

</p>

## Annotation 정리

|Annotations|Description|
|--|--|
|@Test|실행할 테스트임을 명시한다.|
|@Before|`@Test` 테스트 케이스가 실행되기 전에 실행됨을 명시한다.|
|@BeforeClass| 클래스의 모든 테스트 케이스 전에 한 번 실행됨을 명시한다.|
|@After|`@Test` 테스트 케이스가 종료된 후 실행됨을 명시한다.|
|@AfterClass|클래스의 모든 테스트 케이스가 실행된 후 한 번 실행됨을 명시한다.|

<p align="center">
	
<img src="https://user-images.githubusercontent.com/40654227/175248682-a82de8f6-ac5e-4913-829c-726b7c1ec36e.png" width=500/>
	
</p>


## JUnit Annotations 예제

### AnnotationTest.kt
``` kotlin
import org.junit.*

class AnnotationTest {

    private lateinit var list: ArrayList<String>

    @Before
    fun runBeforeTestMethod() {
        list = arrayListOf("@Before", "run", "Before", "Test", "Method")

        println("@Before - runBeforeTestMethod")
        println("\t- executed before each test cases")
        println("\t- create new list($list)")
    }

    @After
    fun runAfterTestMethod() {
        list.clear()
        
        println("@After - runAfterTestMethod")
        println("\t- executed after each test cases")
        println("\t- clear list($list)")
        println()
    }

    @Test
    fun test_method_1() {
        println("@Test - test_method_1")
    }

    @Test
    fun test_method_2() {
        println("@Test - test_method_2")
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun runOnceBeforeClass() {
            println("@BeforeClass - runOnceBeforeClass")
            println("\t- executed before all test cases")
            println()
        }

        @AfterClass
        @JvmStatic
        fun runOnceAfterClass() {
            println("@AfterClass - runOnceAfterClass")
            println("\t- executed after all test cases")
        }
    }
}
```

#### Output

``` text
@BeforeClass - runOnceBeforeClass
	- executed before all test cases
	
@Before - runBeforeTestMethod
	- executed before each test cases
	- create new list([@Before, run, Before, Test, Method])
@Test - test_method_1
@After - runAfterTestMethod
	- executed after each test cases
	- clear list([])

@Before - runBeforeTestMethod
	- executed before each test cases
	- create new list([@Before, run, Before, Test, Method])
@Test - test_method_2
@After - runAfterTestMethod
	- executed after each test cases
	- clear list([])

@AfterClass - runOnceAfterClass
	- executed after all test cases
```


#### 참고
> [JUnit Annotations Tutorial with Example: What is @Test and @After](https://www.guru99.com/junit-annotations-api.html)</br>
> [JUnit – Basic annotation examples](https://mkyong.com/unittest/junit-4-tutorial-1-basic-usage/)
