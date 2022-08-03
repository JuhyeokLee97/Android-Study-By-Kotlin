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
- **FragmentStatePagerAdapter**: desc...
- **FragmentPagerAdapter**: desc..


#### 참고
##### [ ViewPager Using Fragments in Android with Example](https://www.geeksforgeeks.org/viewpager-using-fragments-in-android-with-example/)
