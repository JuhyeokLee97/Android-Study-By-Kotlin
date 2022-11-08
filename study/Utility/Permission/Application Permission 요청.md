# Application Permission 요청

## Application Permission 이란

## 권한 요청하기
권한 요청은 함수를 호출하면서 필요한 권한들을 파라미터에 담아 보내면 됩니다.

``` kotlin
static void requestPermission(Activity activity, String[] permissions, int requestCode)
```

 - `activity`:
 - `permissions`:
 - `requestCode`:

### 요청 권한이 하나인 경우

``` kotlin
companion {
    const val PERMISSION_REQUEST_CODE = 0
}
...

val permissions = arrayOf( Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION )

ActivityCompt.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
```

[참고 블로그](https://copycoding.tistory.com/357)
