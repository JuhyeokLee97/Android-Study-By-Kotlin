# getString() Crash Issue

## 개요
문자열 리소스(strings.xml)를 `layout`파일이 아닌 `class`에서 사용할 때, Android 자체 함수 `getString(R.string.str_name)`을 통해서 문자열을 읽을 수 있다.
</br>
하지만 여기서 주의해야하는 것이 있다. `getString()` 함수를 호출할 때, `Context`를 이용해서 호출해야하는데 그렇지 않은 경우 앱이 죽는 경우가 있다.
</br>
나같은 경우는 앱을 개발하는 중, 다이얼로그에 문자열 리소스를 통해서 사용자에게 정보를 노출시키려고 했지만 `Context`를 이용하지 않고 `getString()`을 호출했다 다이얼로그를 호출하면 앱이 죽는 현상을
경험했다.

## 문자열 리소스(strings.xml)란

### 문자열 리소스(strings.xml) - /res/values/strings.xml

안드로이드 프로젝트에서 /res/value 경로 아래에 strings.xml이라는 문자열 리소스 파일이 있다.

![strings xml 위치](https://user-images.githubusercontent.com/40654227/137754133-f1a851c5-d960-4d26-bdef-0408242f37cc.png)

### 사용할 문자열 추가하기

프로젝트를 처음 생성하면 아래와 같이 기본으로 app\_name이라는 이름을 가진 문자열 리소스가 있다. app\_name과 같은 형태로 태그로 작성하려는 문자열을 감싸주면 된다. 반드시 name=“” 속성을 사용해서 해당 문자열 리소스의 이름을 지정해 주어야 하며, 지정한 이름으로 코드에서 사용할 수 있다.

```
<resources>
    <string name="app_name">DataBindingBasics</string>
    <string name="name">devGeek</string>
    <string name="what_is_your_nickname">What is your nickname?</string>
</resources>
```

### 소스에서 문자열 리소스 사용하기 - activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingHorizontal="20dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/name_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/name"
        android:textAlignment="center"
        android:textColor="@color/black"
        android:textSize="20sp" />

    <EditText
        android:id="@+id/editText_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/what_is_your_nickname"
        android:textAlignment="center"
        android:textColor="@color/black"
        android:textSize="20sp" />

</LinearLayout>
```

**<TextView>** 태그의 속성 중 android:text=""에 **<string>** 태그의 name 속성 값을 넣어준다.  
ex). android:text="@string/name"  
ex). android:text="@string/what\_is\_your\_nickname

### 결과 화면

![실행화면](https://user-images.githubusercontent.com/40654227/137757345-923b6dbd-b034-4f5d-9fd1-20c81224181c.png)
