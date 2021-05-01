package io.github.takusan23.erogamescapedroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.github.takusan23.erogamescapedroid.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 検索Fragmentを置く
        setFragment(SearchFragment())
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main_fragment_host_frame_layout, fragment)
            .addToBackStack(System.currentTimeMillis().toString())
            .commit()
    }

}
