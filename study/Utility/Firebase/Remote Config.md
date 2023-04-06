# Firebase Remote Config

### How does it work?

---

We recommend adding [real-time Remote Config](https://firebase.google.com/docs/remote-config/get-started#add-real-time-listener)
 functionality to your fetch logic **to automatically fetch the latest Remote Config parameter values as soon as they're published.** Note that real-time Remote Config is currently supported for Android and Apple platforms only.

### Get the Romote Config singleton object [[참고 링크](https://firebase.google.com/docs/remote-config/get-started?platform=android#get-remote-config)]

---

Get a Remote Config object instance and **set the minimum fetch interval** to allow for frequent refreshes

```
val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
val configSettings = remoteConfigSettings {
    minimumFetchIntervalInSeconds = 3600
}
remoteConfig.setConfigSettingsAsync(configSettings)
```

During development, it's **recommended to set a relatively low minimum fetch interval.** See [Throttling](https://firebase.google.com/docs/remote-config/get-started?platform=android#throttling)
 for more information.

### [Fetch and activate values](https://firebase.google.com/docs/remote-config/get-started?platform=android#fetch-values)

---

Because these updated parameter values affect the behavior and appearance of your app, you should activate the fetched values at a time that ensures a smooth experience for your user, **such as the next time that the user opens your app.** See [**Remote Config loading strategies**](https://firebase.google.com/docs/remote-config/loading)
 for more information and examples.

### Listen for updates in real time [[참고 링크](https://firebase.google.com/docs/remote-config/get-started?platform=android#add-real-time-listener)]

---

**Important:** Real-time Remote Config is supported by the following SDK versions: **Android SDK v21.3.0+ (Firebase BoM v31.3.0+).**

Real-time Remote Config also requires the Firebase Remote Config Realtime API, which should already be enabled for you. To verify, open the [Google Cloud Console](https://console.developers.google.com/apis/api/firebaseremoteconfigrealtime.googleapis.com/overview), select your project, and open the **APIs and Services** page. The API should appear as enabled. If it's missing or not enabled, click **Enable APIs & Services**, search for **Firebase Remote Config Realtime API**, and enable it.

- Firebase BoM v.31.3.0 관련 버전
    
    
    | 인공물 | 버전 A |
    | --- | --- |
    | com.google.firebase:firebase-analytics | 21.2.1 |
    | com.google.firebase:firebase-analytics-ktx | 21.2.1 |
    | com.google.firebase:firebase-appcheck | 16.1.2 |
    | com.google.firebase:firebase-appcheck-debug | 16.1.2 |
    | com.google.firebase:firebase-appcheck-debug-testing | 16.1.2 |
    | com.google.firebase:firebase-appcheck-ktx | 16.1.2 |
    | com.google.firebase:firebase-appcheck-playintegrity | 16.1.2 |
    | com.google.firebase:firebase-appcheck-safetynet | 16.1.2 |
    | com.google.firebase:firebase-auth | 21.1.0 |
    | com.google.firebase:firebase-auth-ktx | 21.1.0 |
    | com.google.firebase:firebase-common | 20.3.2 |
    | com.google.firebase:firebase-common-ktx | 20.3.2 |
    | com.google.firebase:firebase-config | 21.3.0 |
    | com.google.firebase:firebase-config-ktx | 21.3.0 |
    | com.google.firebase:firebase-crashlytics | 18.3.6 |
    | com.google.firebase:firebase-crashlytics-ktx | 18.3.6 |
    | com.google.firebase:firebase-crashlytics-ndk | 18.3.6 |
    | com.google.firebase:firebase-database | 20.1.0 |
    | com.google.firebase:firebase-database-ktx | 20.1.0 |
    | com.google.firebase:firebase-dynamic-links | 21.1.0 |
    | com.google.firebase:firebase-dynamic-links-ktx | 21.1.0 |
    | com.google.firebase:firebase-encoders | 17.0.0 |
    | com.google.firebase:firebase-firestore | 24.4.5 |
    | com.google.firebase:firebase-firestore-ktx | 24.4.5 |
    | com.google.firebase:firebase-functions | 20.2.2 |
    | com.google.firebase:firebase-functions-ktx | 20.2.2 |
    | com.google.firebase:firebase-inappmessaging | 20.3.1 |
    | com.google.firebase:firebase-inappmessaging-display | 20.3.1 |
    | com.google.firebase:firebase-inappmessaging-display-ktx | 20.3.1 |
    | com.google.firebase:firebase-inappmessaging-ktx | 20.3.1 |
    | com.google.firebase:firebase-installations | 17.1.3 |
    | com.google.firebase:firebase-installations-ktx | 17.1.3 |
    | com.google.firebase:firebase-messaging | 23.1.2 |
    | com.google.firebase:firebase-messaging-directboot | 23.1.2 |
    | com.google.firebase:firebase-messaging-ktx | 23.1.2 |
    | com.google.firebase:firebase-ml-modeldownloader | 24.1.2 |
    | com.google.firebase:firebase-ml-modeldownloader-ktx | 24.1.2 |
    | com.google.firebase:firebase-perf | 20.3.1 |
    | com.google.firebase:firebase-perf-ktx | 20.3.1 |
    | com.google.firebase:firebase-storage | 20.1.0 |
    | com.google.firebase:firebase-storage-ktx | 20.1.0 |

### Throttling [[참고 링크](https://firebase.google.com/docs/remote-config/get-started?platform=android#throttling)]

---

If an app fetches too many times in a short time period, fetch calls are throttled and the SDK returns `FirebaseRemoteConfigFetchThrottledException`. **Before SDK version 17.0.0, the limit was 5 fetch requests in a 60 minute window (newer versions have more permissive limits).**

### Firebase Remote Config Version 17.0.0 Release Note [[참고 링크](https://firebase.google.com/support/release-notes/android#remote-config_v17-0-0)]

---

- Deprecated
    - Deprecated developer mode. Use `[FirebaseRemoteConfigSettings.Builder.setMinimumFetchIntervalInSeconds(0L)](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfigSettings.Builder#setMinimumFetchIntervalInSeconds(long))` instead.
    - Deprecated the synchronous `[FirebaseRemoteConfig.setConfigSettings(FirebaseRemoteConfigSettings)](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfig#setConfigSettings(FirebaseRemoteConfigSettings))`. Use the asynchronous `[FirebaseRemoteConfig.setConfigSettingsAsync(FirebaseRemoteConfigSettings)](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfig#setConfigSettingsAsync(FirebaseRemoteConfigSettings))` instead.
    - Deprecated `[FirebaseRemoteConfigFetchException](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfigFetchException)`. Use the more granular `[FirebaseRemoteConfigServerException](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfigServerException)` and `[FirebaseRemoteConfigClientException](https://firebase.google.com/docs/reference/android/com/google/firebase/remoteconfig/FirebaseRemoteConfigClientException)` instead.
- Changed
    - Updated all "cache expiration" references to "**minimum fetch interval**" and "cache" references to "**local storage**".
    

### Firebase Open Source - Remote Config [[참조 링크](https://firebaseopensource.com/projects/firebase/quickstart-android/config/readme/)]

---

- **Fetch values from the Remote Config service**
    
    When an app calls `fetch`, **locally stored parameter values are used unless the minimum fetch interval is reached**. The minimal fetch interval is determined by:
    
    1. The parameter passed to `fetch(long minFetchInterval)`.
    2. The minimum fetch interval set in Remote Config settings.
    3. The default minimum fetch interval, 12 hours.
    
    Fetched values are immediately activated when retrieved using `fetchAndActivate`. 
    
    `fetchAndActivate` returns **true** if the final set of key/value pairs now available to the application is **different to the set before calling `fetchAndActivate`**, false is returned otherwise.
