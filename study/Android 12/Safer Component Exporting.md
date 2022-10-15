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
공식 문서에서는 Activity에서의 exporting을 아래와 같이 설명한다.

> This element sets whether the activity can be launched by components of other applications:
> 
> - If "`true`", the activity is accessible to any app, and is launchable by its exact class name.
> - If "`false`", the activity can be launched only by components of the same application, applications with the same user ID, or privileged system components. **This is the default value when there are no intent filters.**

Activity에서 `exporting`의 의미는 해당 Activity가 다른 앱의 컴포넌트에서 실행될 수 있는지를 나타낸다.</br>
- `android:exported="true"`: 해당 Class 이름으로 접근한다면 어느 앱이나 접근할 수 있도록 한다.
- `android:exported="false`: 같은 어플리케이션에서만 접근 가능하다.


| has intent-filter | default exported value |
| --- | --- |
| O | true |
| X | false |

### In Service
공식 문서에서는 Service에서의 exporting을 아래와 같이 설명한다.

> Whether or not components of other applications can invoke the service or interact with it — `true` if they can, and `false` if not. When the value is `false`, only components of the same application or applications with the same user ID can start the service or bind to it.
> 
> **The default value depends on whether the service contains intent filters.** **The absence of any filters means that it can be invoked only by specifying its exact class name.** This implies that the service is intended only for application-internal use (since others would not know the class name). So in this case, the default value is `false`. On the other hand, the presence of at least one filter implies that the service is intended for external use, so the default value is `true`.

Service에서 `exporting`의 의미는 해당 Service가 다른 앱의 컴포넌트에서 실행되거나 상호작용할 수 있는지를 나타낸다.</br>
- `android:exported="true"`: 어느 앱의 컴포넌트에서 접근할 수 있도록 한다.
- `android:exported="false`: 같은 어플리케이션에서만 접근 가능하다.

| has intent-filter | default exported value |
| --- | --- |
| O | true |
| X | false |

### In Boradcast Receiver

공식 문서에서는 Boradcast Receiver에서의 exporting을 아래와 같이 설명한다.

> Whether the broadcast receiver can receive message from non-system sources outside its application - "`true`" if it can, and "`false`" if not. if "`false`", the only messages the broadcast reveiver can receive are those sent by the system, components of the same application, or applications with the same user ID.
> 
> If unsepcified, the **default value** depends on whether the the broadcast receiver contains **intent filters**. If the receiver contains at least one intent filter, then the dfault value is "`true`". Otherwise, the default value is "`false`".
> 
> This attribute is not the only way to limit a broadcast reveiver's external exposure. You can also use a permission to limit the external entities that can send it message.

Boradcast Receiver에서 `exporting`의 의미는 해당 Receiver가 다른 앱에서 전송한 메시지를 받을 수 있는냐를 나타낸다.
- `android:exported="true"`: 어느 앱의 컴포넌트에서 보낸 메시지를 받을 수 있다.
- `android:exported="false`: 시스템 또는 같은 어플리케이션에서만 보낸 메시지를 받을 수 있다.

다른 컴포넌트와 같이 **default value**에 대한 기준은 `intent-filter`의 유무이다.
만약 `android:exported`를 명시하지 않았다면 `intnet-filter`를 하나 이상 가지고 있다면 **deault value**는 `true`이고 반대로 없다면 `false`이다.

| has intent-filter | default exported value |
| --- | --- |
| O | true |
| X | false |



### In Provider
공식 문서에서는 Provider에서의 exporting을 아래와 같이 설명한다.

> Whether the content porivider is available for other applications to use:
>
> - `true`: The provider is available to other applications. Any application can use the provider's content URI to access it, subject to the permissions specified for the provider.
> - `false`: The provider is not available to other applications. Set `android:exported="false"` to limit access to the provider to your applications. Only applicatins that have the same user ID (UID) as the provider, or applicaions that have been temporarily granted acccess to the provider through the `android:grantUriPermissions` eleent, have access to it.
>
> Because this attribute was introduced in API level 17, all devices running API level 16 and lower behave as though thisattribute is set "`true`". if you set `android:targetSdkVersion` to 17 or higher, then the default value is "`false`" for dvices running API level 17 and highter

Provider에서 `eporting`의 의미는 해당 Provider가 다른 앱에서 접근이 가능하냐를 나타낸다.
다른 컴포넌트와는 다르게 Provider의 **default value**에 대한 기준은 **API level**이 된다.
Provider 속성이 API level 17에서부터 도입되어서 이전 버전(16 이하)에서는 default value는 **true**이고 API level 17 이상부터는 **false**이다.

| API Level | default exported value |
| --- | --- |
| 17 or higher | false |
| 16 or lower | true |

