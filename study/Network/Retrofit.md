# Retrofit
[header추가](https://velog.io/@dabin/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9CRetrofit)

> [Retrofit ``GET`` 사용 예제 In Kotlin](https://devgeek.tistory.com/)
> 
> [Retrofit ``POST`` 사용 예제 In Kotlin](https://devgeek.tistory.com/)
> 
> [Retrofit ``PUT`` 사용 예제 In Kotlin](https://devgeek.tistory.com/)
> 
> [Retrofit ``DELETE`` 사용 예제 In Kotlin](https://devgeek.tistory.com/)
> 
> [Retrofit ``HEADER`` 추가하기 In Kotlin](https://devgeek.tistory.com/)
> 
> [Retrofit ``Interceptor``를 이용한 Header 추가 In Kotlin](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/Network/Retrofit%20%2B%20Interceptor%20%EC%98%88%EC%A0%9C.md)

## 개요
<p>


- 모바일에서 HTTP API 통신을 할 때 사용하는 라이브러리이다.
- REST 기반의 웹서비스를 통해 JSON 구조를 쉽게 가져오고 업로드할 수 있다.

</p> 

<p>

<strong>HTTP API를 Interface로 구현할 수 있다.</strong>
``` kotlin
interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(
        @Path("user") user: String
    )
}
```
</p>
<p>

<strong>``Retrofit`` class는 위에서 정의한 ``GitHubService`` Interface의 구현체를 생성한다.</strong>
``` Kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build()

val service = retrofit.create(GitHubService::class.java)
```

</p>
<p>

<strong>각각의 ``GitHubService``에서 만들어진 ``Call``은 동기적 또는 비동기적으로 ``HTTP request``를 해당 웹 서버에 요청할 수 있다.</strong>
``` kotlin
val repos = service.listRepos("octocat")
```

</p>
<p>

>HTTP request 를 명시적으로 사용하기 위해서 ``annotation``을 사용한다.
> -   URL parameter replacement and query parameter support
>-   Object conversion to request body (e.g., JSON, protocol buffers)
>-   Multipart request body and file upload


</p>

## API 정의
<p>

함수 위에 있는 ``Anotation`` 그리고 ``Parameter``는 HTTP request가 어떤 방식으로 요청 처리 되는지를 잘 명시한다. 

</p>

### REQUEST METHOD
<p>

- 모든 함수는 ``HTTP annotation``과 ``상대적인 URL`` 정보를 가지고 있어야한다.
- <strong>Retrofit</strong>에서는 8가지의 ``annotation``을 제공한다.
	- {``HTTP``,  ``GET``, ``POST``, ``PUT``, ``PATCH``, ``DELETE``, ``OPTIONS``, ``HEAD``}
``` java
@GET("users/list")
```

- ``query parameter``들을 URL에 추가할 수 있다.
``` java
@GET("users/list?sort=desc")
``` 

</p>

### URL MANIPULATION
<p>

Request URL은 함수의 파라미터를 통해 동적으로 바뀌는 값을 사용하기도 한다. URL에 표기할 때에는 ``{ }`` 중괄호를 사용한다. 그리고 함수의 파라미터에서는 ``@Path("")`` ``annotation``을 사용한다.

</p>

#### Path Param
``` Kotlin
@GET("group/{id}/users")
fun groupList(
    @Path("id") groupId: Int
): Call<List<User>>
```

#### Query Param
``Query Parameters``도 Request에 추가할 수 있다. ``@Query("")`` ``annotation``을 사용한다.
```java
@GET("group/{id}/users")
Call<List<User>> groupList(
	@Path("id") int groupId,
	@Query("sort") String sort
);

```

#### Complex Query Param - ``Map``
``` Kotlin
@GET("group/{id}/users")
fun groupList(
    @Path("id") groupId: Int,
    @QueryMap option: Map<String, String>
): Call<List<User>>
```

### REQUEST BODY
<p>

객체를 HTTP request body로 사용되기 위해 ``@Body`` ``annotation``을 이용하여 body를 추가할 수 있다.

``` kotlin
@POST("users/new")
fun createUser(
    @Body user: User
): Call<User>
```

``User user`` 객체는 ``Retrofit`` 객체로 변환되다.

</p>

### FORM ENCODED AND MULTIPART
<p>

``Form-encoded`` 데이터는 ``FormUrlEncoded`` ``annotation``을 이용해서 함수에 추가할 수 있다.
각 각의 ``key-value``는 ``@Field``를 사용하면 된다.

``` Kotlin
@FormUrlEncoded
@POST("user/edit")
fun updateUser(
    @Field("first_name") first: String,
    @Field("last_name") last: String
): Call<User>
```
</p>

<p>

<strong>Multipart request</strong>은 ``@Multipart``를 명시한 후, ``@Part`` ``annotation``을 이용하여 파라미터를 정의해주면 된다.

``` Kotlin
@Multipart
@PUT("user/photo")
fun updateUser(
    @Part("photo") photo: RequestBody,
    @Part("description") description: RequestBody
): Call<User>
```
Multipart를 사용할 때는 ``Retrofit`` 자체 converter를 이용하거나 자체적으로 구현한 ``RequestBody``를  이용해  ``Serialization``을 다룬다.

</p>

### HEADER MANIPUATION
	
<p>

정적인 ``header``들을 추가할 때에는, ``@Headers`` ``annotation``을 사용하면 된다.
	
``` kotlin
@Headers("Cache-Control: max-age=640000")
@GET("widget/list")
fun widgetList(): Call<List<Widget>>
```
	
``` kotlin
@Headers(
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit-Sample-App"
)
@GET("user/{username}")
fun getUser(
    @Path("username") username: String,
): Call<User>	
```
	
</p>
<p>

동적인 ``header``들을 추가할 때에는, ``@Header``를 파라미터처럼 사용하면 된다. 

``` kotlin
@GET("user")
fun getUser(
    @Header("Authorization") authorization:String
):Call<User>
```

``` kotlin
@GET("user")
fun getUser(
    @HeaderMap header: Map<String, String>
): Call<User>
```


>모든 <strong>request</strong>에 추가되는 <strong>Headers</strong> 같은 경우는 <strong>OkHttp interceptor</strong>을 통해 대체될 수 있다.

