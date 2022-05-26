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
> [Retrofit ``Interceptor``를 이용한 Header 추가 In Kotlin](https://devgeek.tistory.com/)

## 개요
<p>


- 모바일에서 HTTP API 통신을 할 때 사용하는 라이브러리이다.
- REST 기반의 웹서비스를 통해 JSON 구조를 쉽게 가져오고 업로드할 수 있다.

</p> 

<p>

<strong>HTTP API를 Interface로 구현할 수 있다.</strong>
``` java
public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
```
</p>
<p>

<strong>``Retrofit`` class는 위에서 정의한 ``GitHubService`` Interface의 구현체를 생성한다.</strong>
``` java
Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);
```

</p>
<p>

<strong>각각의 ``GitHubService``에서 만들어진 ``Call``은 동기적 또는 비동기적으로 ``HTTP request``를 해당 웹 서버에 요청할 수 있다.</strong>
``` java
Call<List<Repo>> repos = service.listRepos("octocat");
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

### REQUEST BODY

### FORM ENCODED AND MULTIPART

### HEADER MANIPUATION

### 동기 vs 비동기


