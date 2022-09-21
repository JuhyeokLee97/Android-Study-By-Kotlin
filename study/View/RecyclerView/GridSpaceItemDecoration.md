# GridSpaceItemDecoration

## 개요
RecyclerView를 통해서 Grid 형태의 아이템들을 그려야할 때, 단순히 아이템의 레이아웃만으로 일정한 여백을 추가하기는 까다롭다.
예를 들어 `SpanCount = 2`이고 각 아이템에 `20dp`에 해당하는 Padding 값을 할당했다고 한다면, 다음과 같이 아이템 간의 사이는 `40dp`가 되고 상하좌우의 끝의 여백만이 `20dp`가 될 것이다.

<img width="439" alt="image" src="https://user-images.githubusercontent.com/40654227/191238594-b80c7c2a-52c0-4f50-9d28-120ae6f96477.png">

물론 RecyclerView의 Padding 값을 `20dp`로 추가로 설정한다면 모든 간격이 `40dp`로 일정하게 만들수는 있다.
하지만 아이템의 여백 설정을 위해 2가지 아이템(2가지 레이아웃 파일)에서 관리하는건 유지보수 하는 입장에서 좋지 않다.

그래서 ItemDecoration을 이용해서 Grid 형태의 아이템들에 같은 여백을 줄 수 있는 GradSpaceItemDecoration 을 만들어 보겠다.

### 실행 화면
#### 상하좌우 끝의 여백을 포함해 모두 같은 여백을 갖음
<img src="" height=600/>
#### 상하좌우 끝의 여백을 포함하지 않고 아이템 간의 여백만을 갖음
<img src="" height=600/>

## Code

### GridSpaceItemDecoration.kt
``` kotlin
/**
 * GridItemDecoration
 *
 * @param includeEdge: 상하좌우 끝 아이템의 바깥 쪽에 'Space'를 추가할 것인지 선택 */
class GridItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        /**
         * 아이디어
         * 첫번째 행(row-1)에 있는 아이템들에만 상단에 space를 추가한다.
         * 그리고 모든 아이템의 하단에 space를 추가한다.
         * 그러면 모든 아이템의 상,하단에 같은 값의 space를 갖는다.
         *
         * 마지막 열(colom-N)에 있는 아이템들에만 우측에 space를 추가한다.
         * 그리고 모든 아이템의 좌측에는 space를 추가한다.
         * 그러면 모든 아이템의 좌,우에 같은 값의 space를 갖는다.
         */
        if (includeEdge) {
            /** 첫번째 행(row-1)에 있는 아이템인 경우 상단에 [spacing] 만큼의 padding을 추가한다. */
            if (position < spanCount) {
                outRect.top = spacing
            }
            /** 모든 아이템의 좌측에는 [spacing] 만큼의 padding을 추가한다. */
            outRect.left = spacing
            /** 마지막 열(column-N)에 있는 아이템인 경우 우측에 [spacing] 만큼의 padding을 추가한다. */
            if (spanCount == column + 1) {
                outRect.right = spacing
            }
            /** 모든 아이템의 하단에는 [spacing] 만큼의 padding을 추가한다. */
            outRect.bottom = spacing
        } else {
            /** 첫번째 행 이상(row-2)인 아이템들 부터 상단에 [spacing] 만큼의 padding을 추가한다 */
            if (position >= spanCount) {
                outRect.top = spacing
            }
            /** 첫번째 열이 아닌(None Column-1) 아이템들부터 좌측에 [spacing] 만큼의 padding을 추가한다. */
            if (0 != column) {
                outRect.left = spacing
            }
        }

    }
}
```
