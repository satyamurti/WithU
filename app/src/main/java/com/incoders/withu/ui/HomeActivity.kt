package com.incoders.withu.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incoders.withu.R
import kotlinx.android.synthetic.main.fragement_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragement_home)
        sosCard.setOnClickListener {
            intent = Intent(this, SosActivity::class.java)
            startActivity(intent)
        }
        emergencyCard.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        liveLocation.setOnClickListener {
            intent = Intent(this, LiveLocationActivity::class.java)
            startActivity(intent)
        }
        addkeyWords.setOnClickListener {
            intent = Intent(this, AddKeyWordsActivity::class.java)
            startActivity(intent)
        }
    }
}