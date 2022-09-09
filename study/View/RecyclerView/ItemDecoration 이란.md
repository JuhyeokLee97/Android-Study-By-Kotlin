# RecyclerView.ItemDecoration 이란
[참고 블로그](https://proandroiddev.com/itemdecoration-in-android-e18a0692d848)
[ItemDecoration Samples]()

## Part 1: 직접 Item View Layout에 divider(구분선)을 넣지 말자

먼저 **ItemDecorcation**이란 무엇일까? 안드로이드 개발 공식분서에서는 아래와 같이 설명하고 있다.

> An ItemDecoration allows the application to add a special drawing and layout offset to specific item views from the adapter's data set. This can be useful for drawing dividers between items, highlights, visual grouping boundaries and more.

하지만 우리는 단순하게 **ItemDecoration**은 구분선을 넣기 위해 사용한다고만 생각하면 안된다.
**ItemDeocration**은 그 이상의 의미를 갖는다. 구분선이라고 하면 이름 그대로 각 아이템 사이에서만 그려질 수 있는 것이다. 하지만 **ItemDecoration**은 아이템의 사면(상,하,좌,우)에서 그려질 수 있다. 그리고 개발자로 하여금 크기나 그림을 직접 컨트롤할 수 있도록 되어 있다. 그러므로 **ItemDecoration**은 구분선 또는 삽화라고 볼 수 있다.

### Don't add dividers as views - It affets performance
