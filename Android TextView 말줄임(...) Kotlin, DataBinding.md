개요
게시판에 글이 보여질 때, 내용이 많은 글에 대해서는 글줄임(...)을 통해 내용의 일부만 보여주고, 게시글 아이템의 높이를 일정하게 유지한다.
유기적으로 사용자가 내용 전체를 보고 일부만 볼 수 있도록, 글줄임(...) 설정 이벤트를 구현한다.

실행화면


프로젝트 구조(이미지)
DataBinding을 사용함.



Code
[DataBinding 셋팅] build.gradle(:app)
DataBinding 사용을 위해 gradle에 dataBinding { endabled true }를 추가한다.

android {
    compileSdk 31
    ...
    dataBinding{
        enabled true
    }
}
[게시글 데이터] PostModel.kt
data class PostModel(
    var id: Long,
    var userName: String,
    var postScript: String
)
[게시글 아이템 Layout] item_post.xml
태그: View들을 binding해 사용하기 위해 DataBinding에서는 필수적이다.
, 태그: DataBinding을 통해 게시글 아이템(PostModel) 의 값을 자동으로 연결시킨다.

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="post"
            type="com.example.textviewstudy.PostModel" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:id="@+id/tvUserName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="10dp"
            android:layout_marginTop="5dp"
            android:text="@{post.userName}"
            android:textColor="@color/black"
            android:textSize="16sp"
            android:textStyle="bold"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:text="UserName" />

        <TextView
            android:id="@+id/tvScript"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="10dp"
            android:layout_marginEnd="30dp"
            android:ellipsize="end"
            android:text="@{post.postScript}"
            android:textColor="@color/black"
            android:textSize="14sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tvUserName"
            tools:text="A user interface element that displays text to the user. To provide user-editable text, see EditText. The following code sample shows a typical use, with an XML layout and code to modify the contents of the text view:" />


        <TextView
            android:id="@+id/tvOption"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="자세히보기"
            app:layout_constraintEnd_toEndOf="@id/tvScript"
            app:layout_constraintTop_toBottomOf="@id/tvScript" />

        <View
            android:id="@+id/divider"
            android:layout_width="0dp"
            android:layout_height="2dp"
            android:layout_marginTop="15dp"
            android:background="#CCCCCC"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/tvOption" />
    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
[게시글 RecyclerView Adapter] PostAdapter.kt
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.textviewstudy.databinding.ItemPostBinding

class PostAdapter : ListAdapter<PostModel, PostAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(var binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostModel) {
            binding.apply {
                // dataBinding을 통해 item(PostModel)과 xml에 있는 View들의 데이터들을 연결해준다.
                post = item

                // tvScript의 내용의 line을 기준으로 maxLine을 초과한 내용은 "말줄임(...)"을 통해 일부만 보여준다.
                tvScript.text =
                    item.postScript // tvScript.text 값이 dataBinding을 통해서 동기화 되기 전에 아래 로직이 동작되므로 항상 lineCount 값이 0이 되기 때문에 강제로 text값을 설정한다.
                tvScript.run {
                    // TextView
                    doOnLayout {
                        val lineCount = lineCount
                        val maxLine = 2
                        if (lineCount > maxLine) {
                            maxLines = maxLine
                            ellipsize = TextUtils.TruncateAt.END
                            tvOption.visibility = View.VISIBLE
                        } else {
                            tvOption.visibility = View.GONE
                        }
                    }
                }
                // "자세히보기|간략히보기" TextView 클릭 이벤트
                tvOption.setOnClickListener {
                    when (tvScript.maxLines) {
                        2 -> {
                            tvScript.maxLines =
                                100 // 스크립트 TextView의 maxLine 값을 상당히 큰 값을 주어 모든 텍스트가 보이도록 한다.
                            tvOption.text =
                                "간단히보기" // 스크립트 TextView의 글이 모두 보이기 때문에 줄임을 나타낼 수 있도록 tvOption의 텍스트 값을 바꿔준다.
                        }
                        else -> {
                            tvScript.maxLines = 2
                            tvOption.text = "자세히보기"
                        }
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
[RecyclerView 셋팅] activity_main.xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/actionBar"
            android:layout_width="0dp"
            android:layout_height="?attr/actionBarSize"
            android:gravity="start|center"
            android:padding="10dp"
            android:text="게시판"
            android:textColor="@color/black"
            android:textSize="25sp"
            android:textStyle="bold"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <View
            android:id="@+id/divider"
            android:layout_width="0dp"
            android:layout_height="2dp"
            android:background="#CCCCCC"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/actionBar" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@id/divider"
            tools:listitem="@layout/item_post" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
MainActivity.kt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.textviewstudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        // init PostAdapter
        postAdapter = PostAdapter()
        // setPostData
        postAdapter.submitList(getPostList())
        // connect PostAdapter to RecyclerView's adapter
        binding.recyclerView.adapter = postAdapter
    }

    private fun getPostList(): ArrayList<PostModel> {
        val postList = arrayListOf<PostModel>()
        val postModel1 = PostModel(
            id = System.currentTimeMillis(),
            userName = "user_1",
            postScript = "A user interface element that displays text to the user. To provide user-editable text, see EditText. The following code sample shows a typical use, with an XML layout and code to modify the contents of the text view:"
        )
        postList.add(postModel1)

        val postModel2 = PostModel(
            id = System.currentTimeMillis(),
            userName = "user_2",
            postScript = "This code sample demonstrates how to modify the contents of the text view defined in the previous XML layout:"
        )
        postList.add(postModel2)

        val postModel3 = PostModel(
            id = System.currentTimeMillis(),
            userName = "user_3",
            postScript = "Short Script"
        )
        postList.add(postModel3)

        return postList
    }
}
마무리하며
회사에서 프로젝트를 진행하던 중, 정보를 소개하는 글을 담는 TextView를 구현하는데 있어 앱(웹)에서 흔히 볼 수 있는 "글..." 형태의 말줄임을 구현해야하는 작업을 맡은 적이 있었다.
구글링 결과, TextView의 속성 중 maxLine과 ellipsize를 통해서 말줄임(...) 형태를 구현할 수 있다는 것을 알았다.
하지만 더 나아가 (...)형태가 된 TextView를 펼치고 줄이고 할 수 있는 기능이 필요해 구글링 해봤지만, 자세히 설명되어 있지 않아 글을 정리하게 됐다.

특정 상품 또는 업체 설명하는 상세 페이지에서, 기본 정보를 TextView에서 보여줄 때 사용하면 용이할 것 같다.
리뷰를 보여주는 리스트 아이템에서 내용을 담는 TextView에 사용하면 용이할 것 같다.
