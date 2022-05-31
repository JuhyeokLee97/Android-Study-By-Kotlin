# Retrofit + Interceptor 예제 in Kotlin
## Request Interceptor For Adding Headers
### RetrofitClient.kt
<p>
  
  desc...
  
</p>

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
        var cookie = ""

        builder.addHeader("cookie", cookie)
        builder.addHeader("User-Agent", "Plz-Sample-App")

        return chain.proceed(builder.build())
    }
}
```

## Response Interceptor For Control Error
### RetrofitClient.kt
<p>
  desc...
</p>

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

## Request Interceptor For Adding Headers & Response Interceptor For Control Error
### RetrofitClient.kt
<p>
  dsec...
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
        val builder = chain.request().newBuilder()
        var cookie = ""

        builder.addHeader("cookie", cookie)
        builder.addHeader("User-Agent", "Plz-Sample-App")

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
