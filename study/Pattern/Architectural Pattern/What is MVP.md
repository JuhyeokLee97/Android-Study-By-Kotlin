# MVP 패턴이란?
**Model**, **View**, **Presenter**로 구성된 디자인 패턴이다.

**MVP**의 등장배경은 MVC 디자인 패턴에서는 UI(View)와 비지니스 로직이 의존적이여서 수정이나 기능추가 등이 어려운 점을 해결할 수 있는,
 즉 UI(**View**)와 비지니스 로직(**MODEL**)을 분리하고 그 사이를 관리하는 **Presenter**로 구성된 **MVP** 디자인 패턴이 나왔다.
 

## MVP 구성요소
### Model
Layer for storing data. 
It is responsible for handling the domain logic(real-world business rules) and communication with the database and network layers.

### View
UI(User Interface) layer. 
It provides the visualization of the data and keep a track of the user’s action in order to notify the Presenter.

### Presenter
Fetch the data from the model and applies the UI logic to decide what to display. 
It manages the state of the View and takes actions according to the user’s input notification from the View.

#### 참고 블로그
[MVP (Model View Presenter) Architecture Pattern in Android with Example](https://www.geeksforgeeks.org/mvp-model-view-presenter-architecture-pattern-in-android-with-example/)
