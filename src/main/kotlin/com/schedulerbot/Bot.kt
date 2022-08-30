package com.schedulerbot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

class Bot : TelegramLongPollingBot() {
    private val messagesProcessor: MessagesProcessor<Update> by lazy { MessagesProcessorImpl(this) }

    override fun getBotToken() = System.getenv(TOKEN_KEY) ?: throw IllegalArgumentException(EXCEPTION)

    override fun getBotUsername() = "SchedulerBot"

    override fun onUpdateReceived(update: Update?) {
        messagesProcessor.setMessage(update)
    }

    companion object {
        private const val TOKEN_KEY = "SCHEDULER_BOT_TOKEN"
        private const val EXCEPTION = "The bot token must be set with $TOKEN_KEY"
    }
}