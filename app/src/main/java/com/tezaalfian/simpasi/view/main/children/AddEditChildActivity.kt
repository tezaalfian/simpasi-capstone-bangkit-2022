package com.tezaalfian.simpasi.view.main.children

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tezaalfian.simpasi.R

class AddEditChildActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_child)

        this.title = "Children"
    }
}