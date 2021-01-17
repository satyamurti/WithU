package com.incoders.withu.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incoders.withu.R
import kotlinx.android.synthetic.main.activity_keywordss.*


class ActivityNames : AppCompatActivity() {
    var cricketersList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keywordss)

        val layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_cricketers.setLayoutManager(layoutManager)

        cricketersList = (intent.extras!!.getSerializable("list") as ArrayList<String>?)!!

        recycler_cricketers.setAdapter(NamesAdapter(cricketersList))
    }
}