# [ViewPager2를 사용하여 탭으로 스와이프 뷰 만들기](https://developer.android.com/guide/navigation/navigation-swipe-view-2?hl=ko)

## 개요
<p>
  스와이프 뷰를 사용하면 손가락의 가로 동작이나 스와이프로 탭과 같은 동위 화면 간을 탐색할 수 있다.
  이런한 탐색 패턴을 **가로 페이징**이라고도 한다.
  여기서는 탭 간 전환을 위해 스와이프 뷰로 **탭 레이아웃**을 만드는 방법과 탭 대신 **제목 스트립**을 표시하는 방법을 학습한다.
</p>

## 스와이프 뷰 구현
<p>

AndroidX의 `ViewPager2` 위젯을 이용하면 **스와이프 뷰**를 만들 수 있다. 

</p>

<p>

`ViewPager2`를 Layout에서 사용하기 위해서는 `<ViewPager2>` 요소를 Layout에 추가해야한다.
예를 들어 **스와이프 뷰**에서 각각의 페이지가 전체 레이아웃을 사용한다면 layout 파일은 아래와 같이 `<ViewPager2>`를 구현하면 된다.

</p>

``` xml
<androidx.viewpager2.widget.ViewPager2 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/pager"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

<p>

각 페이지를 나타내는 하위 뷰를 삽입하려면 이 레이아웃을 `FragmentStateAdapter`에 연결해야한다. 이를 통해 `Fragment` 집합을 스와이프 하는 방법은 아래와 같다.

</p>
