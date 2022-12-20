# WebView 페이스북 로그인(2) 로직

### 6. 페이스북 로그인 콜백 추가(FacebookLoginProvider.kt)
``` kotlin
class FacebookLoginProvider {
    
    /**
     * 페이스북 로그인 콜백 매니저 생성
     * 
     * @param context 페이스북 로그인을 요청하는 Context 값
     * @param listener [OnFacebookLoginCallback]를 구현하여 로그인 성공(실패)에 따른 동작을 정의
     */
    fun fbLogin(context: Context?, listener: OnFacebookLoginCallback): CallbackManager {
        val facebookCallbackManager = CallbackManager.Factory.create()

        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(context as Activity, arrayListOf("public_profile", "email"))
        loginManager.registerCallback(facebookCallbackManager, object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                val token = result.accessToken.token
                listener.onSuccess(token)
            }

            override fun onCancel() {
                listener.onFailed()
            }

            override fun onError(error: FacebookException) {
                listener.onFailed()
            }
        })
        return facebookCallbackManager
    }

    interface OnFacebookLoginCallback {
        fun onSuccess(token: String?)
        fun onFailed()
    }
}
```

### 7. 페이스북 로그인 구현(MainActivity.kt)
``` kotlin
class MainActivity : AppCompatActivity() {

    private var facebookCallbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ...
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }
   
    private fun receivedLogin(id: String, pw:String){
        facebookCallbackManager = FacebookLoginProvider().fbLogin(this, object : FacebookLoginProvider.OnFacebookLoginCallback{
            override fun onSuccess(token: String?) {
                // todo: Facebook Login 성공 후, 로직
            }

            override fun onFailed() {
                // todo: Facebook Login 실패 후, 로직
            }
        })
    }

}
```
