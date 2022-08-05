package com.schedulerbot

interface MessagesProcessor<T> {
    fun setMessage(message: T)
}