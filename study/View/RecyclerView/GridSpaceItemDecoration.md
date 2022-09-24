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
<img src="https://user-images.githubusercontent.com/40654227/192083896-f27bcb72-16b5-45db-9184-82b45554cc6c.png" height=500/>

#### 상하좌우 끝의 여백을 포함하지 않고 아이템 간의 여백만을 갖음
<img src="https://user-images.githubusercontent.com/40654227/192083898-dd3b2e55-b6fc-4308-aaf7-278719e620a2.png" height=500/>


## Code 

### GridSpaceItemDecoration.kt - 상하좌우 끝의 여백을 포함해 모두 같은 여백을 

#### 아이디어
`getItemOffSets()` 함수를 이용해서, 첫번째 행(row-1)에 있는 아이템들에만 상단에 여백을 주고, 모든 아이템에는 하단에 여백을 준다.
그리고 같은 개념으로 첫번째 열(column-1)에 있는 아이템들만 좌측에 여백을 주고, 모든 아이템에는 우측에 여백을 준다.

``` kotlin
class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작

        /** 첫번째 행(row-1)에 있는 아이템인 경우 상단에 [space] 만큼의 여백을 추가한다 */
        if (position < spanCount){
            outRect.top = space
        }
        /** 마지막 열(column-N)에 있는 아이템인 경우 우측에 [space] 만큼의 여백을 추가한다 */
        if (column == spanCount){
            outRect.right = space
        }
        /** 모든 아이템의 좌측과 하단에 [space] 만큼의 여백을 추가한다. */
        outRect.left = space
        outRect.bottom = space

    }

}
```

### GridSpaceItemDecoration.kt - 상하좌우 끝의 여백을 포함하지 않고 아이템 간의 여백만을 갖음

``` kotlin
class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작

        /** 첫번째 행(row-1) 이후부터 있는 아이템에만 상단에 [space] 만큼의 여백을 추가한다. 즉, 첫번째 행에 있는 아이템에는 상단에 여백을 주지 않는다.*/
        if (position >= spanCount){
            outRect.top = space
        }
        /** 첫번째 열이 아닌(None Column-1) 아이템들만 좌측에 [space] 만큼의 여백을 추가한다. 즉, 첫번째 열에 있는 아이템에는 좌측에 여백을 주지 않는다. */
        if (column != 1){
            outRect.left = space
        }

    }

}
```

### build.gradle(Module): ViewBinding 사용
``` kotlin
android {
    ... 
    buildFeatures{
        viewBinding true
    }
}
```

### item_layout.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/white">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="44dp"
        android:layout_height="44dp"
        android:layout_marginVertical="5dp"
        android:layout_marginStart="8dp"
        android:src="@drawable/ic_launcher_background"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:text="TITLE"
        app:layout_constraintBottom_toBottomOf="@id/imageView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/imageView"
        app:layout_constraintTop_toTopOf="@id/imageView" />
    
</androidx.constraintlayout.widget.ConstraintLayout>
```

### ItemAdapter.kt
``` kotlin
class ItemAdapter: RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var itemList = arrayListOf<String>()

    inner class ViewHolder(private val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(title: String){
            binding.run{
                tvTitle.text = title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemLayoutBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(arrayList: ArrayList<String>){
        itemList = arrayList
        notifyDataSetChanged()
    }
}
```

### activity_main.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#ECECEC"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:spanCount="2"
        tools:listitem="@layout/item_layout" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val recyclerViewAdapter = ItemAdapter().apply {
            val itemList = arrayListOf(
                "타이틀 - 1",
                "타이틀 - 2",
                "타이틀 - 3",
                "타이틀 - 4",
                "타이틀 - 5",
                "타이틀 - 6",
                "타이틀 - 7"
            )
            this.submitList(itemList)
        }

        binding.recyclerView.adapter = recyclerViewAdapter
        binding.recyclerView.run {
            adapter = recyclerViewAdapter
            val spanCount = 2
            val space = 20
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
    }
}
```
