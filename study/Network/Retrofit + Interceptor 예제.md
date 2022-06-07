# Retrofit + Interceptor 예제 in Kotlin

> [인터셉터란](https://devgeek.tistory.com/56)</br>
> [Retrofit Singletone Pattern](https://devgeek.tistory.com/53)</br>
> [Singleton Pattern](https://devgeek.tistory.com/52)

## Request Interceptor For Adding Headers
### RetrofitClient.kt

``` kotlin
object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io"

    val interceptorClient = OkHttpClient().newBuilder()
        .addInterceptor(RequestInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(interceptorClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        var auth = "" // get from localStorage

        builder.addHeader("Auth", auth)
        builder.addHeader("User-Agent", "Dev-Geek-Sample-App")

        return chain.proceed(builder.build())
    }
}
```

<p>
 
 `RetforitClient`는 Singleton Pattern을 사용했다.

#### RequestInterceptor 구현
1. 먼저 `chain.request()`로 부터 `request`를 받아 `newBuilder()`를 통해  `builder`를 생성한다.
2. 다음 local storage에서 header에 담을 `auth`값을 읽어온다.
3. `builder`에 `addHeader(K, V)` 메서드를 이용하여 헤더 정보를 담는다.
4. `chain.proceed()`에 파라미터로 `builder`를 담아 요청보낸다.

#### Add Interceptor
먼저 앞서 구현한 `RequestInterceptor`를 포함하는 `OkHttpClient Builder`를 생성한다. (`interceptorClient`)</br>
다음으로 `retrofit`을 생성할 때, `client()` 메서드를 통해 인자에 생성한 `interceptorClient`를 넣어 생성한다.
   
</p>


## Response Interceptor For Control Error
### RetrofitClient.kt

``` kotlin
object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io"

    val interceptorClient = OkHttpClient().newBuilder()
        .addInterceptor(ResponseInterceptor())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(interceptorClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code()) {
            400 -> {
                // todo Control Error
            }
            401 -> {
                // todo Control Error
            }
            402 -> {
                // todo Control Error
            }
        }
        return response
    }
}
```

<p>
 
 `RetforitClient`는 Singleton Pattern을 사용했다.
#### ResponseInterceptor 구현
1. 먼저 request에 해당하는 response를 생성 하기 위해, `chain.request()`를 통해 `request`를 생성한다.
2. 생성한 `request`를 이용하여 `chain.proceed(request)` request를 숭행한 결과의 `response`를 생성한다.
3. 마지막으로 `response.code`에 따라 에러를 처리해준다.

#### Add Interceptor
먼저 앞서 구현한 `ResponseInterceptor`를 포함하는 `OkHttpClient Builder`를 생성한다. (`interceptorClient`)</br>
다음으로 `retrofit`을 생성할 때, `client()` 메서드를 통해 인자에 생성한 `interceptorClient`를 넣어 생성한다.
   
</p>

## Request Interceptor For Adding Headers & Response Interceptor For Control Error
### RetrofitClient.kt
<p>

  아래의 예제 코드는 `Interceptor`에 `Header`에 데이터를 담는 것과 `Response`를 통해 에러 처리까지 모두 하는 `Interceptor`이다.

</p>

``` kotlin
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io"

    val interceptorClient = OkHttpClient().newBuilder().addInterceptor(RequestInterceptor())
        .addInterceptor(ResponseInterceptor()).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(interceptorClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var auth = "" // get from localStorage

        builder.addHeader("Auth", auth)
        builder.addHeader("User-Agent", "Dev-Geek-Sample-App")

        return chain.proceed(builder.build())
    }
}

class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code()) {
            400 -> {
                // todo Control Error
            }
            401 -> {
                // todo Control Error
            }
            402 -> {
                // todo Control Error
            }
        }
        return response
    }
}
```
