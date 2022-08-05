package com.schedulerbot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

class Bot : TelegramLongPollingBot() {
    private val messagesProcessor: MessagesProcessor<Message> by lazy { MessagesProcessorImpl(this) }

    override fun getBotToken() = Secrets.botToken

    override fun getBotUsername() = "TradeDocHelperBot"

    override fun onUpdateReceived(update: Update?) {
        update?.message?.let { message ->
            messagesProcessor.setMessage(message)
        }
    }
}