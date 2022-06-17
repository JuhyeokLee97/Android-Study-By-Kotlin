
## Android ProgressBar in Kotlin

<p>
<strong>ProgressBar</strong>는 사용자에게 작업 진행률을 나타내는 사용자 인터페이스 컨트롤이다. 예를 들면 인터넷에서 파일을 다운로드하거나 업로드 할 때, 우리는 <strong>ProgressBar</strong>을 통해서 작업 진행 상황을 파악할 수 있다.


</p>

<p>

<Strong>ProgressBar</strong>에는 2가지 모드가 있다.
- Determinate ProgressBar
- Indeterminate ProgressBar

### Determinate ProgressBar

<p align="center">
 <img src="https://user-images.githubusercontent.com/40654227/174306867-48ad270b-fc73-40eb-8ffa-462248049abe.png" width=400/>
</p>

<p>

일반적으로, 우리는 <strong>Determinate progress</strong> 모드를 사용한다. 왜냐하면 이 progressbar는 작업의 진행정도를 퍼센티지(%)와 같이 나타내기 때문이다.</br>

</p>

### Indeterminate ProgressBar

<p align="center">
 <img src="https://user-images.githubusercontent.com/40654227/174316120-a35538f9-e431-4647-a4c1-90c27f838571.png" width=250/>
</p>

<p>

<strong>Indeterminate ProgressBar</strong>는 아쉽게도 작업의 진행률을 확인할 수는 없다. 그래서 대게 진행정도를 파악할 수 없을 때 사용한다. 예를들면 API 통신을 통해 데이터를 읽어 올 때, 즉 서버에서 데이터를 받는 정도를 파악할 수 없는 경우 사용한다.

</p>

### Attribute of ProgressBar Widgets
|XML Attributes|Description|
|--|--|
|android:id| Used to uniquely identify the control|
|android:min|Used to set minimum value |
|android:max| Used to set the maximum value|
|android:progress| Used to set the default progress integer value between 0 and max|
|android:minHeight| Used to set the height of the progress bar|
|android:minWidth| Used to set the width of the progress bar|
|android:background| Used to set the background color for progress bar|
|android:indeterminate| Used to enable indeterminate progress mode|
|android:padding|Used to set the padding for left, right, top, or bottom of the progress bar. |
