# System Permission 요청

## System Permission 이란

## System Permission 요청하기

Intent
``` kotlin
(context as Activity).startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${Application.packageName()}")).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }, REQUEST_OVERLAY_PERMISSION)
```

### System Permisssion 요청 후, 설정 화면 자동 닫기

