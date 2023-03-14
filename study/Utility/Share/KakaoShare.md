## KakaoShare.kt

``` kotlin
import android.content.Context
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link

/**
 * @ref: https://developers.kakao.com/docs/latest/ko/message/message-template#type
 */
class KakaoShare(context: Context) {

    /** 카카오 설치 여부 **/

    /** 피드 템플릿 **/
    fun shareByFeedOneButton(
        title: String,
        desc: String,
        imageUrl: String,
        webLink: String? = null,
        mobileLink: String? = null,
        btnTitle: String
    ) {
        val content = Content(
            title = title,
            description = desc,
            imageUrl = imageUrl,
            link = Link(webUrl = webLink, mobileWebUrl = mobileLink)
        )

        val feedTemplate = FeedTemplate(
            content = content,
            buttonTitle = btnTitle
        )
        
        ShareClient.instance.shareDefault(context, feedTemplate) { sharingResult, error -> 
            if (error != null) {
                Log.e("KAKAO_SHARE", "shareByFeedOneButton: 공유하기 에러 발생", )
            } else if ( sharingResult != null) {
                context.startActivity(sharingResult.intent)
            }
        }
    }
    /** 리스트 템플릿 **/

    /** 위치 템플릿 **/

    /** 커머스 템플릿 **/

    /** 텍스트 템플릿 **/

    /** 캘린더 템플릿 **/
}
```
