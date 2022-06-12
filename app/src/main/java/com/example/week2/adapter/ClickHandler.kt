package com.example.week2.adapter

import com.example.week2.model.Event

interface ClickHandler {
    fun onEventItemClick(event: Event)
}