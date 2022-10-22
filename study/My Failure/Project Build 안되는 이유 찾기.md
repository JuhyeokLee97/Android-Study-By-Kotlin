# Project Build 안되는 이유 찾기

Android Studio에서 `build.gradle` 파일을 수정하고 싱크룰 맞추는 중 실패하는 경우가 있다.
</br>
대게는 실패할 경우, 실패에 대한 이유를 `Build` 콘솔에서 명시해주지만 가끔은 `Unknown Reaseon`이라고 하면서 실패하는 경우가 있다.
</br>
또는 싱크는 성공했지만, **Build**가 되지 않는 경우가 있다.
</br>
이 때 `Event Log` 콘솔에서 보면 실패했다고 알려주지만 자세한 실패 이유를 설명해주지 않는 경우도 있다.

이런 경우에는 이유를 알 수 없어서 구글링으로도 해결하기가 어렵다.

**실패 이유**를 찾는 방법이 있다!!
</br>
바로 **로그 파일**(`idea.log`)을 직접 확인하는 방법이다.
</br>
내가 사용하는 맥북 기준으로 로그 파일 경로는 다음과 같다.
</br>
`User` > `{user_name}` > `Library` > `Logs` > `Google` > `AndroidStudio{version}` > <strong>`idea.log`</strong>
