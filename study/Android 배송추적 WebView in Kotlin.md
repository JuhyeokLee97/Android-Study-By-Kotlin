## 개요

### 시나리오

`배송업체`와 `송장번호`를 입력 후, `배송조회` 버튼 클릭 시 **네이버**에서 택배의 배송 상태를 추적한다.

### 실행영상
<p align="center">
    <img src="https://user-images.githubusercontent.com/40654227/166145994-f6e7a6ff-8687-4862-b6dd-9a10f30f4d1b.gif"/>
</p>

## 프로젝트 구조

![](https://user-images.githubusercontent.com/40654227/166146068-8115ce6a-7926-43e1-868a-d0e3621b8835.png)

### ViewBinding, Coil 사용 - In build.gradle(:app)

**ViewBinding**을 사용하기 위해 `viewBinding { enabled = true }` 를 **build.gradle(:app)**에 추가했다.

## Code

### activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_company"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="배송업체"
        android:textColor="#1c1c1c"
        android:textSize="14sp"
        app:layout_constraintBottom_toBottomOf="@id/et_company"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/et_company" />

    <EditText
        android:id="@+id/et_company"
        android:layout_width="0dp"
        android:layout_height="40dp"
        android:layout_marginHorizontal="10dp"
        android:hint="배송업체를 입력해주세요"
        android:padding="8dp"
        android:textColor="@color/black"
        android:textSize="13sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tv_number"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_number"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="송장번호"
        android:textColor="#1c1c1c"
        android:textSize="14sp"
        app:layout_constraintBottom_toBottomOf="@id/et_number"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/et_number" />

    <EditText
        android:id="@+id/et_number"
        android:layout_width="0dp"
        android:layout_height="40dp"
        android:layout_marginHorizontal="10dp"
        android:hint="송장번호를 입력해주세요"
        android:padding="8dp"
        android:textColor="@color/black"
        android:textSize="13sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/tv_number"
        app:layout_constraintTop_toBottomOf="@id/et_company" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btn_search_delivery_info"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#00C73C"
        android:text="배송조회"
        android:textColor="@color/white"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/et_number" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 배송조회 버튼 클릭 리스너
        binding.apply {
            btnSearchDeliveryInfo.setOnClickListener {
                if (etCompany.text.isNullOrEmpty() || etNumber.text.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, "배송업체 또는 송장번호를 입려해주세요.", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val intent = Intent(this@MainActivity, SearchDeliveryInfoActivity::class.java)
                val deliveryCompany = etCompany.text.toString()
                val trackingNumber = etNumber.text.toString()
                intent.putExtra("deliveryCompany", deliveryCompany)
                intent.putExtra("trackingNumber", trackingNumber)

                startActivity(intent)
            }
        }
    }
}
```

### activity\_search\_delivery\_info.xml

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".SearchDeliveryInfoActivity">

    <WebView
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintBottom_toBottomOf="parent"
        android:id="@+id/web_view"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### SearchDeliveryInfoActivity.kt

```
class SearchDeliveryInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchDeliveryInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchDeliveryInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchDeliveryBaseUrl ="https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="
        var deliveryCompany = intent.getStringExtra("deliveryCompany")
        var trackingNumber = intent.getStringExtra("trackingNumber")

        binding.webView.apply{
            webViewClient = WebViewClient()
            settings.javaScriptEnabled=true
            loadUrl("$searchDeliveryBaseUrl$deliveryCompany+$trackingNumber")
        }
    }
}
```
