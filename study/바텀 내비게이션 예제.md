## 실행화면

![](https://user-images.githubusercontent.com/40654227/147105040-f59a3989-2b8c-4eee-aa49-be52880e5cea.gif)

## 프로젝트 구조(이미지)

![](https://user-images.githubusercontent.com/40654227/147100924-47c0e7e9-6ca0-40f2-a98d-c919d1546c97.png)

## Code - Example

### menu\\bottom\_navigation\_menu.xml

하단 내비게이션에 들어갈 텍스트와 아이콘을 설정하기 위해 **menu\\bottom\_navigation\_menu.xml** 파일을 만든다.  
아래와 같이 **res** 디렉토리 안에서 **Android Resource Directory** 를 클릭하여 **menu** 디렉토리를 만들고, 해당 디렉토리에서 **bottom\_navigation\_menu.xml** 파일을 만든다.

![](https://user-images.githubusercontent.com/40654227/147101294-110963a9-64b5-45d9-8155-340e7f13fe8f.png)![](https://user-images.githubusercontent.com/40654227/147101769-e61d2722-908a-423c-9355-9592883456dc.png)

```
bottom_navigation_menu.xml

<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/home"
        android:icon="@drawable/ic_home"
        android:title="홈" />

    <item
        android:id="@+id/chatting"
        android:icon="@drawable/ic_chat"
        android:title="채팅" />

    <item
        android:id="@+id/myPage"
        android:icon="@drawable/ic_person_pin"
        android:title="마이페이지" />
</menu>
```

### activity\_main.xml

**MainActivity**의 **Layout** 파일에 **<BottomNavigationView>** 와 프레그먼트가 들어갈 **<FrameLayout>** 을 추가한다.

```
activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/fragmentContainer"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@id/bottomNavigation"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigation"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:itemRippleColor="@null"
        app:itemTextColor="@color/black"
        app:itemIconTint="@drawable/selector_bottom_navigation_menu_color"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:menu="@menu/bottom_navigation_menu" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

-   만든 메뉴를 바텀 내비게이션에 적용하기 위해 `app:menu="@menu/bottom_navigation_menu"` 속성값을 지정한다.
-   선택된 바텀 내비게이션의 아이콘의 색을 구분하기 위해 `app:itemIconTint="@drawable/selector_bottom_navigation_menu_color"` 속성값을 지정한다.

### drawable/selector\_bottom\_navigation\_menu\_color.xml

```
selector_bottom_navigation_menu_color.xml

<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="@color/black" android:state_checked="true" />
    <item android:color="@color/gray" android:state_checked="false" />
</selector>
```

### HomeFragment.kt

**MainActivity**에 들어갈 **HomeFragment**와 **layout**

```
HomeFragment.kt

class HomeFragment : Fragment(R.layout.fragment_home) {
}
```

### fragment\_home.xml

```
fragment_home.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".HomeFragment">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="홈 프래그먼트"
        android:textSize="40sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />



</androidx.constraintlayout.widget.ConstraintLayout>
```

### ChattingFragment.kt

**MainActivity**에 들어갈 **ChattingFragment**와 **layout**

```
ChattingFragment.kt

class ChattingFragment : Fragment(R.layout.fragment_chatting) {
}
```

### fragment\_chatting.xml

```
fragment_chatting.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ChattingFragment">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="채팅 프래그먼트"
        android:textSize="40sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MyPageFragment.kt

**MainActivity**에 들어갈 **MyPageFragment**와 **layout**

```
MyPageFragment.kt

class MyPageFragment : Fragment(R.layout.fragment_my_page) {  
}
```

### fragment\_my\_page.xml

```
fragment_my_page.xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".MyPageFragment">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="마이페이지 프래그먼트"
        android:textSize="40sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt

```
MainActivity.kt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val chattingFragment = ChattingFragment()
        val myPageFragment = MyPageFragment()


        replaceFragment(homeFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chatting -> replaceFragment(chattingFragment)
                R.id.myPage -> replaceFragment(myPageFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        // 현 Activity 에 연결된 Fragment 관리하는 supportFragmentManager 를 통해 Fragment 전환
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}
```
