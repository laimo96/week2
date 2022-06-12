package com.example.week2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week2.R
import com.example.week2.adapter.ClickHandler
import com.example.week2.adapter.EventAdapter
import com.example.week2.databinding.FragmentMainBinding
import com.example.week2.db.DataBaseHelper
import com.example.week2.model.Event
import java.util.*

class MainFragment : Fragment(), ClickHandler {

    private lateinit var dataBaseHelper: DataBaseHelper
    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val eventAdapter by lazy {
        EventAdapter(this) { event ->
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundleOf(Pair(EntryFragment.EVENT_DATA, event))
            )
        }
    }

    private var newEvent: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            newEvent = it.getSerializable(EntryFragment.EVENT_DATA) as? Event
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dataBaseHelper = DataBaseHelper(requireContext())

        val list = dataBaseHelper.data
        val mList: ArrayList<Event> = ArrayList()
//        for (i in 0 until list.size)
//        {
//            mList.add(Event(list[i].name,list[i].category,list[i].date.toString()))
//        }
        binding.todoRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
            adapter = eventAdapter

          //  Arrays.sort(dataBaseHelper.data, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
            eventAdapter.updateEventsList(list)
        }

        binding.createEvent.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_entryFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val list = dataBaseHelper.data
        val mList: ArrayList<Event> = ArrayList()
//        for (i in 0 until list.size)
//        {
//            mList.add(Event(list[i].name,list[i].category,list[i].date.toString()))
//        }
//        newEvent?.let {
//            eventAdapter.updateEventsList(dataBaseHelper.data)
//            newEvent = null
//            arguments = null
//        }
    }

    override fun onEventItemClick(event: Event) {
        findNavController().navigate(
            R.id.action_mainFragment_to_detailsFragment,
            bundleOf(Pair(EntryFragment.EVENT_DATA, event))
        )
    }
}