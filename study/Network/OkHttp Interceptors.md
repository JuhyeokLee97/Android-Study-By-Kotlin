# OkHttp Interceptors
## 개요
	
<p>
	`Interceptors`란 network 통신에서 요청에 대해 `monitor`, `rewrite` 그리고 `retry` 할 수 있는 강력한 메카니즘이다.

</p>

#### Sample Interceptor Code
	
``` kotlin
	class LoggingInterceptor : Interceptor{  
	    override fun intercept(chain: Interceptor.Chain): Response {  
	        val request = chain.request()  
	  
	        val t1 = System.nanoTime()  
	        Log.d(TAG, "Sending request ${request.url()} on ${chain.connection()} \n${request.headers()} ")  
	  
	        val response = chain.proceed(request)  
	  
	        val t2 = System.nanoTime()  
	        Log.d(TAG, "Received response for ${response.request().url()} in ${(t2 - t1)/1e6} \n${response.headers()}")  
	  
	        return response  
	    }  
	}
```

#### Output
	
``` 
	Sending request https://run.mocky.io/v3/9fb33b80-8b3a-4c9f-b608-764bc60eae98 on null 
	     
	Received response for https://run.mocky.io/v3/9fb33b80-8b3a-4c9f-b608-764bc60eae98 in 1320.567626 
	    Content-Type: application/json; charset=UTF-8
	    Date: Thu, 02 Jun 2022 11:28:58 GMT
	    Content-Length: 1445
	    Sozu-Id: 01G4J2SZT2BSHNYJFGJS4X29WA

```


<p>

`chain.proceed(request)` 는 Interceptor를 구현하는데 매우 중요한 부분이다. 이 부분이 HTTP 통신이 일어나는 시점이고, `request`에 해당하는 `response`를 생성하는 부분이다. 만약 `chain.proceed(request)`가 2번 이상 호출된다면 `response bodies`는 모두 닫히게 되어 접근이 불가하다.
	
</p>

<p>

``Intercepotrs``는 서로 엮일 수도 있다. 예를 들어 `compressing interceptor`와 `checksumming interceptor`가 있다고 했을 때, 우리는 데이터가 `compressed`된 이후에 `checksummed`되는지, `checksummed`된 이후에 `compressed` 되는 것인지 결정해야한다. 그래서 `OkHttp`는 `interceptor`를 추적하는 `list`를 사용하여 순서대로 `interceptor`들을 호출한다.


</p>
<p align="center">

<image src="https://square.github.io/okhttp/assets/images/interceptors%402x.png" width=400/>

</p>

## Application Interceptors

## Network Interceptors

## Choosing between application and network interceptors

## Rewriting Request

## Rewriting Response
