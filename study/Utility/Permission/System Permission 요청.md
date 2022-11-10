# System Permission 요청

## System Permission 이란
### 종류
1. ScheduleExactAlarm
2. Background Location
## System Permission 요청하기

### 기본(개요)
`Settings`를 이용하여 다음과 같이 Intent로 넘긴다.

``` kotlin
(context as Activity).startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${Application.packageName()}")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }, REQUEST_OVERLAY_PERMISSION)
```

### 정확한 알람 권한 요청: ScheduleExactAlarm
#### 1. AndroidManifest, 권한 등록하기
다음과 같이 ``    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/> `` 를 00에 등록한다

``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.devgeek.permissionexample">

    <uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM"/>
    ...
    <application
       ... >
      ...
    </application>
</manifest>
```
## System Permisssion 요청 후, 설정 화면 자동 닫기
``` kotlin
(context as LifecycleOwner).lifecycleScope.launch {
                repeat(40) {
                    delay(500)
                    // 해당 권한 허용되었는지 검사
                    if (PermissionUtils.canScheduleExactAlarms(context)) {
                      // 설정에서 권한 허용한 경우... ex). startActivity
                        cancel()
                        permissionListener.isGranted()
                    }
                }
            }

```
### 2.MainActivity
``` kotlin
class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initClickListener()
    }

    private fun initClickListener() {
        with(binding) {
            btnExactAlarmPermission.setOnClickListener {
                if (!canScheduleExactAlarm(this@MainActivity)) {
                    Intent(
                        Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                        Uri.parse("package:${packageName}")
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    }.let(::startActivity)
                }
            }
        }
    }

    private fun canScheduleExactAlarm(context: Context): Boolean{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.canScheduleExactAlarms() ?: false
        } else {
            true
        }
    }
}
```
