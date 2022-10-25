# Intent로 Data Class 타입 객체 넘기기

## 개요
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

