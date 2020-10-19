package com.example.sicredi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sicredi.R
import com.example.sicredi.databinding.ItemPersonBinding
import com.example.sicredi.model.Person
import java.util.*

class PeopleAdapter (private val peopleList: ArrayList<Person>)
    : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    fun updatePeopleList(newPeopleList: List<Person>) {
        peopleList.clear()
        peopleList.addAll(newPeopleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PeopleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemPersonBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_person, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    class PeopleViewHolder(
        itemView: ItemPersonBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemview: ItemPersonBinding = itemView
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.itemview.person = peopleList[position]
    }

}