package com.schedulerbot.messages

enum class MasterState(private val state: String) {
    GREETINGS("GREETINGS"),
    DAY("DAY"),
    HOUR("HOUR"),
    SUBMIT("SUBMIT"),
    SUMMARY("SUMMARY");

    operator fun invoke() = state

    companion object {
        fun getState(state: String?) = values().firstOrNull { state == it.state } ?: GREETINGS
    }
}