# TabLayout

## TabLayout 이란
<p>
  
TabLayout provides a horizontal layout to display tabs.

Population of the tabs to display is done through TabLayout.Tab instances. You create tabs via newTab(). From there you can change the tab's label or icon via TabLayout.Tab.setText(int) and TabLayout.Tab.setIcon(int) respectively. To display the tab, you need to add it to the layout via one of the addTab(Tab) methods. For example:

``` java
 TabLayout tabLayout = ...;
 tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
 tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
 tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
 ```
  
You should add a listener via addOnTabSelectedListener(OnTabSelectedListener) to be notified when any tab's selection state has been changed.
You can also add items to TabLayout in your layout through the use of TabItem. An example usage is like so:

``` xml
 <com.google.android.material.tabs.TabLayout
         android:layout_height="wrap_content"
         android:layout_width="match_parent">

     <com.google.android.material.tabs.TabItem
             android:text="@string/tab_text"/>

     <com.google.android.material.tabs.TabItem
             android:icon="@drawable/ic_android"/>

 </com.google.android.material.tabs.TabLayout>
 

```


</p>

## TabLayout 전체 밑줄
<img src="https://user-images.githubusercontent.com/40654227/188272838-4f9ad575-01e1-4bb0-9af2-29a388232447.png" height=400/>

아래와 같이 `tab_layout_background.xml` drawable 파일을 만든다.
</br>
그리고 TabLayout의 속성 값 중 `android:background`에 셋팅한다.
``` xml
tab_layout_background.xml

<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:left="-3dp"
        android:right="-3dp"
        android:top="-3dp">
        <shape android:shape="rectangle">
            <solid android:color="#fff" />
            <stroke
                android:width="3dp"
                android:color="#FF0000" />
        </shape>
    </item>
</layer-list>
```
``` xml
<com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        ...
        android:background="@drawable/tab_layout_background"/>
```

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
