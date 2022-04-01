# RecyclerView & RecyclerView Adapter Example

축구 선수의 이름, 포지션 그리고 번호를 입력 받아서 “저장하기” 버튼을 눌렀을 때, 선수의 정보를 RecyclerView를 통해 나타내는 예제입니다.

참고). **ListAdapter**를 통해 RecyclerView 구현을 참고하실 분은 ["안드로이드 RecyclerView.ListAdapter 예제 in Kotlin"](https://devgeek.tistory.com/22) 에서 확인하실 수 있습니다.

# 실행화면

![demo](https://user-images.githubusercontent.com/40654227/132125298-6fbaad9a-3109-41a5-b4f1-3fb8babcfe7a.gif)

완전한 전체 코드는 [github.com/JuhyeokLee97](https://github.com/JuhyeokLee97/Android_RecyclerView) 에서 확인하실 수 있습니다.

## MainActivity Layout: activity\_main.xml 작성

축구 선수의 이름, 포지션 그리고 번호를 입력 받아서 “저장하기” 버튼을 눌렀을 때, RecyclerView에 정보가 보여지는 레이아웃입니다.

RecycerView 속성 중 “app:layoutManager”은 RecyclerView에서 아이템들을 어떤 형태로 보여줄 지 정보를 담는 중요한 속성입니다. 이번 예제에서는 “LinearLayoutManager”를 사용해 일렬로 나타내 보겠습니다.

```
<?xml version="1.0" encoding="utf-8"?>  
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"  
  xmlns:app="http://schemas.android.com/apk/res-auto"  
  xmlns:tools="http://schemas.android.com/tools"  
  android:layout_width="match_parent"  
  android:layout_height="match_parent"  
  tools:context=".MainActivity">  

 <LinearLayout  android:id="@+id/ll_player_name"  
  android:layout_width="match_parent"  
  android:layout_height="50dp"  
  android:layout_marginHorizontal="20dp"  
  android:orientation="horizontal"  
  app:layout_constraintStart_toStartOf="parent"  
  app:layout_constraintTop_toTopOf="parent">  

 <TextView  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:text="Player\nName"  
  android:textSize="20dp" />  

 <EditText  android:id="@+id/et_player_name"  
  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="3"  
  android:hint="Messi" />  
 </LinearLayout>  
 <LinearLayout  android:id="@+id/ll_player_position"  
  android:layout_width="match_parent"  
  android:layout_height="50dp"  
  android:layout_marginHorizontal="20dp"  
  android:orientation="horizontal"  
  app:layout_constraintStart_toStartOf="@id/ll_player_name"  
  app:layout_constraintTop_toBottomOf="@id/ll_player_name">  

 <TextView  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:text="Position"  
  android:textSize="20dp" />  

 <EditText  android:id="@+id/et_player_position"  
  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="3"  
  android:hint="FW" />  
 </LinearLayout>  
 <LinearLayout  android:id="@+id/ll_player_number"  
  android:layout_width="match_parent"  
  android:layout_height="50dp"  
  android:layout_marginHorizontal="20dp"  
  android:orientation="horizontal"  
  app:layout_constraintStart_toStartOf="@id/ll_player_position"  
  app:layout_constraintTop_toBottomOf="@id/ll_player_position">  

 <TextView  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:text="Player Number"  
  android:textSize="20dp" />  

 <EditText  android:id="@+id/et_player_number"  
  android:layout_width="0dp"  
  android:layout_height="match_parent"  
  android:layout_weight="3"  
  android:hint="10" />  
 </LinearLayout> <Button  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:layout_marginHorizontal="20dp"  
  android:id="@+id/btn_add_player"  
  android:text="저장하기"  
  android:textStyle="bold"  
  android:textSize="20dp"  
  app:layout_constraintTop_toBottomOf="@id/ll_player_number"  
  />  

 <androidx.recyclerview.widget.RecyclerView  android:layout_width="match_parent"  
  android:layout_height="0dp"  
  android:layout_marginHorizontal="20dp"  
  android:layout_marginBottom="60dp"  
  android:id="@+id/rv_player_info"  
  app:layout_constraintBottom_toBottomOf="parent"  
  app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"  
  app:layout_constraintTop_toBottomOf="@id/btn_add_player"/>  

</androidx.constraintlayout.widget.ConstraintLayout>
```

## RecyclerView ItemLayout: item\_view.xml 작성

“res/layout” 디렉토리에 “Layout Resource File”을 생성합니다. 파일 이름은 “item\_view”로 했습니다.  
하나의 줄에 선수의 이름, 포지션 그리고 번호를 보여주기 위해 LinearLayout 안에 3개의 TextView를 사용했습니다.

```
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:orientation="horizontal">  

 <TextView  android:id="@+id/tv_player_name"  
  android:layout_width="0dp"  
  android:layout_height="wrap_content"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:textSize="30dp" />  

 <TextView  android:id="@+id/tv_player_position"  
  android:layout_width="0dp"  
  android:layout_height="wrap_content"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:textSize="30dp" />  

 <TextView  android:id="@+id/tv_player_number"  
  android:layout_width="0dp"  
  android:layout_height="wrap_content"  
  android:layout_weight="1"  
  android:gravity="center"  
  android:textSize="30dp" />  
</LinearLayout>
```

## Init View: MainActivity 작성

“res/layout/activity\_main.xml” 에서 만든 View들을 사용하기 위해 변수들을 초기화 합니다.

```
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var etPlayerName: EditText
    lateinit var etPlayerPosition: EditText
    lateinit var etPlayerNumber: EditText
    lateinit var btnAddPlayer: Button
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init View
        etPlayerName = findViewById(R.id.et_player_name)
        etPlayerPosition = findViewById(R.id.et_player_position)
        etPlayerNumber = findViewById(R.id.et_player_number)
        btnAddPlayer = findViewById(R.id.btn_add_player)
        recyclerView = findViewById(R.id.rv_player_info)

    }

}
```

## SoccerPlayer.class

사용자에게 입력 받을 선수 이름, 포지션, 번호 데이터를 저장할 수 있는 SoccerPlayer.Class를 만듭니다.  
MainActivity.kt와 같은 위치에 생성하면 됩니다.

```
class SoccerPlayer(_name: String, _position: String, _number: String) {  
     var name: String? = null  
     var position: String? = null  
     var number: String? = null  

     init{  
        name = _name  
        position = _position  
        number = _number  
     }  
}
```

## SoccerPlayer 저장

MainActivity.kt에 저장하기 버튼이 눌렸을 때, SoccerPlayer 객체를 playerList에 저장하도록 btnAddPlayer의 setOnClickListener를 정의해줍니다.

```
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var etPlayerName: EditText
    lateinit var etPlayerPosition: EditText
    lateinit var etPlayerNumber: EditText
    lateinit var btnAddPlayer: Button
    lateinit var recyclerView: RecyclerView

    // player List create
    lateinit var playerList: ArrayList<SoccerPlayer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init View
        etPlayerName = findViewById(R.id.et_player_name)
        etPlayerPosition = findViewById(R.id.et_player_position)
        etPlayerNumber = findViewById(R.id.et_player_number)
        btnAddPlayer = findViewById(R.id.btn_add_player)
        recyclerView = findViewById(R.id.rv_player_info)

        // Init PlayerList Item
        playerList = ArrayList<SoccerPlayer>()
        playerList.add(SoccerPlayer("Messi", "Forward", "10"))

        // Button 클릭 시, 선수 정보 저장.
        btnAddPlayer.setOnClickListener {
            var name = etPlayerName.text.toString()
            var position = etPlayerPosition.text.toString()
            var number = etPlayerNumber.text.toString()

            playerList.add(SoccerPlayer(name, position, number))
        }

    }

}
```

## Adapter 생성

RecyclerView에 데이터를 연결해주기 위해서 Adapter를 생성(MainActivity.kt와 같은 위치)합니다. 연결해줄 데이터인 playerList를 인자로 받아서 ReclcyerView.Adpater 구현에 필요한 ViewHolder를 구현하고 override 함수인 onCreateViewHolder, onBindViewHolder, getItemCount를 구현합니다.

추가적으로 버튼이 눌렸을 때 입력 받을 선수데이터를 playerList에 삽입하고 RecyclerView에 데이터를 업데이트 해줄 addItem을 구현합니다.

```
import android.view.LayoutInflater  
import android.view.View  
import android.view.ViewGroup  
import android.widget.TextView  
import androidx.recyclerview.widget.RecyclerView  

class MyRecyclerViewAdapter(var playerList: ArrayList<SoccerPlayer>): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {  

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){  
        var tvPlayerName: TextView = itemView.findViewById(R.id.tv_player_name)  
        var tvPlayerPosition: TextView = itemView.findViewById(R.id.tv_player_position)  
        var tvPlayerNumber: TextView = itemView.findViewById(R.id.tv_player_number)  

    }  

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {  
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)  
        return MyViewHolder(itemView)  
    }  

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {  
        var player: SoccerPlayer = playerList.get(position)  

        holder.tvPlayerName.text = player.name  
        holder.tvPlayerPosition.text = player.position  
        holder.tvPlayerNumber.text = player.number  
  }  

    override fun getItemCount(): Int {  
        return playerList.size  
  }  

    fun addItem(player: SoccerPlayer){  
        playerList.add(player)  
        notifyDataSetChanged()  
    }  
}
```

## RecyclerView에 Adapter 연결하기

축구 선수 데이터를 갖는 playerList를 인자로 MyRecyclerViewAdapter()를 생성하여 recyclerView.adapter에 연결합니다.  
그리고 저장하기 버튼의 setOnClickListener에서 playerList에 직접 데이터를 삽입하지 않고 adapter.addItem()을 이용해서 데이터를 삽입하고 recyclerView에 보여지도록 합니다.

```
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var etPlayerName: EditText
    lateinit var etPlayerPosition: EditText
    lateinit var etPlayerNumber: EditText
    lateinit var btnAddPlayer: Button
    lateinit var recyclerView: RecyclerView

    // player List create
    lateinit var playerList: ArrayList<SoccerPlayer>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init View
        etPlayerName = findViewById(R.id.et_player_name)
        etPlayerPosition = findViewById(R.id.et_player_position)
        etPlayerNumber = findViewById(R.id.et_player_number)
        btnAddPlayer = findViewById(R.id.btn_add_player)
        recyclerView = findViewById(R.id.rv_player_info)

        // Init PlayerList Item
        playerList = ArrayList<SoccerPlayer>()
        playerList.add(SoccerPlayer("Messi", "Forward", "10"))

        // Set RecyclerView Adapter
        var adapter = MyRecyclerViewAdapter(playerList)
        recyclerView.adapter = adapter

        // Button 클릭 시, 선수 정보 저장.
        btnAddPlayer.setOnClickListener {
            var name = etPlayerName.text.toString()
            var position = etPlayerPosition.text.toString()
            var number = etPlayerNumber.text.toString()

            adapter.addItem(SoccerPlayer(name, position, number))
        }

    }

}
```

## RecyclerView Adaper 이해하기

추가적으로 ViewHolder와 각 함수들이 왜 필요한지를 제 생각을 담아 정리했습니다. 정답은 아니겠지만 이해하는데 참고하시면 도움이 될 것 같습니다.

![Adapter Structure](https://user-images.githubusercontent.com/40654227/132125500-7ab3af5f-8b35-4491-888a-42125ceaec9d.png)
