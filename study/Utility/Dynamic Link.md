## Code

### FirebaseDynamicLinkUtils.kt
``` kotlin
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase

object FirebaseDynamicLinkUtils {

    fun shareDynamicLinks(link: String, domainUriPrefix: String, androidMinimumVersion: Int, callback: (String) -> Unit) {
        createDynamicLinks(link, domainUriPrefix, androidMinimumVersion)
            .addOnCompleteListener {
                it.result?.shortLink?.let { link ->
                    val resultLink = link.toString()
                    callback.invoke(resultLink)
                    Log.i(MainActivity.TAG, "shareDynamicLinks: $resultLink")
                }
            }
    }

    private fun createDynamicLinks(
        link: String,
        domainUriPrefix: String,
        androidMinimumVersion: Int
    ): Task<ShortDynamicLink> {
        return Firebase.dynamicLinks.shortLinkAsync {
            this.link = Uri.parse(link)
            this.domainUriPrefix = domainUriPrefix
            androidParameters { this.minimumVersion = androidMinimumVersion }
            buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
        }
    }
}
```
### MainActivity.kt
``` kotlin
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.devgeek.sampledynamiclink.databinding.ActivityMainBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initDynamicLinks()
        binding.btnCreateDynamicLink.setOnClickListener {
            FirebaseDynamicLinkUtils.shareDynamicLinks(
                link = DEEP_LINK_URL,
                domainUriPrefix = DOMAIN_PREFIX,
                androidMinimumVersion = 1,
                callback = { dynamicLink ->
                    binding.etDynamicLinkReceiver.setText(dynamicLink)
                    Toast.makeText(this, "$dynamicLink 를 복사해주세요.", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    /**
     * Dynamic Link 를 통해서 들어온 경우 처리를 할 수 있다.
     */
    private fun initDynamicLinks() {
        /** Receive Deep Link */
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener { data ->
                data?.let {
                    var deepLink: Uri? = null
                    deepLink = data.link
                    Log.i(TAG, "deepLink is [$deepLink] ")
                    Log.i(TAG, "path is [${deepLink?.path}]")

                    startActivity(Intent(this@MainActivity, DeepLinkActivity::class.java).apply {
                        putExtra("DEEP_LINK", deepLink.toString())
                    })

                }
            }
            .addOnFailureListener {
                Log.e(TAG, "get DynamicLink Failed")
            }
    }
    
    companion object {
        const val TAG = "DEV_GEEK"
        const val DEEP_LINK_URL = "https://devgeek.tistory.com"
        const val DOMAIN_PREFIX = "https://devgeek.page.link"
    }
}
```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/etDynamicLinkReceiver"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="20dp"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnCreateDynamicLink"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:text="create dynamic button"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

