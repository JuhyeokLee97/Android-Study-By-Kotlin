## style.xml을 이용해 중복을 제거하자

아래 코드를 보면 **TextView** 와 **EditText** 에서 속성 값으로 **{fontFamily, textColor, textSize}** 가 중복되어 사용되는 것을 볼 수 있다. 만약 아래와 같은 **TextView** 또는 **EditText**가 100개 이상이 있다면 중복으로 인해 코드가 불필요하게 많아진다.  
이를 해결하기 위해서 **style.xml** 을 사용했다.

### style.xml 사용 전 - /res/layout/activity\_main.xml

```
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
        android:fontFamily="@font/roboto"
        android:text="@string/name"
        android:textAlignment="center"
        android:textColor="@color/black"
        android:textSize="20sp" />

    <EditText
        android:id="@+id/editText_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:fontFamily="@font/roboto"
        android:hint="@string/what_is_your_nickname"
        android:textAlignment="center"
        android:textColor="@color/black"
        android:textSize="20sp" />

</LinearLayout>
```

### style.xml 사용 후 - /res/layout/activity\_main.xml

```
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
        style="@style/NameStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/name"
        android:textAlignment="center" />

    <EditText
        android:id="@+id/editText_name"
        style="@style/NameStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/what_is_your_nickname"
        android:textAlignment="center" />

</LinearLayout>
```

## style.xml 파일 만들기

/res/values 폴더를 우클릭 한다.  
**New**/**Value Resource File** 를 클릭한다.

![style xml 파일 만들기](https://user-images.githubusercontent.com/40654227/137759898-a8e26cb7-3310-4f87-b5e5-08ac8bc9504a.png)

**New Resource File** 마법사에서 **File name** 에 style.xml을 **Directory name**에 values를 입력하고 **OK** 를 클릭한다.

![New Resource File 마법사](https://user-images.githubusercontent.com/40654227/137760402-5af30bb9-9b7f-4b41-be08-5345b455b171.png)

## style.xml에 style 추가하기 - /res/values/style.xml

```
<resources>

    <style name="NameStyle">
        <item name="android:fontFamily">@font/roboto</item>
        <item name="android:textColor">@color/black</item>
        <item name="android:textSize">20sp</item>
    </style>
</resources>
```
