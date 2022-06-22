## [Unit Testing in Android using JUnit](https://www.geeksforgeeks.org/unit-testing-in-android-using-junit/)

## 개요
### Unit Test
> [Android Unit Test란]()
<p>

``Unit testing``은 개발자로 하여금 에러가 없는 좋은 코드를 만들 수 있도록 도와준다. 
실제 앱을 바로 개발하기 보다는 개발 전에 `Unit Test`들을 작성하기를 추천하는데, 먼저 `test`들을 작성하면 실제 앱 개발은 테스트 가이드 라인에 맞춰 개발하면 된다. 

</p>

### 기능 설명
<p>

In this article, we are using **JUnit** to test our code. **JUnit** is a “**Unit Testing**” framework for Java Applications which is already included by default in android studio. It is an automation framework for Unit as well as UI Testing. It contains annotations such as **@Test,**  **@Before**, **@After**, etc. Here we will be using only **@Test** annotation to keep the article easy to understand. Note that we are going to implement this project using the **Kotlin** language.

테스트할 내용...

</p>

## 구현 예제

### build.gradle(:Module): Unit Test를 위한 의존성 추가

### RegistrationUtil.kt: 테스트 될 대상(객체) 구현

<p>
dsec...
</p>

<p>

``` kotlin
object RegistrationUtil {

    private val existingUsers = listOf("Rahul", "Rohan")

    /**
     * The test cases will pass if..
     * ...username/password/confirmPassword is not empty
     * ...password is at least 2 digits
     * ...password matches the confirm Password
     * ...username is not taken
     */

    fun validRegistrationInput(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        /**
         * Write conditions along with their return statement
         * if (username | password | confirm password) are empty return false
         */
        if (userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false
        }
        /** if username exists in the existingUser list return false */
        if (userName in existingUsers)
            return false
        /** if password does not matches confirm password return false */
        if (password != confirmPassword)
            return false
        /** if digit count of the password is less than 2 then return false*/
        if (password.count { it.isDigit() } < 2)
            return false
        return true
    }
}
```

</p>

### RegistrationUnitTest.kt: 테스트 클래스 구현

<p>
만드는 방법... 
dsec...
</p>

<p>

``` kotlin
import org.junit.Assert.*
import org.junit.Test

class RegistrationUtilTest{
    /**
     * Write tests for the RegistrationUtil class considering all the conditions
     * annotate each function with @Test
     * We can use backtick to write function name..
     * whatever we write in those backtick will be considered as function name
     */
    @Test
    fun `empty username return false`(){

        // Pass the value to the function of RegistrationUtil class
        // since RegistrationUtil is an object/ singleton we do not need to create its object
        val result = RegistrationUtil.validRegistrationInput(
            "",
            "123",
            "123"
        )
        // assertThat() comes from the truth library that we added earlier
        // put result in it and assign the boolean that it should return
        assertFalse(result)
    }
    /**
     * follow the same for other cases also
     * in this test if username and correctly repeated password returns true
     */
    @Test
    fun `username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validRegistrationInput(
            "Rahul",
            "123",
            "123"
        )
        assertFalse(result)
    }

    /** in this test userName already taken returns false */
    @Test
    fun `username already taken returns false`() {
        val result = RegistrationUtil.validRegistrationInput(
            "Rohan",
            "123",
            "123"
        )
        assertFalse(result)
    }

    /** if confirm password does not matches the password return false */
    @Test
    fun `incorrect confirm password returns false`() {
        val result = RegistrationUtil.validRegistrationInput(
            "Rahul",
            "123",
            "1234"
        )
        assertFalse(result)
    }

    /** in this test if password has less than two digits than return false */
    @Test
    fun `less than two digit password return false`() {
        val result = RegistrationUtil.validRegistrationInput(
            "Rahul",
            "abcd1",
            "abcd1"
        )
        assertFalse(result)

    }
}
```

</p>

### Test 결과 확인
#### 테스트 실행 밥법
#### 테스트 결과
