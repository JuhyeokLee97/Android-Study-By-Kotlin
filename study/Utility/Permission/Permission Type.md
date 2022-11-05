# [권한 종류(Types of permissions)](https://developer.android.com/guide/topics/permissions/overview)

### 개요
안드로이드에서는 권한에 대해 크게 3가지(install-time, runtime, special) 타입으로 나누고 있다.
각 권한의 타입은 (해당 권한을 허용했을 경우) 앱에서 접근하는 데이터가 어떤 것인지 그리고 앱이 수행할 수 있는 동작의 범위는 어디까지인지를 나타낸다. 
각 권한에 대한 접근 제한 단계는 권한 타입을 따르고 있다.
접근 제한 단계에 대해서는 [Permission API references](https://developer.android.com/reference/android/Manifest.permission) 에서 자세히 확인할 수 있다.

## Install-time permissions
**insgall-time 권한**을 간단하게 말하면 앱 스토에서 앱 상세 페이지에 가면 사용자가 볼 수 있도록 공지되어 있고, **앱을 설치하는 경우, 시스템에서 자동적으로 권한을 받아오는** 권한 타입이다.
**install-time** 권한의 특징으로는 제한된 데이터에 대한 접근을 막거나 시스템 또는 다른 앱에 영향을 줄 수 있는 동작을 할 수 없도록 제한한다는 것이다.

<!--
추가적으로는 `install-time` 권한 타입안에서도 타입이 2가지(Normal, Signature)로 나뉜다.

### Normal Permissions
**normal** 권한은 앱의 샌드박스를 넘어 확장되지만 사용자의 개인정보와 다른 앱의 동작에 거의 위험을 주지 않는 데이터 및 작업에 대한 접근을 허용한다.

### Signature permissions

-->

## Runtime Permissions

<p>
<strong>runtime 권한</strong>은 간단하게 말하면 앱을 사용하는 중, 특정한 데이터에 접근하거나 작을을 수행하는 경우에 권한을 요청하는 것들의 집합이라고 할 수 있다.
위험한 권한
위험한 권한이라고도 불리는 **runtime** 권한은 제한된 데이터에 대한 접근 허용과 다른 앱과 시스템에 상당히 영향을 줄 수 있는 동작 허용에 대한 권한을 포함한다.
개발 중 중요한 것은 이전에 해당 권한 들을 허용했다고 가정하고 데이터에 접근하거나 동작을 수행하면 안된다.
항상 권한을 체크해야하고 없다면 사용자에게 권한 요청을 해야한다.
</p>

<p>

만약 우리가 만든 앱에서 <strong>runtime</strong> 권한을 요청하는 경우, 시스템에서는 다음과 같이 시스템 권한 팝업을 보여준다.

</p>


<p>

<strong>runtime</strong> 권한은 <strong>install-time</strong> 권한과 다르게 사용자의 개인 정보나 잠재적으로 민감한 정보에 대한 접근 권한을 포함하고 있다.
예를 들면 개인정보로 위치 정보나, 연락처 정보 접근하거나 마이크나 카메라와 같이 직간접적으로 사용자의 개인 정보에 접근할 수 있는 권한을 요청한다.
때문에 시스템은 <strong>runtime</strong> 권한을 요청할 경우, 사용자에게 해당 데이터에 접근하는 이유를 명시하고 있다.

</p>

위험한 권한이라고도 알려져 있는 **runtime 권한**은 제한된 데이터에 대한 접근 허용과 다른 앱과 시스템에 상당히 영향을 줄 수 있는 제한된 동작 허용을 포함한다.
그래서 **runtime** 권한은

## Runtime Permissions
Runtime permissions, also known as dangerous permissions, give your app additional access to restricted data or let your app perform restricted actions that more substantially affect the system and other apps. Therefore, you need to request runtime permissions in your app before you can access the restricted data or perform restricted actions. Don't assume that these permissions have been previously granted—check them and, if needed, request them before each access.

When your app requests a runtime permission, the system presents a runtime permission prompt, as shown in figure 3.

Many runtime permissions access private user data, a special type of restricted data that includes potentially sensitive information. Examples of private user data include location and contact information.

The microphone and camera provide access to particularly sensitive information. Therefore, the system helps you explain why your app accesses this information.

The system assigns the dangerous protection level to runtime permissions.

## Special Permissions
Special permissions correspond to particular app operations. Only the platform and OEMs can define special permissions. Additionally, the platform and OEMs usually define special permissions when they want to protect access to particularly powerful actions, such as drawing over other apps.

The Special app access page in system settings contains a set of user-toggleable operations. Many of these operations are implemented as special permissions.

Each special permission has its own implementation details. The instructions for using each special permission appear on the permissions API reference page. The system assigns the appop protection level to special permissions.

## Permission Groups

Permissions can belong to permission groups. Permission groups consist of a set of logically related permissions. For example, permissions to send and receive SMS messages might belong to the same group, as they both relate to the application's interaction with SMS.

Permission groups help the system minimize the number of system dialogs that are presented to the user when an app requests closely related permissions. When a user is presented with a prompt to grant permissions for an application, permissions belonging to the same group are presented in the same interface. However, permissions can change groups without notice, so don't assume that a particular permission is grouped with any other permission.

