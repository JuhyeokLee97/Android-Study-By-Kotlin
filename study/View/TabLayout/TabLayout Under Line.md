
# [Android] TabLayout Under Line

## 개요

TabLayout 하단 전체에 밑줄을 그리는 방법을 간단히 공유합니다.</br>
배달의 민족 앱에서 하단 "찜"탭 화면에 진입하면 상단 탭에 회색 밑줄이 있는데 이를 비슷하게 구현했습니다.


### 예시: 배달의 민족  > 찜
<img src="https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/assets/40654227/07a108e6-4de0-4c6b-8605-ba932ec6ffa1" width=400/>


### 결과 화면
<img src="https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/assets/40654227/224acf43-f612-467c-947e-784f576b040e" width=400/>


## Code
### rectangle_underline_gray.xml: TabLayout's Background
``` xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="@color/gray" />
        </shape>
    </item>
    <item>
        <inset android:insetBottom="1dp">
            <shape android:shape="rectangle">
                <solid android:color="@color/white" />
            </shape>
        </inset>
    </item>
</layer-list>
```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:background="@drawable/rectangle_underline_gray"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:tabGravity="fill"
        app:tabIndicatorHeight="3dp"
        app:tabIndicatorColor="@color/orange"
        app:tabIndicatorFullWidth="true"
        app:tabTextColor="@color/orange"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```
