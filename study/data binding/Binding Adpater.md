# BindingAdapter


## 바인딩 어댑터란

<p>

- **바인딩 어댑터** 레이아웃 요소에 코드를 바인딩 하는 역할을 한다. 
- **바인딩 어댑터**는 UI의 속성 중 값을 할당하기 위해 `setter`를 호출하거나, 따로 작성한 로직을 실행하거나 또는 사용자의 인터렉션에 반응하도록 한다.

</p>

### 자동으로 값 할당하기
<p>
데이터 바인딩 라이브러리는 몇 `view`들을 위해 미리 준비된 **바인딩 어댑터**가 있다. 
기본적인 예로는 아래와 같이 `TextView`에 텍스트 값을 설정하는 어댑터가 있다.

</p>

``` xml
<layout>
	<data>
		<variable
			name="viewModel"
			type="./.../ViewModel"/>
	</data>
	...
	<TextView
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		...
		android:text="@{viewModel.contents}"
		... />

</layout>
```

<p>

`TextView`에 텍스트 값을 할당하기 위해서 바인딩 표현식(Binding Expression) `@{viewModel.contents}`를 사용했다.
참고로 `viewModel.contents`의 값의 타입은 `String`이다. 그러기 때문에 데이터 바인딩 라이브러리는 `TextView`에서 `setText(text: String)`에 해당하는 함수를 찾게 된다. 이 함수는 이미 존재하기 때문에 특별히 **바인딩 어댑터**를 생성할 필요 없이 동작한다.

</p>


_Note_: You can add any code you want in a binding expression. However, it’s better practice to add complex logic in `ViewModel` and call its method instead.
