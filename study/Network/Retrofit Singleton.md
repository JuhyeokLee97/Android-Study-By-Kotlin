# Retrofit Singleton
> [Singleton Pattern 이란]()
> [Retrofit 이란]()
## 왜 Retrofit을 Singletone으로?
<p>
  Singleton Class가 사용되는 이유는 일반적으로  객체를 생성하면 시스템 리소스가 객체가 생성되는 만큼 사용되기 때문이다. 그래서 각각의 객체를 생성해 리소스를 사용하기보다는 하나의 객체를 생성해 필요할 때마다 객체를 생성하지 않고 같은 객체를 사용하는 것이다.
  
  Singleton Class 대표적 특징은 아래와 같다.
  1. <strong>Only one instance: </strong> Singleton Class는 해당 클래스에 하나의 객체만을 제공한다. 또한 `outer classes` 나 `subclasses`에서 해당 객체 생성을 막는다.
  2. <strong>Globally accessible: </strong> Singleton 객체는 전역에서 접근하여 사용할 수 있다.
  
  그래서 <strong>NetworkService</strong>, <strong>DatabaseService</strong> 등 오직 하나의 객체만 필요한 경우에 사용된다.

</p>

## Retrofit 객체 Singleton 사용 예제
### RetrofitClient.kt
``` kotlin
object RetrofitClient {
    private const val BASE_URL = "https://run.mocky.io"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
```

### ProductService.kt
``` kotlin
interface ProductService {
    @GET("v3/9fb33b80-8b3a-4c9f-b608-764bc60eae98")
    fun getProducts(): Call<ProductDto>

}
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ...

        val retrofit = RetrofitClient.retrofit
        retrofit.create(ProductService::class.java).getProducts()
            .enqueue(object : Callback<ProductDto> {
                override fun onResponse(call: Call<ProductDto>, response: Response<ProductDto>) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<ProductDto>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }
}
```
