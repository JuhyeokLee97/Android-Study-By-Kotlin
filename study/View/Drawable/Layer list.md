# Layer list
> A drawable that manages an array of other drawables.
> These are drawn in array order, so the element with the largest index is drawn on top.

drawable의 배열을 관리하는 drawable 이다.
`Layer list`는 배열의 순서대로 그려진다.
그래서 인덱스 값이 가장 큰 아이템이 제일 위에 그려진다.

> A `LayerDrawable` is a drawable object that manages an array of the other drawables.
> Each drawable in the list is drawn in the order of the list.
> The last drawable in the list is drawn on top
>
> Each drawable is represented by an `<item>` element inside a single `<layer-list>` element.

### 파일 위치

`res/drawable/filename.xml`
`filename`은 리소스의 ID값이 된다.

### 리소스 데이터 타입
컴파일된 리소스는 `LayerDrawable` 타입으로 나타난다.

### 리소스 참조
리소스는 다음처럼 참조할 수 있다.
- Java(Kotlin): `R.drawable.filename`
- XML: `@[package:]drawable/filename`

### 문법

``` xml
<?xml version="1.0" encoding="uft-8"?>
<layer-list
    xmlns:android="http://schemas.android.com/apk/res/android" >
    <item
        android:drawable="@[package:]drawable/drawable_resource"
        android:id="@[+][package:]id/resource_name"
        android:top="dimension"
        android:right="dimension"
        android:bottom="dimension"
        android:left="dimension" />
</layer-list>

```



### Reference
[Android developers > Layer list](https://developer.android.com/guide/topics/resources/drawable-resource#LayerList)
