package com.incoders.withu.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.incoders.withu.R
import kotlinx.android.synthetic.main.activity_add_key_words.*
import java.util.*


class AddKeyWordsActivity : AppCompatActivity(), View.OnClickListener {
    var layoutList: LinearLayout? = null

    var teamList: MutableList<String?> =
        ArrayList()
    var cricketersList: ArrayList<String> = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_key_words)
        layoutList = findViewById(R.id.layout_list)
        button_add.setOnClickListener(this)
        button_submit_list.setOnClickListener(this)
        teamList.add("Team")
        teamList.add("India")
        teamList.add("Australia")
        teamList.add("England")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_submit_list -> if (checkIfValidAndRead()) {
                val intent = Intent(this, ActivityNames::class.java)
                val bundle = Bundle()
                bundle.putSerializable("list", cricketersList)


                val sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(this)
                val editor: SharedPreferences.Editor = sharedPrefs.edit()
                val gson = Gson()

                val json = gson.toJson(cricketersList)

                editor.putString("keywords", json)
                editor.commit()






                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
    }

    private fun checkIfValidAndRead(): Boolean {
        cricketersList.clear()
        var result = true
        for (i in 0 until layoutList!!.childCount) {
            val cricketerView = layoutList!!.getChildAt(i)
            val editTextName =
                cricketerView.findViewById<View>(R.id.edit_cricketer_name) as EditText
            var cricketer = String()
            if (editTextName.text.toString() != "") {
                cricketer= editTextName.text.toString()
            } else {
                result = false
                break
            }
            cricketersList.add(cricketer)
        }
        if (cricketersList.size == 0) {
            result = false
            Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show()
        } else if (!result) {
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show()
        }
        return result
    }

    private fun addView() {
        val cricketerView: View =
            layoutInflater.inflate(R.layout.row_add_cricketer, null, false)
        val editText =
            cricketerView.findViewById<View>(R.id.edit_cricketer_name) as EditText

        val imageClose =
            cricketerView.findViewById<View>(R.id.image_remove) as ImageView
        imageClose.setOnClickListener { removeView(cricketerView) }
        layoutList!!.addView(cricketerView)
    }

    private fun removeView(view: View) {
        layoutList!!.removeView(view)
    }
}