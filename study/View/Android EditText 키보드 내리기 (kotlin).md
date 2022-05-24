## 개요 (실행화면, 간단한 앱 설명)

### 이슈 화면

RecyclerView를 공부한 중 **EditText**에 텍스트를 입력하고 저장 버튼을 눌렀을 때, 키보드가 닫히지 않는 불편함이 있었다.

![](https://user-images.githubusercontent.com/40654227/148273171-c5f8085f-8dfc-4443-8a52-08a580f2180b.gif)

### 해결 화면

**해당 포커스를 갖는 EditText**를 제외한 영역을 누르면 키보드가 내려가도록 했다.

![](https://user-images.githubusercontent.com/40654227/148273279-acd8d520-724d-41e4-ac64-2b81c0b6f695.gif)

## Code - Example

### MainActivity.kt

해당 **Activity**에서 **dispatchTouchEvent()** 함수를 오버라이드 해준다.

```
override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null && ev != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev.x.toInt()
            val y = ev.y.toInt()

            if (!rect.contains(x, y)) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(focusView.windowToken, 0)
                focusView.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
```
