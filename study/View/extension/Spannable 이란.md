# [Android] Spannable 이란

## 개요
### Spans 란
**Spannable**을 이해하기 위해서는 먼저 **Span** 객체를 이해하고 있는것이 좋다.
다음은 안드로이드 공식 문서에서 **Span**을 정의하는 내용이다.

>Spans are powerful markup objects that you can use to style text at a character or paragraph level. By attaching spans to text objects, you can change text in a variety of ways, including adding color, making the text clickable, scaling the text size, and drawing text in a customized way. Spans can also change  [`TextPaint`](https://developer.android.com/reference/android/text/TextPaint)  properties, draw on a  [`Canvas`](https://developer.android.com/reference/android/graphics/Canvas), and even change text layout.
>
>Android provides several types of spans that cover a variety of common text styling patterns. You can also create your own spans to apply custom styling.

간단하게 요약하자면 **Span**은 문자나 단락 수준에서 텍스트 스타일을 지정하는데 사용되는 객체이다. 예를 들어 특정 텍스트에 **색상을 추가**하거나 **텍스트를 클릭 가능**하게 만들기 및 **텍스트 크기 조절** 등 다양하게 스타일을 지정하여 텍스트에 연결시킬 수 있다. 더 나아가 **Span**은 `TextPaint` 속성을 변경하고 `Canvas` 에 그리며 텍스트 레이아웃까지 수정할 수 있다.

**Span** 객체를 만들어서 텍스트 스타일을 수정하는데 있어 사용할 수 있는 클래스는 3가지가 있다.
`SpannedString`, `SpannableString` 그리고 `SpannableStringBuilder`가 있는데 

``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // https://velog.io/@changhee09/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-Spannable%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%B4-%EB%AC%B8%EC%9E%90%EC%97%B4-%EA%BE%B8%EB%AF%B8%EA%B8%B0

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val text = "https://devgeek.tistory.com에 많이 놀러와주세요."
        val target = "https://devgeek.tistory.com"
        val startIdx = text.indexOf(target)
        val endIdx = startIdx + target.length

        val mClickableSpan = object: ClickableSpan(){
            override fun onClick(widget: View) {
                Toast.makeText(this@MainActivity, "test", Toast.LENGTH_SHORT).show()
            }
        }
        val spannable = text.toSpannable().apply {
            // 폰트 설정
            val styleSpan = StyleSpan(Typeface.BOLD)
            setSpan(styleSpan, startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 색상 설정
            val colorSpan = ForegroundColorSpan(R.color.blue)
            setSpan(colorSpan, startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 밑줄 설정
            val underLineSpan = UnderlineSpan()
            setSpan(underLineSpan, startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            // 클릭 이벤트 설정
            val clickableSpan = object: ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(this@MainActivity, "test", Toast.LENGTH_SHORT).show()
                }
            }
            setSpan(clickableSpan, startIdx, endIdx, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.textView.text = spannable
        binding.textView.movementMethod = LinkMovementMethod()
//        binding.textView.text = builder
    }
}
```
