package com.example.week2.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.week2.R
import com.example.week2.databinding.FragmentEntryBinding
import com.example.week2.db.DataBaseHelper
import com.example.week2.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EntryFragment : Fragment() {

    private val binding by lazy {
        FragmentEntryBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding.eventNameEntry.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.saveEventBtn.isEnabled = p0?.isNotEmpty() ?: false
            }

            override fun afterTextChanged(p0: Editable?) {
                // no-op
            }

        })



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.saveEventBtn.setOnClickListener {
            val name = binding.eventNameEntry.text.toString()
            val category = binding.eventCategoryEntry.text.toString()
            val date = binding.calendarEvent.date
            val dateString = getDate(date, "dd-MM-yyyy")!!
            val dataBaseHelper = DataBaseHelper(requireContext());
            dataBaseHelper.insertData(name,category,date)
            Event(0,name, category, date).also {
                findNavController().navigate(
                    R.id.action_EntryFragment_to_MainFragment, bundleOf(
                        Pair(EVENT_DATA, it)
                    ))
            }

        }

    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    companion object {
        const val EVENT_DATA = "EVENT_DATA"
    }
}