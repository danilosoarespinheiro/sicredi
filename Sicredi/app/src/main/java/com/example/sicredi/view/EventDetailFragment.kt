package com.example.sicredi.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sicredi.R
import com.example.sicredi.databinding.FragmentEventDetailBinding
import com.example.sicredi.model.CheckIn
import com.example.sicredi.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_event_detail.*

class EventDetailFragment : Fragment() {

    private var eventId = 0
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentEventDetailBinding
    private val peopleAdapter : PeopleAdapter = PeopleAdapter(ArrayList())
    private lateinit var checkIn : CheckIn

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            eventId = EventDetailFragmentArgs.fromBundle(requireArguments()).eventId
        }

        viewModel = ViewModelProviders.of(this)[DetailsViewModel::class.java]
        viewModel.fetchFromServer(eventId.toString())

        peopleRecyclerView.layoutManager = LinearLayoutManager(context)
        peopleRecyclerView.adapter = peopleAdapter

        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val binding: FragmentEventDetailBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_event_detail, container, false)
        this.binding = binding
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun observeViewModel(){
        viewModel.events.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it !=null ){
                binding.event = it
                peopleRecyclerView.visibility = View.VISIBLE
                peopleAdapter.updatePeopleList(it.people)
            }
        })

        viewModel.eventsLoadError.observe(viewLifecycleOwner, androidx.lifecycle.Observer {})

        viewModel.peopleLoadError.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                listEmptyMsg.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it != null){
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listEmptyMsg.visibility = View.GONE
                    peopleRecyclerView.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share the content")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    eventId
                )
                startActivity(Intent.createChooser(intent, "Share with"))
            }
            R.id.action_check_in -> {
                AlertDialog.Builder(this!!.requireContext())
                    .setTitle("Check-in  ")
                    .setMessage("Do you want to check-in ?")
                    .setPositiveButton(
                        "Yes"
                    ) { _: DialogInterface?, _: Int ->
                         viewModel.checkIn(buildCheckIn())
                    }
                    .setNegativeButton(
                        "No"
                    ) { dialog: DialogInterface?, _: Int ->
                        dialog?.dismiss()
                    }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buildCheckIn() : CheckIn{
        checkIn = CheckIn(eventId, "Otávio", "otavio_souza@...")
        return checkIn
    }
}