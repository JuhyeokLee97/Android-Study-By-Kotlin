# RecyclerView Multi-ViewHolder Basic Sample

## Multi-ViewHolder 개념

## 개요

### 앱 설명
RecyclerView에서 **Multi-ViewHolder**를 이용하여 메신저 어플리케이션에서 보여지는 UI를 만든다.
### 실행화면
<img src="https://user-images.githubusercontent.com/40654227/187210490-b3b7ff36-38b3-479a-9966-1e5a720bbd82.png" height=600/>

## Code
### 프로젝트 구조
<img src="https://user-images.githubusercontent.com/40654227/187449361-f79d9524-1f64-4100-965d-2beaee8cf852.png"/>

### build.gradle(:Module): ViewBinding 셋팅
``` kotlin
android {
    ...
    buildFeatures{
        viewBinding true
    }
}
```

### MessageModel.kt

``` kotlin
interface MessageModel {
    data class SenderMessage(
        val message: String
    ): MessageModel

    data class ReceiverMessage(
        val message: String
    ): MessageModel
}
```

RecyclerView의 Multi-ViewHolder에서 사용할 아이템 아이템을 일반화 하기 위해서 Interface로 `MessageModel`를 만든다.
그리고 사용할 아이템 타입을 정의하는 클래스를 정의하는데 `MessageModel`을 상속받는다. 

### item_sender_chat.xml

`SenderViewHolder`에서 사용할 Layout을 구현한다.

``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_margin="10dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <androidx.cardview.widget.CardView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginEnd="100dp"
        android:backgroundTint="#FFC107"
        app:cardCornerRadius="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/tvMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="20dp"
            android:layout_marginVertical="10dp"
            android:padding="10dp"
            android:textColor="@color/white"
            android:textSize="16sp"
            android:textStyle="bold"
            tools:text="나의 메시지 나의 메시지" />
    </androidx.cardview.widget.CardView>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### SenderViewHolder.kt
``` kotlin
class SenderViewHolder(private val binding: ItemSenderChatBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindItem(senderMessage: MessageModel.SenderMessage) {
        binding.tvMessage.text = senderMessage.message
    }

    companion object {
        fun create(parent: ViewGroup): SenderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemSenderChatBinding.inflate(layoutInflater, parent, false)
            return SenderViewHolder(view)
        }
    }

}
```

### item_receiver_chat.xml
`ReceiverViewHolder`에서 사용할 Layout을 구현한다.
``` xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="10dp">

    <androidx.cardview.widget.CardView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="100dp"
        android:backgroundTint="#4CAF50"
        app:cardCornerRadius="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/tvMessage"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginHorizontal="20dp"
            android:layout_marginVertical="10dp"
            android:padding="10dp"
            android:textColor="@color/white"
            android:textSize="16sp"
            android:textStyle="bold"
            tools:text="상대방 메시지 상대방 메시지" />
    </androidx.cardview.widget.CardView>

</androidx.constraintlayout.widget.ConstraintLayout>
```

### ReceiverViewHolder.kt
``` kotlin
class ReceiverViewHolder(private val binding: ItemReceiverChatBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindItem(receiverMessage: MessageModel.ReceiverMessage){
        binding.tvMessage.text = receiverMessage.message
    }

    companion object {
        fun create(parent: ViewGroup): ReceiverViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemReceiverChatBinding.inflate(layoutInflater, parent, false)
            return ReceiverViewHolder(view)
        }
    }
}
```

### ChatAdapter.kt
``` kotlin
class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val itemList = arrayListOf<MessageModel>()

    fun addItem(item: MessageModel){
        itemList.add(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_SENDER -> SenderViewHolder.create(parent)
            else -> ReceiverViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SenderViewHolder -> holder.bindItem(itemList[position] as SenderMessage)
            is ReceiverViewHolder -> holder.bindItem(itemList[position] as ReceiverMessage)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is SenderMessage -> TYPE_SENDER
            is SenderMessage -> TYPE_RECEIVER
            else -> -1
        }

    }
    companion object{
        const val TYPE_SENDER = 0
        const val TYPE_RECEIVER = 1
    }

}
```
- `itemList = arrayListOf<MessageModel>()`: ViewHolder에 사용될 아이템을 일반화(`MessageModel`)한 리스트.(`MessageModel`을 상속받아 구현한 `SenderMessage` 또는 `ReceiverMessage` 모두 아이템이 될 수 있다.)
- `getItemViewType(position: Int)`: `itemList[position]`의 타입이 **SenderMessage**인 경우, `TYPE_SENDER`를 반환하고 **ReceiverMessage**인 경우, `TYPE_RECEIVER`를 반환한다. 반환된 **viewYpe**을 통해서 `onCreateViewHolder(parent: ViewGroup, viewType: Int)`에서 ViewHolder를 생성할 때 사용한다.
- `onCreateViewHolder(parent: ViewGroup, viewType: Int)`: **viewType**에 따른 `SenderViewHolder` 또는 `ReceiverViewHolder`를 생성하여 반환한다.

### activity_main.xml
``` xml
androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvChatting"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:background="#7A03A9F4"
        android:orientation="vertical"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainActivity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val chatAdapter = ChatAdapter()
        chatAdapter.apply {
            addItem(SenderMessage("안녕하세요."))
            addItem(SenderMessage("저는 안드로이드 개발자입니다."))
            addItem(ReceiverMessage("반갑습니다!!"))
            addItem(SenderMessage("당신은 어떤 개발자입니까?"))
            addItem(ReceiverMessage("저는 안드로이드 신입 개발자에요. :)"))
            addItem(SenderMessage("반가워요."))
        }

        binding.rvChatting.adapter = chatAdapter
    }
}
```
