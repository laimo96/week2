package com.example.week2.model

import java.io.Serializable

data class Event(
    val id: Int,
    val name: String,
    val category: String,
    val date: Long
) : Serializable, Comparable<Event> {
    override fun compareTo(other: Event): Int {
        TODO("Not yet implemented")
    }
}
