## Code

### MainActivity.kt
``` kotlin
package com.devgeek.sampledynamiclink

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.devgeek.sampledynamiclink.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGetDeepLink.setOnClickListener {
            initDynamicLinks()
        }
        binding.btnCreateDynamicLink.setOnClickListener {
//            Toast.makeText(this, createDynamicLink(), Toast.LENGTH_SHORT).show()
            shareDynamicLinks(this)
        }
    }

    fun initDynamicLinks() {
        /** Receive Deep Link */
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener { data ->
                data?.let {
                    var dynamicLink: Uri? = null
                    dynamicLink = data.link
                    Log.i(TAG, "dynamicLink is [$dynamicLink] ")
                    Log.i(TAG, "path is [${dynamicLink?.path}]")
                    binding.tvDynamicLinkReceiver.text = dynamicLink?.toString() ?: "NULL"
                }
            }
            .addOnFailureListener {
                Log.i(TAG, "get DynamicLink Failed")
                binding.tvDynamicLinkReceiver.text = "FAILED"
            }
    }

    fun createDynamicLink(): String {
        return FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse(DEEP_LINK_URL))
            .setDomainUriPrefix(DOMAIN_PREFIX)
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(this.packageName)
                    .build()
            )
            .buildDynamicLink()
            .uri.toString()
    }

    private fun shareDynamicLinks(context: Context) {
        createDevGeekLink()
            .addOnCompleteListener {
                it.result?.shortLink?.let { link ->
                    val resultLink = link.toString()
                    Toast.makeText(context, "$resultLink", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "shareDynamicLinks: $resultLink")
                }
            }
    }

    private fun createDevGeekLink(): Task<ShortDynamicLink> {
        val url = DEEP_LINK_URL
        val domainUriPrefix = DOMAIN_PREFIX
        val aosMinimumVersion = 1

        return createDynamicLinks(url, domainUriPrefix, aosMinimumVersion)

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

    companion object {
        const val TAG = "ZOOH"
        const val DEEP_LINK_URL = "https://devgeek.tistory.com"
        const val DOMAIN_PREFIX = "https://devgeek.page.link"
    }
}
```
