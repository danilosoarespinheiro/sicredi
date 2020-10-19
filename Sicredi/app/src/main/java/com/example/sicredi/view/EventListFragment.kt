package com.example.sicredi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sicredi.R
import com.example.sicredi.viewmodel.EventsViewModel
import kotlinx.android.synthetic.main.fragment_event_list.*


class EventListFragment : Fragment() {

    private val eventsAdapter : EventsAdapter = EventsAdapter(ArrayList())
    private lateinit var viewModel: EventsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_event_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[EventsViewModel::class.java]
        viewModel.fetchFromServer()
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.adapter = eventsAdapter

        observeViewModel()

    }

    private fun observeViewModel(){
        viewModel.events.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it !=null ){
                eventsRecyclerView.visibility = View.VISIBLE
                eventsAdapter.updateEventList(it)
            }
        })

        viewModel.eventsLoadError.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                listEmptyMsg.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listEmptyMsg.visibility = View.GONE
                    eventsRecyclerView.visibility = View.GONE
                }
            }
        })
    }

}