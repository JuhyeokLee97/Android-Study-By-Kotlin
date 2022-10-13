# Android 12: Safer Component Exporting 대응

## 중요 내용
공식 문서에서는 Android 12 이상부터 **Safer Component Exporting**에 대해서 아래와 같이 설명한다.

> If your app targets Android 12 or higher and contains `activities`, `services`, or `broadcast receivers` that use `intent filters`, you must explicitly declare the `android:exported` attribute for these app components.

안드로이드 12 이상을 타겟할 경우, `activity`, `service` 또는 `broadcast receiver` 중에서 `intent filter` 를 포함하는 경우 `android:exported`를 명시해야한다는 것이다.
</br>
</br>

> If the app component includes the `LAUNCHER` category, set `android:exported` to `true`. In most other cases, set `android:exported` to `false`.

만약 아래와 같이 사용하는 컴포넌트 중에서 **`LAUNCHER` 카테고리를 포함하는** 컴포넌트 같은 경우는 무조건 `android:exported="true"`로 설정해야한다.</br>
일반적으로는 `android:exported="false"`로 설정하면 된다.

``` xml
<intent-filter>
	<action android:name="android.intent.action.MAIN" />
	<category android:name="android.intent.category.LAUNCHER" />
</intent-filter>
```
</br>
</br>

## `android:exported` 명시를 위한 default export value

### In Activity

> This element sets whether the activity can be launched by components of other applications:
> 
> - If "`true`", the activity is accessible to any app, and is launchable by its exact class name.
> - If "`false`", the activity can be launched only by components of the same application, applications with the same user ID, or privileged system components. **This is the default value when there are no intent filters.**

| has intent-filter | default exported value |
| --- | --- |
| O | true |
| X | false |

### In Service

> Whether or not components of other applications can invoke the service or interact with it — `true` if they can, and `false` if not. When the value is `false`, only components of the same application or applications with the same user ID can start the service or bind to it.

**The default value depends on whether the service contains intent filters.** **The absence of any filters means that it can be invoked only by specifying its exact class name.** This implies that the service is intended only for application-internal use (since others would not know the class name). So in this case, the default value is `false`. On the other hand, the presence of at least one filter implies that the service is intended for external use, so the default value is `true`.
> 

| has intent-filter | default exported value |
| --- | --- |
| O | true |
| X | false |

### In Provider

> - `true`: The provider is available to other applications. Any application can use the provider's content URI to access it, subject to the permissions specified for the provider.

- `false`: The provider is not available to other applications. Set `android:exported="false"` to limit access to the provider to your applications. Only applications that have the same user ID (UID) as the provider, or applications that have been temporarily granted access to the provider through the `[android:grantUriPermissions](https://developer.android.com/guide/topics/manifest/provider-element#gprmsn)` element, have access to it.
> 

| API Level | default exported value |
| --- | --- |
| 17 or higher | false |
| 16 or lower | true |

### In Boradcast Service
