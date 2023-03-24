

### build.gradle (:app)
``` 
dependencies {
    ...
    // firebase remote config
    implementation 'com.google.firebase:firebase-config-ktx:21.2.1'
    ...
}
```

### FirebaseRemoteConfigUtils.kt
``` kotlin
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object FirebaseRemoteConfigUtils {
    
    private val FETCH_INTERVAL_IN_SECONDS_FOR_RELEASE = 3600L
    private val FETCH_INTERVAL_IN_SECONDS_FOR_DEBUG = 0L
    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) FETCH_INTERVAL_IN_SECONDS_FOR_DEBUG else FETCH_INTERVAL_IN_SECONDS_FOR_RELEASE  // 1시간 5회 이상 가능

    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

}
```

### Reference
- [Firebase Release Note](https://firebase.google.com/support/release-notes/android)
- [Latest Version](https://firebase.google.com/support/release-notes/android#latest_sdk_versions)
