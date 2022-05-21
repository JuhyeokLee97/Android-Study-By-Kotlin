# Jetpack.ViewModel

## ViewModel 사용 이유
<p>
  
  <strong>``UI Controller의 데이터를 캡슐화하여 앱 구성이 변경되어도 데이터를 유지하기 위함이다.`` </strong>
  
  ### ``UI Controller 부하``
  **원인**: UI Controller에 DB나 Network에서 데이터 로드를 요구하는 경우.</br>
  **결과**: UI를 관리하는 클래스가 너무 키지게 된다. UI Controller에 과도한 책임을 할당하면 다른 클래스로 작업이 위임되지 않고, 단일 클래스가 혼자서 앱의 작업을 모두 처리하려고 한다. 또한 이런 방법으로 UI Contorller에 과도한 책임을 할당하면 테스트가 훨씬 더 어려워진다.
  
  <strong> UI Controller 로직에서 View 데이터 소유권을 분리하는 방법이 쉽고 효율적이다.</strong></br>
  <strong>데이터가 복잡해지면 데이터 로드만을 담당하는 클래스를 두는 것이 좋다.</strong>
  ### ``UI Controller``
  - Activity, Fragment
  - 기본적으로 UI 데이터 표시
  - 사용자 작업에 반응
  - 권한 요청과 같은 운영체제 커뮤니케이션
    
</p>

## ViewModel 데이터 로드
<p align="center">
  
  <img src="https://user-images.githubusercontent.com/40654227/169655950-89594d2f-73bd-43a0-9f57-4b144db4e86e.png"/>

</p>
