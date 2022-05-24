### Issue: Android 키보드 생성 시, Bottom Navigation Appear

아래 코드를 사진과 같이 Botoom Navigation을 만들었지만 키보드가 생기면 Bottom Navigation이 같이 올라오는 상황이 발생한다.

![그림1](https://user-images.githubusercontent.com/89065117/139229204-93d23501-5fa8-4011-8b6f-91ee85252b71.png)

### Solution: Bottom Navigation Hide

**manifests** > **AndroidManifest.xml** 파일에서 해당 **<activity>** 태그의 속성 값으로 아래 코드를 추가하면 된다.

```
android:windowSoftInputMode="adjustPan"
```
