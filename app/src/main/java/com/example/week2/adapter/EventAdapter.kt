package com.example.week2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2.databinding.TodoItemBinding
import com.example.week2.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(
    // clock handling with interface
    private val onEventClickHandler: ClickHandler,
    private val eventsList: MutableList<Event> = mutableListOf(),
    // click handling with high order function
    private val onClickEventHighOrderFunction: (Event) -> Unit
) : RecyclerView.Adapter<EventViewHolder>() {

    fun updateEventsList(event: ArrayList<Event>) {
//        eventsList.add(event)
        event.sortWith { o1, o2 -> o1.date.compareTo(o2.date) }

        eventsList.addAll(event)
        notifyDataSetChanged()
//        notifyItemInserted(eventsList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(eventsList[position], onEventClickHandler, onClickEventHighOrderFunction)

    override fun getItemCount(): Int = eventsList.size
}

class EventViewHolder(
    private val binding: TodoItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        event: Event,
        onEventClickHandler: ClickHandler,
        onClickEventHighOrderFunction: (Event) -> Unit
    ) {
        binding.eventName.text = event.name
        binding.eventCategory.text = event.category
        binding.eventDate.text = getDate(event.date, "dd-MM-yyyy")

        itemView.setOnClickListener {
            // this is handling the click with interface
            // onEventClickHandler.onEventItemClick(event)

            // this is handling click with high order function
            onClickEventHighOrderFunction(event)
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

}