package com.schedulerbot

interface MessagesProcessor<T> {
    fun setMessage(update: T?)
}