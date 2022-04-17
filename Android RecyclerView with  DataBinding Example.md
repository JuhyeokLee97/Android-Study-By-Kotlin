## 개요

### 실행화면

![](https://user-images.githubusercontent.com/40654227/144453880-a12bef11-f878-43a2-b494-2284fb0cc620.gif)

## 빌드환경

**bulid.gradle(Module)** 파일 에서 **android { }** 태그 안에 속성 값으로 아래 코드를 추가한다.

```
dataBinding {
    enable = true
}
```

## 프로젝트 구조(이미지)

![](https://user-images.githubusercontent.com/40654227/144451200-76eef9cd-e4cc-4a2c-ba94-576dc15a6afc.png)

## Code - Example

### build.gradle(Module)

**\[Gradle Scripts\]** > **\[build.gradle\]**

```

plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    compileSdk 31

    defaultConfig {
        applicationId "com.example.recyclerviewapplication"
        minSdk 26
        targetSdk 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

    dataBinding {
        enabled = true
    }
}

dependencies {

    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.0'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.2'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```

### activity\_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/tvChicken"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:paddingVertical="10dp"
            android:text="치킨"
            android:textColor="@color/black"
            android:textSize="30sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <View
            android:id="@+id/divider"
            android:layout_width="0dp"
            android:layout_height="2dp"
            android:background="@color/black"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tvChicken" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rvChickenList"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:overScrollMode="never"
            android:paddingBottom="50dp"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/divider"
            tools:listitem="@layout/list_item_of_chicken" />


    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBind()
    }

    private fun initBind() {
        val adapter = ChickenRecyclerViewAdapter(getItems())
        binding.rvChickenList.adapter = adapter
    }

    private fun getItems(): ObservableArrayList<ChickenListItemViewModel> {
        val list = ObservableArrayList<ChickenListItemViewModel>()

        val item1 = ChickenListItemViewModel()
        with(item1) {
            thumbnail.set(applicationContext.getDrawable(R.drawable.ic_goobne))
            title.set("굽네치킨")
            score.set(4.9)
        }
        list.add(item1)

        val item2 = ChickenListItemViewModel()
        with(item2) {
            thumbnail.set(applicationContext.getDrawable(R.drawable.ic_bhc))
            title.set("BHC")
            score.set(5.0)
        }
        list.add(item2)

        val item3 = ChickenListItemViewModel()
        with(item3) {
            thumbnail.set(applicationContext.getDrawable(R.drawable.ic_bbq))
            title.set("BBQ")
            score.set(4.8)
        }
        list.add(item3)

        val item4 = ChickenListItemViewModel()
        with(item4) {
            thumbnail.set(applicationContext.getDrawable(R.drawable.ic_restaurant))
            title.set("치킨집-1")
            score.set(4.8)
        }
        list.add(item4)

        val item5 = ChickenListItemViewModel()
        with(item5) {
            thumbnail.set(applicationContext.getDrawable(R.drawable.ic_restaurant))
            title.set("치킨집-2")
            score.set(4.8)
        }
        list.add(item5)



        return list
    }
}
```

### ChickenListItemViewModel.kt

```
class ChickenListItemViewModel {
    var thumbnail = ObservableField<Drawable>()
    var title = ObservableField<String>()
    var score = ObservableDouble()
}
```

### list\_item\_of\_chicken.xml

```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="data"
            type="com.example.recyclerviewapplication.ChickenListItemViewModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/ivThumbnail"
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:padding="5dp"
            android:src="@{data.thumbnail}"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:src="@drawable/ic_launcher_background" />

        <TextView
            android:id="@+id/tvTitle"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:paddingHorizontal="5dp"
            android:text="@{data.title}"
            android:textColor="@color/black"
            android:textSize="25sp"
            app:layout_constraintStart_toEndOf="@id/ivThumbnail"
            app:layout_constraintTop_toTopOf="@id/ivThumbnail"
            tools:text="식당 이름" />

        <ImageView
            android:id="@+id/ivStar"
            android:layout_width="25dp"
            android:layout_height="25dp"
            android:layout_marginBottom="20dp"
            android:src="@drawable/ic_star"
            app:layout_constraintBottom_toBottomOf="@id/ivThumbnail"
            app:layout_constraintStart_toStartOf="@id/tvTitle" />

        <TextView
            android:id="@+id/tvScore"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="5dp"
            android:text="@{String.valueOf(data.score)}"
            android:textSize="14sp"
            app:layout_constraintBottom_toBottomOf="@id/ivStar"
            app:layout_constraintStart_toEndOf="@id/ivStar"
            app:layout_constraintTop_toTopOf="@id/ivStar"
            tools:text="별점" />

        <View
            android:layout_width="0dp"
            android:layout_height="1dp"
            android:layout_marginTop="10dp"
            android:background="#535050"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/ivThumbnail" />


    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

### ChickenRecyclerViewAdapter.kt

```
class ChickenRecyclerViewAdapter(var itemList: ObservableArrayList<ChickenListItemViewModel>) :
    RecyclerView.Adapter<ChickenRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemOfChickenBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(val binding: ListItemOfChickenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChickenListItemViewModel) {
            binding.data = item
        }
    }
}
```
