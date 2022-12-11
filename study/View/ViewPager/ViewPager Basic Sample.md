# [Android] ViewPager Basic Sample

## ViewPager 란

<p>

- **ViewPager**는 사용자가 각 페이지들을 좌우로 스와이프 할 수 있도록 하는 **Layout Manager**다.
- 스와이프되는 페이지들은 `Activity`를 사용하지 않고 `Fragment`를 사용한다.
- 대표적인 예로는 `Youtube`를 이야기할 수 있는데, 사용자가 화면을 전환하기 위해서 오른쪽 또는 왼쪽으로 이동하는 경우다.
- 또 다른 사용 예로는 사용자가 앱을 처음 시작할 때, 앱을 통해 사용법을 안내하는 데에도 사용된다.

</p>

### Adapter

<p>


**ViewPager**에 보여질 페이지들을 연결시키기 위해서는 **Adapber**를 구현해야한다.
**PagerAdapter**는 **FragmentPagerAdapter**와 **FragmentStatePagerAdapter**에 의해 확장된 기본 클래스이다.

</p>

#### `FragmentPagerAdapter  ` vs `FragmentStatePagerAdapter`
- **FragmentStatePagerAdapter**
	- 메모리상에 현제 보여지는 화면만을 유지하며 메모리를 효율적으로 사용한다.
	- Fragment의 개수를 알 수 없는 경우와 같이, 동적인 상황에서 사용하는게 좋다.
- **FragmentPagerAdapter**
	- Fragment의 개수를 알 수 있는 경우에만 사용한다.
	- 예를들어 3개의 탭을 갖는 앱이 있을 때, 3개의 탭의 개수는 변경되지 않고 3개의 Fragment를 사용하기 때문에 **FragmentPagerAdapter**를 사용하기에 적절하다.

이번 포스팅에서 ViewPager Adapter를 구현하는데 있어, **FragmentPagerAdapter**를 사용한다.

## 실행영상
<img src="https://user-images.githubusercontent.com/40654227/183327423-43989139-0ec4-4aac-b946-2d8c92a794b0.gif" height=400/>

## Code
### MyViewPagerAdapter.kt: `Adapter`구현, `FragmentPagerAdapter`상속
``` kotlin
private val fragments = ArrayList<Fragment>()
private val fragmentTitles = ArrayList<String>()

/**
 * @param fm A FragmentManager manages Fragments in Android, specifically, it handles transactions between fragments. A transaction is a way to add, replace, or remove fragments.
 */
class MyViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /**
     * This method is responsible for populating the fragments and fragmentTitle lists.
     * which hold the fragments and titles respectively.
     * */
    fun add(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    /**
     * This method returns the number of fragments to display. (Required to Override)
     * */
    override fun getCount(): Int = fragments.size

    /**
     * @param position fragments' position index
     * Returns the fragment at the pos index. (Required to override)
     */
    override fun getItem(position: Int): Fragment = fragments[position]

    /**
     * Similar to getItem() this methods returns the title of the page at index pos.
     * */
    override fun getPageTitle(position: Int): CharSequence= fragmentTitles[position]
}
```
#### 함수 설명
- `getCount()`: 보여질 프래그먼트 목록의 개수를 반환한다.
- `getItem(position: Int)`: `position`에 해당하는 프래그먼트를 반환한다.
- `getPageTitle(position: Int)`: `getItem(position)`함수와 비슷하게, `position`에 해당하는 타이틀을 반환한다.
- `add(fragment: Fragment, title: String)`: 이 함수는 `fragments`와 `fragmentTittles` 리스트에 데이터를 저장한다.

## Create 3 Fragments For 3 Pages
### FirstFragment.kt
``` kotlin
class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
}
```
### fragment_first.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#4CAF50"
    tools:context=".FirstFragment">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="Page 1"
        android:textColor="@color/white"
        android:textSize="60sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```
### SecondFragment.kt
``` kotlin
class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
}
```
### fragment_second.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#4CAF50"
    tools:context=".SecondFragment">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="Page 2"
        android:textColor="@color/white"
        android:textSize="60sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
### ThirdFragment.kt
``` kotlin
class ThirdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_third, container, false)
    }
}
```
### fragment_third.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#4CAF50"
    tools:context=".ThirdFragment">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:text="Page 3"
        android:textColor="@color/white"
        android:textSize="60sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### activity_main.xml
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tab_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:tabGravity="fill"
        app:tabMode="fixed"
        app:tabTextColor="@color/black" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tab_layout" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        binding = activityMainBinding
        // Set up the adapter
        val viewPagerAdapter = MyViewPagerAdapter(supportFragmentManager)

        // Add Fragments
        viewPagerAdapter.add(FirstFragment(), "Page 1")
        viewPagerAdapter.add(SecondFragment(), "Page 2")
        viewPagerAdapter.add(ThirdFragment(), "Page 3")

        // Set the adapter
        binding?.viewpager?.adapter = viewPagerAdapter

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        binding?.let {
            it.tabLayout.setupWithViewPager(it.viewpager)
        }
    }
}
```

#### 참고
##### [ ViewPager Using Fragments in Android with Example](https://www.geeksforgeeks.org/viewpager-using-fragments-in-android-with-example/)
