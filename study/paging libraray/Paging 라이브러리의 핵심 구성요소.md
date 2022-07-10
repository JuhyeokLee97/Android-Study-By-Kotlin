# Paging 라이브러리의 핵심 구성요소

#### Paging 라이브러리의 핵심 구성요소는 다음과 같다.
- `PaingSource`: 특정 페이지 쿼리의 데이터 청크를 로드하는 기본 클래스이다. 데이터 레이어의 일부이며 일반적으로 `DataSource` 클래스에서 노출되고 이후에 `ViewModel`에서 사용하기 위해 `Repository`에 의해 노출된다.

- `PagingConfig`: 페이징 독작을 결정하는 매개변수를 정의하는 클래스이다. 여기에는 페이지 크리, 자리표시자의 사용 설정 여부 등이 포함된다.

- `Pager`: `PagingData` 스트림을 생성하는 클래스이다. `PagingSource`에 따라 다르게 실행되며 `ViewModel`에서 만들어야 한다.

- `PagingData`: 페이지로 나눈 데이터의 컨테이너이다. 데이터를 새로고침할 때마다 자체 `PagingSource`로 지원되는 상응하는 `PagingData` 내보내기가 별도로 생성된다.

- `PagingDataAdapter`: `RecyclerView`에 `PagingData`를 표시하는 `RecyclerView.Adapter` 서브 클래스이다. `PagingDataAdapter`는 팩토리 메서드를 사용하여 Kotlin `Flow`나 `LiveData`, RxJava `Flowable`, RxJava `Observable` 또는 정적 목록에도 연결할 수 있다. `PagingDataAdapter`는 내부 `PagingData`로드 이벤트를 수신 대기하고 페이지가 로드될 때 UI를 효율적으로 업데이트 한다.
