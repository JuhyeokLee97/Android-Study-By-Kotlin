# TabLayout

## TabLayout 이란
<p>
  
TabLayout provides a horizontal layout to display tabs.
**TabLayout**은 탭을 가로로 그려줄 수 있도록 지원한다.

표시할 탭을 만들기 위해서는 `TabLayout.Tab` 객체를 통해 생성할 수 있고, 객체의 내장함수 `newTab()` 함수를 통해 탭을 생성한다.
From there you can change the tab's label or icon via TabLayout.Tab.setText(int) and TabLayout.Tab.setIcon(int) respectively. 

To display the tab, you need to add it to the layout via one of the addTab(Tab) methods. 

For example:



</p>

## TabLayout.Indicator 설정: 밑줄 설정
### Indicator 색상 설정: 밑줄 색상 설정
`app:tabIndicatorColor`를 통해서 **Indicator** 색상을 설정 할 수 있다.
``` xml
<com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="56dp"
        ...
        app:tabIndicatorColor="@color/black"/>
```
### Indicator 높이 설정: 밑줄 높이 설정
`app:tabIndicatorHeight`를 통해서 **Indicator** 높이를 설정 할 수 있다.
``` xml
<com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"                                    
        android:layout_width="match_parent"
        android:layout_height="56dp"
        ...
        app:tabIndicatorHeight="20dp"/>
```
