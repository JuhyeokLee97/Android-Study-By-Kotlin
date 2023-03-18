## KakaoShare.kt

``` kotlin
package com.devgeek.kakaoshare

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

/**
 * @ref: https://developers.kakao.com/docs/latest/ko/message/message-template#type
 */
class KakaoShare(private val context: Context) {

    /** 피드 템플릿 **/
    fun shareByFeedOneButton(
        title: String,
        desc: String,
        imageUrl: String,
        webLink: String? = null,
        mobileLink: String? = null,
        btnTitle: String
    ) {
        if (!isKakaoInstalled()) {
            moveToPlayStore()
            return
        }
        val content = Content(
            title = title,
            description = desc,
            imageUrl = imageUrl,
            link = Link(webUrl = webLink, mobileWebUrl = mobileLink)
        )

        val link = Link(webLink, mobileLink)
        val buttons = listOf(Button(btnTitle, link))
        val feedTemplate = FeedTemplate(
            content = content,
            buttons = buttons
        )

        ShareClient.instance.shareDefault(context, feedTemplate) { sharingResult, error ->
            if (error != null) {
                Log.e("KAKAO_SHARE", "shareByFeedOneButton: 공유하기 에러 발생", )
            } else if ( sharingResult != null) {
                context.startActivity(sharingResult.intent)
                Log.w(TAG, "warning message: ${sharingResult.warningMsg}")
                Log.w(TAG, "argument message: ${sharingResult.argumentMsg}")
            }
        }
    }

    /** 리스트 템플릿 **/

    /** 위치 템플릿 **/

    /** 커머스 템플릿 **/

    /** 텍스트 템플릿 **/

    /** 캘린더 템플릿 **/

    /** 카카오 설치 여부 **/
    private fun isKakaoInstalled(): Boolean {
        val intent = (context as Activity).packageManager.getLaunchIntentForPackage("com.kakao.talk")
        return intent != null
    }

    /** 카카오 플레이 스토어 이동 **/
    private fun moveToPlayStore() {
        val uri = Uri.parse("market://details?id=com.kakao.talk")
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    companion object {
        const val TAG = "KAKAO_SHARE"
    }
}
```
