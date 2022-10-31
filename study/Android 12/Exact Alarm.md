# 정확한 알람 권한 

## [정확한 알람(Exact Alarm)](https://developer.android.com/training/scheduling/alarms)

## 개요
## Set an inexact alarm
만약 어떤 앱에서 정확하지 않은(*inexact*) 알람을 설정 한 경우, 핸드폰 시스템에서는 알람이 정확이 미래 어떤 시점에서 전달될 것인지 확정할 수 없다.
대신 시스템에서는 디바이스의 배터리에 가장 효율적으로 시점을 선택하여 알람을 보낸다.

### Deliver an alarm after a specific time

만약 앱에서 `set()`, `setInexactRepeating()`, or `setAndAllowWhileIdle()`을 호출한다면 그 알림은 트리거가 동작하기 전까지는 절대 울리지 않는다.

Android 12 (API level 31)이거나 그 이상 부터는 시스템에서 알림을 제공된 트리거 타임의 한 시간 이내에 동작하도록 한다.
단, 배터리 보호 상태(Doze)와 같은 경우를 제외한다.

## Set an exact alarm
시스템은 <strong>정확한 알림(exact alarm)</strong>을 정확한 시점에 동작하도록 한다.</br>
만약 해당 앱이 Android 12 (API level 31)이거나 그 이상인 경우에는 무조건 **정확한 알림 권한**(*Alarms & reminders*) 권한을 선언해야 한다.</br>
그렇지 않으면 `SecurityException`이 발생한다.</br>
