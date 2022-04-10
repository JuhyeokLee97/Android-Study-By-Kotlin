## 문자열 리소스(strings.xml) - /res/values/strings.xml

안드로이드 프로젝트에서 /res/value 경로 아래에 strings.xml이라는 문자열 리소스 파일이 있다.

![strings xml 위치](https://user-images.githubusercontent.com/40654227/137754133-f1a851c5-d960-4d26-bdef-0408242f37cc.png)

## 사용할 문자열 추가하기

프로젝트를 처음 생성하면 아래와 같이 기본으로 app\_name이라는 이름을 가진 문자열 리소스가 있다. app\_name과 같은 형태로 태그로 작성하려는 문자열을 감싸주면 된다. 반드시 name=“” 속성을 사용해서 해당 문자열 리소스의 이름을 지정해 주어야 하며, 지정한 이름으로 코드에서 사용할 수 있다.

```
<resources>
    <string name="app_name">DataBindingBasics</string>
    <string name="name">devGeek</string>
    <string name="what_is_your_nickname">What is your nickname?</string>
</resources>
```

## 소스에서 문자열 리소스 사용하기 - activity\_main.xml

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

## 결과 화면

![실행화면](https://user-images.githubusercontent.com/40654227/137757345-923b6dbd-b034-4f5d-9fd1-20c81224181c.png)
