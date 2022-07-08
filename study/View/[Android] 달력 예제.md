# [Android] 달력 예제 in Kotlin

## 개요

<p>
  
달력 예제 구현에서 캘린더 뷰는 [material-calendarview](https://github.com/prolificinteractive/material-calendarview)를 사용한다.  
  
</p>

### 구현 내용

- 캘린더 상단에 보여지는 `월.년` 디폴트값을 `년.월`로 커스터마이징한다.
- 캘린더 요일을 한국어로 커스터마이징한다.
- 달력의 날짜 텍스트를 커스터마이징한다.
- 휴무일 개념으로 특정 날짜의 상태를 `disable` 처리한다.
- 현재 일을 기준으로 과거의 날짜의 상태를 `disable` 처리한다.

### 실행 화면

<p>
  <img src="https://user-images.githubusercontent.com/40654227/177912198-15bf228b-aa7a-4da5-9282-6b7b0aae2750.png" height=300 align+"left" />
  <img src="https://user-images.githubusercontent.com/40654227/177912218-e0e160ea-1868-4461-a976-78a08b5a488e.png" height=300 align+"center" />
</p>  
  


## 예제 구현
### Gradle Scripts\settings.gradle: `Jitpack Repository`추가
``` kotlin
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### build.gradle(Module): 의존성 추가
``` kotlin
dependencies {
    ...
    // Calendar
    implementation 'com.github.prolificinteractive:material-calendarview:2.0.1'
}
```

### build.gradle(Module): ViewBinding 추가
``` kotlin
android {
    ...
    buildFeatures{
        viewBinding true
    }
}
```

### themes.xml: 달력 텍스트 스타일 수정하기 위한 소스 추가
`themes.xml` 파일에 달력 텍스트 스타일을 수정하기 위해 사용할 `<style name="TextStyleSemiBold15"/>`를 추가한다
``` xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.SampleCalenderView" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        ...
        <!-- Customize your theme here. -->
    </style>

    <style name="TextStyleSemiBold15">
        ...
    </style>
</resources>
```

### activity_main.xml: CalendarView 추가
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.prolificinteractive.materialcalendarview.MaterialCalendarView
        android:id="@+id/calendarView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:mcv_selectionColor="#00F"
        app:mcv_weekDayTextAppearance="@style/TextStyleSemiBold15"
        app:mcv_showOtherDates="all" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

 - `app:mcv_selectionColor="#00F"`: 캘린더에서 날짜를 선택 했을 때, 선택된 날짜에 처리되는 색이다.
 - `app:mcv_weekDayTextAppearance="@style/TextStyleSemiBold15"`: 캘린더의 날짜 텍스트 스타일을 설정한다.
 - `app:mcv_showOtherDates="all"`: 달력 뷰에 보여지는 날짜의 스타일을 지정한다. `all`을 선택하면 해당 달의 `이전`, `이후`의 날짜도 보여준다.

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val today = CalendarDay.today()

        val disabledDates = hashSetOf<CalendarDay>()
        disabledDates.add(CalendarDay.from(2022, 7, 12))

        binding.calendarView.apply {
            // 휴무일 지정을 위한 Decorator 설정
            addDecorator(DayDisableDecorator(disabledDates, today))
            // 요일을 지정하귀 위해 {"월", "화", ..., "일"} 배열을 추가한다.
            setWeekDayLabels(arrayOf("월", "화", "수", "목", "금", "토", "일"))
            // 달력 상단에 `월 년` 포맷을 수정하기 위해 TitleFormatter 설정
            setTitleFormatter(MyTitleFormatter())
        }

        DateFormatTitleFormatter()
    }

    inner class MyTitleFormatter : TitleFormatter {
        override fun format(day: CalendarDay?): CharSequence {
            val simpleDateFormat =
                SimpleDateFormat("yyyy.MM", Locale.US) //"February 2016" format

            return simpleDateFormat.format(Calendar.getInstance().getTime())
        }

    }

    inner class DayDisableDecorator : DayViewDecorator {
        private var dates = HashSet<CalendarDay>()
        private var today: CalendarDay

        constructor(dates: HashSet<CalendarDay>, today: CalendarDay) {
            this.dates = dates
            this.today = today
        }

        override fun shouldDecorate(day: CalendarDay): Boolean {
            // 휴무일 || 이전 날짜
            return dates.contains(day) || day.isBefore(today)
        }

        override fun decorate(view: DayViewFacade?) {
            view?.let { it.setDaysDisabled(true) }
        }
    }
}
```

#### 휴무일 지정(Disable 처리) - CalendarView에 Decorator 설정을 위한 DayViewDecorator 인터페이스 구현
``` kotlin
    inner class DayDisableDecorator : DayViewDecorator {
        private var dates = HashSet<CalendarDay>()
        private var today: CalendarDay

        constructor(dates: HashSet<CalendarDay>, today: CalendarDay) {
            this.dates = dates
            this.today = today
        }

        override fun shouldDecorate(day: CalendarDay): Boolean {
            // 휴무일 || 이전 날짜
            return dates.contains(day) || day.isBefore(today)
        }

        override fun decorate(view: DayViewFacade?) {
            view?.let { it.setDaysDisabled(true) }
        }
    }
```

 - `dates`: 휴무일을 저장하는 Set
 - `today`: 현재 날짜를 저장하는 변수
 - `shouldDecorate(day)`: 해당 날짜가 `dates` Set에 포함되어있거나 `today` 보다 과거이면 Decorate가 되기위해 `true`값을 반환한다.
 - `decorate(view)`: `shouldDecorate`함수를 통해서 `true`값을 갖는 날짜 view에 `disable`처리를 한다.



