# Navigation Basic Sample

## 개요

### 어플리케이션
<p align="center"/>
<img src="https://user-images.githubusercontent.com/40654227/178662795-c8def067-878e-45ab-a41e-0c2f3a30f837.png" height=500/>
</p>

## 코드

### build.gradle(Module)
``` kotlin
android {
    ...
    buildFeatures{
        viewBinding true
    }
}

dependencies {
    ...
    /** Jetpack Navigation Library */
    implementation 'androidx.navigation:navigation-fragment-ktx:2.5.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.5.0'
    ...
}

```
### nav_graph.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/mainFragment">

    <fragment
        android:id="@+id/mainFragment"
        android:name="com.example.navigationbasicsample.MainFragment"
        android:label="MainFragment"
        tools:layout="@layout/fragment_main">

        <action
            android:id="@+id/action_mainFragment_to_redFragment"
            app:destination="@id/redFragment"
            app:enterAnim="@android:anim/fade_in" />
        <action
            android:id="@+id/action_mainFragment_to_greenFragment"
            app:destination="@id/greenFragment"
            app:enterAnim="@android:anim/fade_in" />
    </fragment>

    <fragment
        android:id="@+id/redFragment"
        android:name="com.example.navigationbasicsample.RedFragment"
        android:label="RedFragment"
        tools:layout="@layout/fragment_red">
        <action
            android:id="@+id/action_redFragment_to_greenFragment"
            app:destination="@id/greenFragment"
            app:enterAnim="@android:anim/fade_in" />
    </fragment>

    <fragment
        android:id="@+id/greenFragment"
        android:name="com.example.navigationbasicsample.GreenFragment"
        android:label="GreenFragment"
        tools:layout="@layout/fragment_green">
        <action
            android:id="@+id/action_greenFragment_to_redFragment"
            app:destination="@id/redFragment"
            app:enterAnim="@android:anim/fade_in" />
    </fragment>

</navigation>
```

### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/navHostFragment"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />
</LinearLayout>
```

### MainActity.kt
``` kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
```

### fragment_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#03A9F4"
    tools:context=".MainFragment">

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="Main Fragment Screen"
        android:textColor="@color/white"
        android:textStyle="bold"
        android:textSize="36sp"
        app:layout_constraintBottom_toTopOf="@id/btnGoToRedFragment"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnGoToRedFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:background="#F44336"
        android:text="Go To RedFragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnGoToGreenFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:layout_marginTop="20dp"
        android:background="#65E66A"
        android:text="Go To GreenFragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btnGoToRedFragment" />


</androidx.constraintlayout.widget.ConstraintLayout>
```

### MainFragment.kt
``` kotlin
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoToRedFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_redFragment)
        }
        binding.btnGoToGreenFragment.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_greenFragment)
        }
    }

}
```

### fragment_red.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#F44336"
    tools:context=".RedFragment">

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="Red Fragment Screen"
        android:textColor="@color/white"
        android:textSize="36sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@id/btnBackToFragment"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnBackToFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:background="#2B3235"
        android:text="Back To Previous Fragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnGoToGreenFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:layout_marginTop="20dp"
        android:background="#65E66A"
        android:text="Go To GreenFragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btnBackToFragment" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### RedFragment.kt
``` kotlin

class RedFragment : Fragment() {
    private lateinit var binding: FragmentRedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToFragment.setOnClickListener { findNavController().navigateUp() }
        binding.btnGoToGreenFragment.setOnClickListener { findNavController().navigate(R.id.action_redFragment_to_greenFragment) }
    }

}
```

### fragment_green.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="#65E66A"
    tools:context=".GreenFragment">

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:text="Green Fragment Screen"
        android:textStyle="bold"
        android:textColor="@color/white"
        android:textSize="36sp"
        app:layout_constraintBottom_toTopOf="@id/btnBackToFragment"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnBackToFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:background="#2B3235"
        android:text="Back To Previous Fragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.appcompat.widget.AppCompatButton
        android:id="@+id/btnGoToRedFragment"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginHorizontal="30dp"
        android:layout_marginTop="20dp"
        android:background="#F44336"
        android:text="Go To RedFragment"
        android:textColor="@color/white"
        android:textSize="20sp"
        android:textStyle="bold"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btnBackToFragment" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### GreenFragment.kt
``` kotlin
class GreenFragment : Fragment() {
    private lateinit var binding: FragmentGreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /** btn back to previous Fragment */
        binding.btnBackToFragment.setOnClickListener { findNavController().navigateUp() }
        /** btn go to RedFragment */
        binding.btnGoToRedFragment.setOnClickListener {
            findNavController().navigate(R.id.action_greenFragment_to_redFragment)
        }
    }

}
```
