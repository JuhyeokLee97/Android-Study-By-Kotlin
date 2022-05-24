# [LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=eng)
## 개요
 - ``LiveData``는 관찰 가능한 데이터 홀더 클래스이다.
 - 관찰 가능한 일반 클래스와 달리 ``LiveData``는 수명 주기를 인식하는데 즉, Activity, Fragment, Service 등 다른 앱 구성요소의 ``LifeCycle``고려한다.
 - <strong>LifeCycle 을 인식하기 때문에 ``LiveData``는 ``active lifecycle`` 상태에 있는 앱 구성요소 관찰자만 업데이트한다.</strong>

## LiveData의 이점
### UI와 Data 상태값 일치
<p>

LiveData는 Observer Pattern을 따르기 때문에, 데이터가 변경될 때 ``Observer`` 객체에 알린다.</br>
개발자는 구독된 객체가 변경되었을 때, UI를 업데이트하는 방식으로만 고려하면 된다. <strong>그러므로 데이터가 변경될 때 마다 UI를 어떻게 업데이트 시킬 지 고려할 필요가 없다.</strong>

</p>

### 메모리 누수가 없음
Observer는 ``Lifecycle`` 객체에 결합되어 있고 해당 객체의 생명 주기가 끝나면 <strong>자동으로 삭제</strong>가 된다.

### 중지된 Activity로 인한 에러(비정상 종료) 없음
Activity가 back-stack 에 있을 때를 비롯하여 Observer의 Lifecycle이 ``inactive`` 상태일 때 어떠한 LiveData의 이벤트도 받지 않는다.

### Lifecycle을 더 이상 수동으로 처리하지 않음
UI components는 관련 데이터(``LiveData``)를 관찰하기만 할 뿐이지 Observation을 중지하거나 다시 시작하지 않는다.</br>
LiveData는 관련된 ``lifecycle status``의 변경을 관찰하기 때문에 자동으로 관리해준다.

### 언제나 최신의 데이터
만약 lifecycle이 비활성화 되면,  다시 활성화 될 때 최신의 데이터를 받아온다. 예를 들어 백그라운드(background)에 있었던 Activity는 포그라운드(foreground)로 돌아온 직후 최신 데이터를 받는다.

### 자연스러운 configuration 변경
기기 회전과 같은 configuration이 변경으로 인해 Activity 또는 Fragment가 다시 생성되면 사용 가능한 최신의 데이터를 받는다.

### 리소스 공유
앱에서 시스템 서비스를 공유할 수 있도록 ``Singleton pattern``을 사용하는 ``LiveData`` 객체를 확장하여 시스템 서비스를 래핑할 수 있다. ``LiveData``가 시스템 서비스에 한 번 연결되면, 해당 리소스가 필요한 모든 Observer가 ``LiveData``를 관찰할 수 있다.</br>
참고). [LiveData 확장](https://developer.android.com/topic/libraries/architecture/livedata?hl=ko#extend_livedata)

## LiveData 사용법
### 개요
### LiveData Object 만들기
### LiveData Object 관찰하기
### LiveData Objects 업데이트하기
### Coroutines + LiveData
