package com.incoders.withu.ui


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.incoders.withu.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val myPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val sosNum =  myPref.getString("sosNumber","")
        editTextSos.hint = sosNum.toString()

        saveSettings.setOnClickListener {
            saveSet()
        }


    }

    private fun saveSet() {
        if (editTextSos.text.isEmpty()){
            editTextSos.error = "Please enter a name"
            return
        }
        val myPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = myPref.edit()
        editor.putString("sosNumber",editTextSos.text.toString())
        editor.apply()
        Toast.makeText(this,"Data Saved",Toast.LENGTH_SHORT).show()
    }

}