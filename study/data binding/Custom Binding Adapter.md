# Custom Binding Adapter

### 개요
<p>

- **커스텀 바인딩 어댑터**를 이용하면 데이터를 바인딩 하기 전에 동작하는 로직을 추가할 수 있다.
- 일반적으로 커스텀 바인딩 어댑터는 Data Binding Library에서 제공하는 어댑터가 없는 경우 사용한다.

</p>

<p>

이번 글에서는 뷰의 `Visibility`, `Load Image` 그리고 `Format String `을 다루도록 한다.

</p>

### View.visivility
1. **CustomBindingAdapter** 구현을 위해 `kotlin-kapt`를 아래와 같이 `build.gradle(Module)` 파일의 **plugins**에 추가한다.
```kotlin
plugins {  
  ...
  id 'kotlin-kapt'  
}
```
2. 프로젝트에 `BindingAdpater.kt` Object 파일을 하나 생성한다.
<!-- 프로젝트 구조 이미지 -->

``` kotlin
@BindingAdapter("android:visibility")
@JVMStatic
fun View.setVisibility(visible: Boolean) {
  visibility = if (visible) {
    View.VISIBLE
  } else {
    View.GONE
  }
}

```
### ImageView.load

### TextView.text
