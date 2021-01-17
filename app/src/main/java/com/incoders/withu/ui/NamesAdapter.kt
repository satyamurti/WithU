package com.incoders.withu.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incoders.withu.R
import java.util.*

class NamesAdapter(cricketersList: ArrayList<String>) :
    RecyclerView.Adapter<NamesAdapter.CricketerView>() {
    var cricketersList: ArrayList<String> = ArrayList<String>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CricketerView {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_cricketer, parent, false)
        return CricketerView(view)
    }

    override fun onBindViewHolder(
        holder: CricketerView,
        position: Int
    ) {
        val cricketer: String = cricketersList[position]
        holder.textCricketerName.setText(cricketer)

    }

    override fun getItemCount(): Int {
        return cricketersList.size
    }

    inner class CricketerView(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textCricketerName: TextView
        var textTeamName: TextView

        init {
            textCricketerName =
                itemView.findViewById<View>(R.id.text_cricketer_name) as TextView
            textTeamName = itemView.findViewById<View>(R.id.text_team_name) as TextView
        }
    }

    init {
        this.cricketersList = cricketersList
    }
}