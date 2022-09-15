# ItemDecoration.getItemOffSets 이란

<strong>
  
ItemDecoration.getItemOffSets(
        @NonNull Rect outRect,
      </br>
        @NonNull View view, </br>
        </br>
        @NonNull RecyclerView parent, </br>
        </br>
        @NonNull State state 
)

</strong>

Retrieve any offsets for the given item. 
Each field of outRect specifies the number of pixels that the item view should be inset by, similar to padding or margin. 
The default implementation sets the bounds of outRect to 0 and returns.
If this ItemDecoration does not affect the positioning of item views, 
it should set all four fields of outRect (left, top, right, bottom) to zero before returning.

if you need to access Adapter for additional data, you can call getChildAdapterPosition(View) to get the adapter position of the View.

Params:

`outRect` – Rect to receive the output.

`view` – The child view to decorate

`parent` – RecyclerView this ItemDecoration is decorating

`state` – The current state of RecyclerView.

### PaddingItemDecoration.kt
``` kotlin
class PaddingItemDecoration(private val padding: Int): RecyclerView.ItemDecoration(){

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = padding
            right = padding
            top = padding
            bottom = padding
        }
    }
}
```
