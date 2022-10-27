# Intent로 Data Class 타입 객체 넘기기

## 개요
직렬화란?
직렬화는 메모리 내에 존재하는 정보를 보다 쉽게 전송 및 전달하기 위해 byte 코드 형태로 나열하는 것이다. 여기서 메모리 내에 존재하는 정보는 즉 객체를 말한다.
JVM(Java Virtual Machine)의 메모리에 상주 되어있는 객체 데이터를 바이트 형태로 변환하는 기술
따라서 직렬화는 주로 객체들을 통째로 파일로 저장하거나 전송하고 싶을 때 사용한다. 나는 안드로이드 앱 개발을 할 때 액티비티간의 데이터를 전달할 때 인텐트를 사용하고 이 인텐트에 전달할 데이터를 추가한다.

복잡한 클래스의 객체를 이동하려는 경우에는 Serializable 또는 Parcelabe을 사용하여 직렬화하여 인텐트에 추가해서 데이터를 이동하면 된다.

역직렬화란?
byte로 변환된 Data를 원래대로 Object나 Data로 변환하는 기술을 역직렬화라고 한다.
직렬화된 바이트 형태의 데이터를 객체로 변환해서 JVM으로 상주시키는 형태
Serializable
Java에서는 Value Object를 쉽게 직렬화 하기위해 Serializable이라는 interface가 있다. 이는 Marker Interface로서 단순히 implement하는 것만으로도 JVM에게 직렬화가 가능하다는 것을 알려주기 때문에 사용하는게 매우 쉬운 편이다.

Serializable은 Android SDK가 아닌 표준 Java의 인터페이스이다.

### 실행화면

## Code

### build.gradle

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="8dp"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="이름"
            android:textSize="20dp"
            android:textStyle="bold" />

        <EditText
            android:id="@+id/etName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="이름을 입력해주세요." />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="성별"
            android:textSize="20dp"
            android:textStyle="bold" />

        <EditText
            android:id="@+id/etSex"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="남자/여자" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="나이"
            android:textSize="20dp"
            android:textStyle="bold" />

        <EditText
            android:id="@+id/etAge"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="나이를 입력해주세요."
            android:inputType="number" />
    </LinearLayout>

    <Button
        android:id="@+id/btnSend"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="유저 객체 전달하기" />

</LinearLayout>
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run{
            btnSend.setOnClickListener {
                val name = etName.text.toString()
                val sex = etSex.text.toString()
                val age = etAge.text.toString().toInt()

                val user = User(name, sex, age)

                Intent(this@MainActivity, UserInfoActivity::class.java).apply {
                    putExtra(UserInfoActivity.EXTRA_USER_INFO, user)
                }.let(::startActivity)
            }
        }
    }
}

data class User(
    val name: String,
    val sex: String,
    val age: Int
): Serializable
```

### activity_user_info.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="이름"
            android:textSize="20dp"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/tvName"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="성별"
            android:textSize="20dp"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/tvSex"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:orientation="horizontal">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="나이"
            android:textSize="20dp"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/tvAge"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    </LinearLayout>

    <Button
        android:id="@+id/btnSend"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="유저 객체 전달하기" />

</LinearLayout>
```

### UserInfoActivity.kt
``` kotlin
class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserInfoBinding

    private val user: User by lazy{
        intent.getSerializableExtra(EXTRA_USER_INFO) as User
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.run{
            tvName.text = user.name
            tvSex.text = user.sex
            tvAge.text = user.age.toString()
        }
    }

    companion object{
        const val EXTRA_USER_INFO = "userInfo"
    }
}
```

[참고 블로그](https://woovictory.github.io/2019/01/03/Android-What-is-serialization/)
