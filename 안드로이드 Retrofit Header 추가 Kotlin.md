## Retrofit Configuration

`Retrofit` is the class through which your API interfaces are turned into callable objects. By default, Retrofit will give you sane defaults for your platform but it allows for customization.

## @Headers

아래와 같은 형식으로 요청에 필수적인 값들을 Header에 담을 수 있다.

```
    BookService.kt

@Headers(
    "Key-1: Value-1",
    "Key-2: Value-2",
    ...,
    "Key-N: Value-N"
)
@GET("/books")
fun getBooks(): Call<BookModel>
```

## Programmatically Add Headers

Header에 들어가는 값이 동적인 경우도 있다. 이 때에는 함수 호출 시, 파라미터로 헤더에 추가할 값을 지정할 수 있다.  
예를 들어 현재 사용자의 프로필 정보를 읽어오는데 있어 사용자의 **Session Token** 이 필요하다면 이를 정적으로 **Header**에 담을 수 없다. 그래서 **@Header("키") 변수명: 자료형** 형태로 함수의 파라미터로 선언하여 사용할 수 있다.

```
    UserService.kt

@Headers(
    "Key-1: Value-1",
    "Key-2: Value-2",
    ...,
    "Key-N: Value-N"
)
@GET("/user/profile")
fun getUserProfile(
    @Header("Session-Token") sessionToken: String,
): Call<UserProfileModel>
```
