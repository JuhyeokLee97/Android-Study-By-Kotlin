# [OkHttp Interceptor in Android](https://www.geeksforgeeks.org/okhttp-interceptor-in-android/)
> [OkHttp Interceptor란...]()
> [Retrofit + OkHttp Interceptor 예제]()

## 개요
<p>

`Interceptors`란 API 통신에서 요청에 대해 `monitor`, `rewrite` 그리고 `retry` 할 수 있는 강력한 메커니즘이다. Interceptor를 통해서 우리는 API 통신을 만들 때, 통신 과정을 모니터링 하거나 특별한 작업을 수행할 수 있다. 쉽게 말해서, Interceptor 기능은 공항 보안 요원이 보안 검사하는 과정과 비슷하다. 그들은 먼저 탑승권을 확인하고, 승인한 다음에서야 우리가 지나갈 수 있도록 하는 것처럼 말이다. 

</p>

<p>

`Interceptor`는 중앙에서 API 호출들을 모니터링 하는 것처럼 다양하게 사용된다. 일반적으로 우리는 각각의 network 호출에 대해 `logger`를 달아야 할 필요가 있다. 하지만 Interceptor를 이용한다면 하나의 `logger`를 추가하여 모든 network 호출에 대해 동작하도록 할 수 있다. 다른 경우로는 `offline-first app`의 build를 위해 network 요청의 `response`를 캐싱하기도 한다.

</p>

## Interceptor 종류
<p>

`Interceptors`에는 2가지 타입이 있다.
1. <strong>Application Interceptors</strong>: `Application Code(우리가 작성한 코드)` 와 `OkHttp Core Library` 사이에 추가된 `Interceptors`. 
    - 이런 Interceptor들은 `addInterceptor()`를 이용한다.
2. <strong>Network Interceptor: </strong>`OkHttp Core Library`와  `server` 사이에 추가된 `Interceptors`
	- 이런 Interceptor들은 `addNetworkInterceptor()`를 이용한다.

</p>

<p>

<image src="https://square.github.io/okhttp/assets/images/interceptors%402x.png" width=400/>

</p>

## OkHttpClient에 interceptors 추가하기
``` kotlin
fun devgeekHttpClient(): OkHttpClient {
	val bulder = OkHttpClient().newBuilder()
		.addInterceptor(/*my interceptor*/)
	return builder.build()
}
```

<p>

위에 코드를 보면 ``addInterceptor``에 내가 만든 Interceptor를 추가할 수 있는데, 이론적인 것보다는 실제로 어떻게 Android 개발에서 Interceptor가 사용되는지 살펴보도록 하자.

</p>

## Error-Interceptor
``` kotlin
class devgeekInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {  
        val request = chain.request()  
        val response = chain.proceed(request)  
  
        when (response.code()) {  
            400 -> {  
                // Show Bad Request Error Message 
			}  
            401 -> {  
                // Show UnauthorizedError Message  
  
		    }  
            403 -> {  
                // Show Forbidden Message  
  		    }  
  		    404 ->{
				// Show NotFound Message
			}
			// ... and so on
        }  
  
        return response  
    }  
}
```

<p>

desc...

</p>

## Kepping the response in the cache

## What should I do now

## Adding a Header, such as an Access Token, in a central location

## Refreshing the Access Token in a Single Location
