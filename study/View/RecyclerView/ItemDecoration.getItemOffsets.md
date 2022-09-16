# ItemDecoration.getItemOffSets 이란

<strong>
  
ItemDecoration.getItemOffSets(</br>
        @NonNull Rect outRect,</br>
        @NonNull View view, </br>
        @NonNull RecyclerView parent, </br>
        @NonNull State state </br>
)

</strong>

안드로이드 공식 문서에서는 아래와 같이 설명되어있다.

> Retrieve any offsets for the given item. 
Each field of outRect specifies the number of pixels that the item view should be inset by, similar to padding or margin. 
The default implementation sets the bounds of outRect to 0 and returns.
If this ItemDecoration does not affect the positioning of item views, 
it should set all four fields of outRect (left, top, right, bottom) to zero before returning.
>
> if you need to access Adapter for additional data, you can call getChildAdapterPosition(View) to get the adapter position of the View.
>
>Params:
>
>`outRect` – Rect to receive the output.
>
>`view` – The child view to decorate
>
>`parent` – RecyclerView this ItemDecoration is decorating
>
>`state` – The current state of RecyclerView.

간단하게 정리하자면, RecyclerView의 item의 offset을 읽어오는 것이다. 근데 **outRect**의 속성값(`left`, `right`, `top`,`bottom`)을 이용해서 패딩(여백)을 픽셀 단위로 설정할 수 있다. 단, 특별히 설정하지 않으면 default 값은 0이다.

다음은 `getItemOffsets()`를 재구현하여 사용한 예제들이다.

### SpacingItemDecoration.kt!
##### 적용 전(좌) 적용 후(우)

<p>
<img src="https://user-images.githubusercontent.com/40654227/190650453-8eefc320-88ae-4d1c-9672-66a748e65881.png" width=300 align='left'/>
<img src="https://user-images.githubusercontent.com/40654227/190650173-8c8f5432-8599-4275-81a0-49a7f17a60de.png" width=300 align='center'/>
</p>

``` kotlin
class PaddingItemDecoration(private val spacing: Int): RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = spacing
            right = spacing
            top = spacing
            bottom = spacing
        }
    }
}
```
