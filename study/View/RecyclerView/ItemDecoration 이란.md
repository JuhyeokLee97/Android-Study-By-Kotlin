# RecyclerView.ItemDecoration 이란
[참고 블로그](https://proandroiddev.com/itemdecoration-in-android-e18a0692d848)
[ItemDecoration Samples]()

## Part 1: 직접 Item View Layout에 divider(구분선)을 넣지 말자

먼저 **ItemDecorcation**이란 무엇일까? 안드로이드 개발 공식분서에서는 아래와 같이 설명하고 있다.

> An ItemDecoration allows the application to add a special drawing and layout offset to specific item views from the adapter's data set. This can be useful for drawing dividers between items, highlights, visual grouping boundaries and more.

하지만 우리는 단순하게 **ItemDecoration**은 구분선을 넣기 위해 사용한다고만 생각하면 안된다.
**ItemDeocration**은 그 이상의 의미를 갖는다. 구분선이라고 하면 이름 그대로 각 아이템 사이에서만 그려질 수 있는 것이다. 하지만 **ItemDecoration**은 아이템의 사면(상,하,좌,우)에서 그려질 수 있다. 그리고 개발자로 하여금 크기나 그림을 직접 컨트롤할 수 있도록 되어 있다. 그러므로 **ItemDecoration**은 구분선 또는 삽화라고 볼 수 있다.

### 구분선을 View로서 추가하지 마세요 - 성능에 영향을 줍니다
나를 포함한 몇몇 개발자들은 RecyclerView에 구분선을 넣는데 있어 간단하고 빠르게 하기 위해서 View로 추가하는 경우가 있었다.
그 이유는 뻔했다. ListView에서 네이티브하게 구분선을 추가할 수 있는 방법을 제공했기 때문이다.
아래 xml과 같이 구분선(divider)를 추가할 수 있다.

``` xml
<ListView
    android:id="@+id/activity_home_list_view"
    android:layout_width="match_parent" 
    android:layout_height="match_parent"
    android:divider="@android:color/black"
    android:dividerHeight="8dp"/>

```
하지만 RecyclerView에서는 구분선(divider)를 직접적으로 추가할 수 없다.
그래서 우리는 구분선을 그려줄 수 있는 **ItemDecoration**을 사용할 필요가 있다.
하지만 또 개발자들은 **ItemDecoration**을 이용하지 않고 어렵긴 하지만 직접적으로 구분선을 View로서 추가하는 방법을 찾은 것이다.
``` xml
<LinearLayout android:orientation="vertical">
    <LinearLayout android:orientation="horizontal">
        <ImageView />
        <TextView />
    </LinearLayout>
    <View
        android:width="match_parent"
        android:height="1dp"
        android:background="#333" />
</LinearLayout>
```
이렇게 직적접으로 구분선을 추가할 경우 의도치 않은 안좋은 영향을 받을 수 있는데, 위와 같은 경우는 성능에 영향을 주게 된다.

layout에 구분선을 추가할 경우, 결국 View들의 개수가 증가되게 된다.
알다시피 layout에서 View의 개수가 줄어들수록 좋은 성능을 낼 수 있다.
때로는 구분선을 추가함으로써 layout의 계층관계가 깊어지는 경우도 있는데,
위와 같은 경우도 구분선을 추가함으로써 추가적인 LinearLayout(Outer Layout)을 만들어 계층관계가 1depth 깊어졌다.

### 구분선을 View로서 추가하지 마세요 - 부작용

만약 아이템에 특정한 애니메이션을 추가한다면, 해당 아이템에 애니메이션이 동작할 때 구분선(View)도 같이 동작하게 된다.
구체적인 상황을 예로 든다면 아래 이미지와 같다.
<img src="" height=600/>



