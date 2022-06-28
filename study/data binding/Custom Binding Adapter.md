# Custom Binding Adapter

### 개요
<p>

- **커스텀 바인딩 어댑터**를 이용하면 데이터를 바인딩 하기 전에 동작하는 로직을 추가할 수 있다.
- 일반적으로 커스텀 바인딩 어댑터는 Data Binding Library에서 제공하는 어댑터가 없는 경우 사용한다.

</p>

<p>

이번 글에서는 뷰의 `Visibility`, `Load Image` 그리고 `Format String `을 다루도록 한다.

</p>

#### 활용 영상

<p align="center">

  <img src="https://user-images.githubusercontent.com/40654227/176098331-f1ba9c44-1c5f-431d-888a-c33b39195b8c.gif" height=300/>
  
</p>

### 프로젝트 셋팅
1. **CustomBindingAdapter** 구현을 위해 `kotlin-kapt`를 아래와 같이 `build.gradle(Module)` 파일의 **plugins**에 추가한다.
```kotlin
plugins {  
  ...
  id 'kotlin-kapt'  
}
```

2. **DataBinding**사용을 위해 ``buildFeatures{ dataBinding true  }``를 아래와 같이 `build.gradle(Module)` 파일의 **android{}** 에 추가한다.
``` kotlin
android {
    ...
    buildFeatures{
        dataBinding true
    }
}
```

3. 아래와 같이 프로젝트에 `MyBindingAdpater.kt` Object 파일을 하나 생성한다.

![image](https://user-images.githubusercontent.com/40654227/176097174-f6bf57d8-99cb-4601-8152-de0dc0583360.png)


### CustomBindingAdapter 구현: View.visivility

<p>
  일반 뷰의 `View.Visibility`를 관리하도록 한다.
</p>  

#### Code in `MyBindingAdpater.kt`

``` kotlin

    @BindingAdapter("isVisible")
    @JvmStatic
    fun isVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

```


### CustomBindingAdapter 구현: ImageView.load

<p>
  
  이미지뷰에서 url값을 통해 이미지를 그리도록 한다.
  단). url을 통해서 이미지를 로드하기 위해서는 **[coil-kt](https://github.com/coil-kt/coil)**에서 제공하는 의존성을 추가해야한다.
  **Coli.load**사용을 위해 ``implementation "io.coil-kt:coil:2.1.0"``를 아래와 같이 `build.gradle(Module)` 파일의 **dependencies{ }** 에 추가한다.
  ``` kotlin
  dependencies {
    ...
    implementation "io.coil-kt:coil:2.1.0"
  }
  
  ```
  
</p>  

#### Code in `MyBindingAdpater.kt`

<p>
  
``` kotlin
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        view.load(url)
    }
```
  
</p>

### CustomBindingAdapter 구현: TextView.text

#### Code in `MyBindingAdpater.kt`
``` kotlin
    @BindingAdapter("price")
    @JvmStatic
    fun bindPrice(view: TextView, price: Int) {
        view.text = "${price}원"
    }
```
