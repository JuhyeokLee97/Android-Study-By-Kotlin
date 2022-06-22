
## [Unit Testing in Android using JUnit](https://www.geeksforgeeks.org/unit-testing-in-android-using-junit/)

## 개요
### Unit Test
> [Android Unit Test란](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/unit%20test/Unit%20Test.md)
> UnitTest Annotation 정리
<p>

``Unit testing``은 개발자로 하여금 에러가 없는 좋은 코드를 만들 수 있도록 도와준다. 
실제 앱을 바로 개발하기 보다는 개발 전에 `Unit Test`들을 작성하기를 추천하는데, 먼저 `test`들을 작성하면 실제 앱 개발은 테스트 가이드 라인에 맞춰 개발하면 된다. 

</p>

### 기능 설명
<p>

 
테스트할 내용... 주석을 정리하자

</p>

## 구현 예제

### build.gradle(:Module): Unit Test를 위한 의존성 추가

<p>
    
   안드로이드 신규 프로젝트를 생성하게 되면 기본적인 **JUnit**과 **Test**를 위한 의존성이 추가되어 있다.
    이번에는 매우 기본적인 **Unit Test**를 진행하기 때문에 추가적인 의존성을 추가할 필요는 없다.
    
</p>

<p>
프로젝트를 생성했을 때의 기본 값이다.
    
```kotlin
dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.2'
    implementation 'com.google.android.material:material:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

}
```
    
</p>


### RegistrationUtil.kt: 테스트 될 대상(객체) 구현

<p>

- 테스트할 대상을 만들기 위해, 임의의 디렉토리에 **RegistrationUtil.kt** Object 파일을 만든다.
- **RegistrationUtil**은 싱글톤이기 때문에 따로 오브젝트를 생성할 필요는 없다.
- 3개의 인자(`username`, `password`, `confirm password`)를 받는 **validRegistrationInput** 함수를 구현한다. 이 함수는 테스트의 대상이된다.

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

#### 테스트 파일 만들기

<p>

아래 사진 처럼 프로젝트의 패키지들 중 `(test)`라고 작성되어있는 패키지가 있다. 해당 패키지에 `RegistrationUtilTest.kt`  클래스 파일을 만든다.

<img src="https://user-images.githubusercontent.com/40654227/175023209-a0aba0e6-ee45-40b5-811b-32d4596b14bd.png"/>
</p>

#### Code
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

<p>

작성한 테스트 코드(파일)을 실행하는 방법은 아래 이미지와 같이 해당 파일에서 마우스 우클릭을 통해 나온 옵션 중 `Run 'RegistrationUtilTest...'`를 선택해주면 된다. 

<img src="https://user-images.githubusercontent.com/40654227/175024503-cc2ae5a9-c890-4f72-bf9a-c54b2f85e523.png"/>
</p>


#### 테스트 결과
작성한 5가지의 테스트 모두 통과했다.

<img src="https://user-images.githubusercontent.com/40654227/175025177-c27bf2c2-9e57-4061-9906-7d4e0c68e2ed.png"/>
