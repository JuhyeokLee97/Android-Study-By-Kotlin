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

### 실행 영상

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
