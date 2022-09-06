# RecyclerView.ItemDecoration

## RecyclerView.ItemDecoration 이란
**RecyclerView.ItemDecoration**이란 안드로이드 개발 공식문서에 따르면, 
**ItemDecoration**을 사용하면 어댑터의 데이터 셋으로부터 특정한 아이템 뷰에 대해 **drawing**이나 **layout offset**을 추가할 수 있다고 한다.
이로 인해 각 항목을 구분짓는 구분선을 그리거나, 강조표시하거나 그리고 디자인적으로 그룹화하는 등을 할 수 있다고 한다.

### ItemDcoration을 이용하여 기본 Divider 만들기
#### 실행화면
##### 적용 전(좌) 적용 후(우)

<p>
<img src="https://user-images.githubusercontent.com/40654227/188630330-2c5a8b92-42bf-4125-a3f0-f1223fe025c1.png" width=300 align='left'/>
<img src="https://user-images.githubusercontent.com/40654227/188630668-ea4f37d8-00ab-48b1-a16f-121f9c0efd27.png" width=300 align='center'/>
</p>


  
`RecyclerView.ItemDecoration`을 상속 받아 구현된 내장 클래스 `DividerItemDecoration`을 이용하여 기본적인 **Divider**를 다음과 같이 구현할 수 있다.

``` kotlin

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val itemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemAdapter.submitList(itemList)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerView.adapter = itemAdapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }
}
```

먼저 `val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)`와 같이 `DividerItemDecoration` 객체를 생성한다.

`DividerItemDecoration(context, orientation)`: **context**에는 RecyclerView가 보여질 View(Activity)의 context를 넣어주고, **orientation**에는 `DividerItemDecoration`에서 제공하는 `VERTICAL` 또는 `HORIZONTAL`를 사용하면 된다.

그리고 RecyclerView에 생성한 `dividerItemDecoration` 객체를 연결하기 위해서 `RecyclerView.addItemDecoration()`함수를 이용한다.

  

### ItemDecoration을 이용하여 Custon Divider 만들기
#### 실행영상

### ItemDecoration을 이용하여 Padding 지정하기
