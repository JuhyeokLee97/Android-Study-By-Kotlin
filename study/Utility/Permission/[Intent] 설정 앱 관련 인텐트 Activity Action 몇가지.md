[Intent] 설정 앱 관련 인텐트 Activity Action 몇가지
Android/Intent 2021. 10. 22. 00:04
권한 관련 앱 세부정보 액티비티로 보내는 것을 공부하다 몇가지 대충 써봤다.

 

android.provider.Settings 클래스

https://developer.android.com/reference/android/provider/Setting

 

Activity Action관련 상수( 액션마다 수행할 수 없는 컴포넌트(액티비티)가 있을 수 있음 )

ACTION_APPLICATION_SETTINGS ( 설정-> 어플리케이션(해당) )
인텐트 설정 필요없음

 

설정-> 애플리케이션설정(해당)


ACTION_APPLICATION_DETAILS_SETTINGS ( 설정-> 어플리케이션 -> 특정 어플리케이션 선택(해당) )
Input- 인텐트 data uri를 "package:com.패키지명"으로 설정

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_real_permission_check)

    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$packageName")
    try {
           startActivity(intent)
    } catch (e: ActivityNotFoundException) {
           log("수행할 수 있는 컴포넌트 없음")
    }

}
 


ACTION_APPLICATION_DEVELOPMENT_SETTINGS (개발자 옵션)
인텐트 설정 필요없음

ACTION_APP_NOTIFICATION_SETTINGS   (설정->어플리케이션-> 특정 앱-> 알림(해당액티비티) )
Added in API level 26

 

특정 앱의 알림(Notification) 정보 액티비티

 

Input: EXTRA_APP_PACKAGE, the package to display.

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_permission_check)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                log("수행할 수 있는 컴포넌트 없음")
            }
        }

    }
 


ACTION_APP_NOTIFICATION_BUBBLE_SETTINGS ( 설정-> 알림 -> 고급 설정-> 플로팅 알림(해당 액티비티) )
Added in api 29


안드로이드 10에서 등장한 버블 알림기능과 관련된 액티비티

(안드로이드 9 디바이스 테스트 - 컴포넌트 없음)

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_permission_check)
        
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_BUBBLE_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            log("수행할 수 있는 컴포넌트 없음")
        }
    }
안드로이드 11 디바이스 테스트


ACTION_APP_SEARCH_SETTINGS 
added in api 29

 

설정-> 검색 돋보기 액션 -> 해당 액티비티

 


ACTION_APP_USAGE_SETTINGS ( 특정 앱 사용한 정보) 
설정-> 애플리케이션-> 해당 앱 -> 앱 타이머-> 해당 액티비티

 

added in api 29

 

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_real_permission_check)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_USAGE_SETTINGS)
            intent.putExtra(Intent.EXTRA_PACKAGE_NAME, packageName)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                log("수행할 수 있는 앱이 없습니다")
            }
        }
    }
