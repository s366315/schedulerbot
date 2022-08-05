package com.schedulerbot

interface MessageParser {
    fun parseString(caption: String): RowModel
}