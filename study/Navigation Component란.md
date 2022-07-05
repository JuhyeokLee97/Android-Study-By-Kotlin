# Jetpack Navigation Component in Android

- **Navigation Architecture Component**는 앱의 네비게이션 플로우를 가시화 하면서 네비게이션을 구현하는 것을 간단하게 한다.
- **Navigation Library**를 사용하면 아래와 같은 장점이 있다.

1. 프래그먼트 트랜잭션을 자동으로 핸들링해준다.
2. 기본적으로 앞 뒤 이동 액션을 정확하게 핸들링해준다.
3. 기본적으로 애니메이션과 전환 동작을 제공한다.
4. 딥 링크는 최고 우선순위 작업으로 간주된다.
5. 네비게이션 UI 패턴들(`navigation drawers`, `bottom navigation`)을 간편하게 구현할 수 있다.


## 개요
### Navigation Component 3가지 구성요소
1. `Navigation Graph`(New XML resource)
   - **Navigation Graph** 리소스는 네비게이션 관련 데이터를 한 곳에서 관리한다.
   - **destination**이라고 하는 앱의 모든 위치와 사용자가 앱을 통해 이동할 수 있는 경로를 포함한다.
2. `NavHostFragment`(LayoutXML view)
   - **NavHostFragment**는 유니크한 위젯이다. 이 위젯은 layout에 포함할 수 있다.
   - 이것은 **Navigation Graph**에서 다양한 **destination**들을 보여준다.
3. `NavController`(Kotlin/Java object)
   - **NavController**는 **Navigation Graph**에서 사용자의 위치를 추적할 수 있는 개체이다.
   - **Navigation Graph**를 통해 이동하면 **NavHostFragment**에서 **destination** 콘텐츠의 스왑이 조정된다.
