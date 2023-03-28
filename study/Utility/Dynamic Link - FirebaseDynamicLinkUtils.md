## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/etShortDynamicLink"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="20dp"
        android:layout_marginTop="100dp"
        android:hint="Short Dynamic Link"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/etDynamicLink"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="20dp"
        android:layout_marginTop="20dp"
        android:hint="Dynamic Link"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/etShortDynamicLink"/>

    <Button
        android:id="@+id/btnShareShortDynamicLink"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="Create Short Dynamic Link"
        app:layout_constraintBottom_toTopOf="@id/btnCreateDynamicLink"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

    <Button
        android:id="@+id/btnCreateDynamicLink"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="Create Dynamic Link"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## MainActivity.kt
``` kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devgeek.dynamiclinksample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val LINK = "https://devgeek.tisotry.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initListener()
    }

    override fun onClick(v: View) {
        with(binding) {
            when(v.id){
                btnCreateDynamicLink.id -> {
                    val dynamicLink = FirebaseDynamicLinkUtils.getNewDynamicLink(LINK)
                    etDynamicLink.setText(dynamicLink)
                }
    
                btnShareShortDynamicLink.id -> {
                    FirebaseDynamicLinkUtils.shareDynamicLinks(link = LINK) { shortDynamicLink ->
                        etShortDynamicLink.setText(shortDynamicLink)
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.run {
            btnCreateDynamicLink.setOnClickListener(this@MainActivity)
            btnShareShortDynamicLink.setOnClickListener(this@MainActivity)
        }
    }
}
```

## FirebaseDynamicLinkUtils.kt
``` kotlin
object FirebaseDynamicLinkUtils {
    private const val TAG = "FIREBASE_DYNAMIC_LINK_UTILS"
    private const val DOMAIN_URI_PREFIX = "https://devgeekexample.page.link/"
    private const val ANDROID_MINIMUM_VERSION = 28

    fun shareDynamicLinks(link: String, processShortLink: (String) -> Unit) {
        getDynamicShortLinkTask(link)
            .addOnCompleteListener {
                it.result?.shortLink?.let { link ->
                    val resultLink = link.toString()
                    processShortLink.invoke(resultLink)
                    Log.i(TAG, "shareDynamicLinks: $resultLink")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Exception >> $exception")
            }
    }

    private fun getDynamicShortLinkTask(
        link: String,
    ): Task<ShortDynamicLink> {
        return Firebase.dynamicLinks.shortLinkAsync {
            this.link = Uri.parse(link)
            this.domainUriPrefix = DOMAIN_URI_PREFIX
            androidParameters { this.minimumVersion = ANDROID_MINIMUM_VERSION }
            buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
        }
    }

    fun getNewDynamicLink(link: String): String {
        val dynamicLink = Firebase.dynamicLinks.dynamicLink {
            this.link = Uri.parse(link)
            this.domainUriPrefix = DOMAIN_URI_PREFIX
            androidParameters { this.minimumVersion = ANDROID_MINIMUM_VERSION }
        }

        return dynamicLink.uri.toString()
    }
}
```
