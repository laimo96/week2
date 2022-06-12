package com.example.week2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.week2.R
import com.example.week2.databinding.FragmentDetailsBinding
import com.example.week2.db.DataBaseHelper
import com.example.week2.model.Event
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ChangeFragment : Fragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private var newEvent: Event? = null
    private var selectedDate: Long? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            newEvent = it.getSerializable(EntryFragment.EVENT_DATA) as? Event
        }

        binding.updateEventName.setText(newEvent!!.name)
        binding.updateEventCategory.setText(newEvent!!.category)
        binding.updateCalenderEvent.date = newEvent!!.date

        selectedDate = newEvent!!.date
        val dataBaseHelper = DataBaseHelper(requireContext())
        binding.updateButton.setOnClickListener {
            dataBaseHelper.update(
                newEvent!!.id,
                binding.updateEventName.text.toString(),
                binding.updateEventCategory.text.toString(),
               selectedDate.toString(),
            )
            findNavController().navigate(
                R.id.action_DetailsFragment_to_MainFragment)
        }

        binding.updateCalenderEvent.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            selectedDate = dateToLong("$dayOfMonth/${month +1 }/$year")
        })
        return binding.root
    }

    private fun dateToLong(dateString: String): Long
    {
        var dateInLong: Long = 0
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date: Date = sdf.parse(dateString)
            dateInLong = date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateInLong
    }
}

