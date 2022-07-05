# Custom Binding Adapter Example(RecyclerView) in Kotlin

> [DataBinding](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/data%20binding/DataBinding%20Tutorial-1:%20DataBinding%20%EC%9D%B4%EB%9E%80.md)</br>
> [Binding Adapter](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/data%20binding/Binding%20Adpater.md)</br>
> [Custom Binding Adapter](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/data%20binding/Custom%20Binding%20Adapter.md)</br>

## 개요

<p>

  `Custom Binding Adapter`를 이용하여, 썸네일을 담당하는 이미지뷰에 이미지를 로드하고 가격을 담당하는 텍스트뷰에 정수를 입력하면 `원`을 추가하여 보여지도록 하는 리스트를 만든다. 

</p>

#### 실행화면

<p align="center">
  <img src="https://user-images.githubusercontent.com/40654227/177325296-0e067369-9bc3-485b-bfc7-e74a7633c282.png"/>
</p>  

## 프로젝트 구조
![프로젝트 구조](https://user-images.githubusercontent.com/40654227/177324546-a7c006ab-2289-49d9-9d52-05e6b8c8a83a.png)

## build.gradle(Module)

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

## AndroidManifest.xml

<p>
  
외부에서 이미지를 읽어오기 위해 **인터넷 권한 설정**을 허용해준다.</br>
권한 설정은 아래와 같이 ``<uses-permission android:name="android.permission.INTERNET" />``를 <manifest> 태그 안에 넣어준다.

  </p>

``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.devgeek.recyclerviewwithdatabindingsample">

    <uses-permission android:name="android.permission.INTERNET" />
    
    <application
      ...>
      ...
    </application>
    
</manifest>
```

## activity_main.xml
```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            tools:listitem="@layout/product_item" />

    </LinearLayout>
</layout>

```

## MainActivity.kt: Init RecyclerView

``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val productAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = productAdapter
        loadProducts()

    }

    private fun loadProducts() {
        val productList = mutableListOf<ProductModel>()
        productList.apply {
            add(
                ProductModel(
                    id = 0,
                    thumbNailUrl = "https://i.picsum.photos/id/229/100/100.jpg?hmac=rr3nwhpjaZelGscaBcDMtEB6tPeMEWkqcNMlnnOh5fs",
                    name = "상품명 - 1",
                    description = "상품 설명 - 1",
                    price = 10000
                )
            )

            add(
                ProductModel(
                    id = 1,
                    thumbNailUrl = "https://i.picsum.photos/id/846/100/100.jpg?hmac=MIrweaSnfc3PC93ZXP2_RIwTtSgyr4Rj59QNAKYgsfg",
                    name = "상품명 - 2",
                    description = "상품 설명 - 2",
                    price = 20000
                )
            )

            add(
                ProductModel(
                    id = 2,
                    thumbNailUrl = "https://i.picsum.photos/id/250/100/100.jpg?hmac=KjkOu1SZ6cWLdtP6WxtUrsqWRkO0mYRCVy0QIcH9gj0",
                    name = "상품명 - 3",
                    description = "상품 설명 - 3",
                    price = 30000
                )
            )

            add(
                ProductModel(
                    id = 3,
                    thumbNailUrl = "https://i.picsum.photos/id/620/100/100.jpg?hmac=bzyN8XTTVzqxBr2Y7efXe57NV2vj9pQluM4_-xmSFBU",
                    name = "상품명 - 4",
                    description = "상품 설명 - 4",
                    price = 40000
                )
            )
        }

        productAdapter.submitList(productList)
    }
}
```

## ProductModel.kt: RecyclerView item type

``` kotlin
data class ProductModel(
    val id: Int,
    val thumbNailUrl: String,
    val name: String,
    val description: String,
    val price: Int
)

```

## MyBindingAdapter.kt: Custom Binding Adapter
> [Custom Binding Adapter](https://github.com/JuhyeokLee97/Android-Study-By-Kotlin/blob/main/study/data%20binding/Custom%20Binding%20Adapter.md)
``` kotlin
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load

object MyBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        view.load(url)
    }

    @BindingAdapter("price")
    @JvmStatic
    fun bindPrice(view: TextView, price: Int) {
        view.text = "${price}원"
    }
}
```

## product_item.xml: RecyclerView Item UI
``` xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="productModel"
            type="com.devgeek.recyclerviewwithdatabindingsample.ProductModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="20dp">

        <ImageView
            android:id="@+id/ivThumbnail"
            imageUrl="@{productModel.thumbNailUrl}"
            android:layout_width="100dp"
            android:layout_height="100dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tvName"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="20dp"
            android:text="@{productModel.name}"
            android:textSize="20sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toEndOf="@id/ivThumbnail"
            app:layout_constraintTop_toTopOf="@id/ivThumbnail"
            tools:text="이름" />

        <TextView
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="@{productModel.description}"
            app:layout_constraintBottom_toTopOf="@id/tvPrice"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="@id/tvName"
            app:layout_constraintTop_toBottomOf="@id/tvName"
            tools:text="상품 설명" />

        <TextView
            android:id="@+id/tvPrice"
            price="@{productModel.price}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textColor="@color/black"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            tools:text="10000원" />


    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>

```

## ProductAdapter.kt
>[ListAdapter]()
``` kotlin

class ProductAdapter : ListAdapter<ProductModel, ProductAdapter.ViewHolder>(diffUtil) {

    /** Create new views (invoked by the layout manager) **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     *
     * Get element from your dataset at this position and replace the
     * contents of the view with that element
     * */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productModel = currentList[position]
    }

    inner class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            /** Define click listener for the ViewHolder's View. */
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
```
