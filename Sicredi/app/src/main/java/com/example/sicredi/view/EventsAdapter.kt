package com.example.sicredi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sicredi.R
import com.example.sicredi.databinding.ItemEventBinding
import com.example.sicredi.model.Event
import java.util.*

class EventsAdapter(private val eventsList: ArrayList<Event>)
    : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>(),
    EventClickListener {

    fun updateEventList(newEventsList: List<Event>) {
        eventsList.clear()
        eventsList.addAll(newEventsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemEventBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_event, parent, false)
        return EventsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventsViewHolder(
        itemView: ItemEventBinding
    ) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemview: ItemEventBinding = itemView
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.itemview.event = eventsList[position]
        holder.itemview.listener = this
    }

    override fun onEventClicked(v: View) {
        val uuidString =
            (v.findViewById<View>(R.id.eventId) as TextView).text.toString()
        val uuid = Integer.valueOf(uuidString)
        Navigation.findNavController(v)
            .navigate(EventListFragmentDirections.actionEventListFragmentToEventDetailFragment(uuid))
    }
}