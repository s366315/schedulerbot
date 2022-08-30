package com.schedulerbot.messages

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

private val greetingsButtons = ReplyKeyboardMarkup(
    listOf(KeyboardRow().apply {
        add(KeyboardButton("Поделиться номером").apply { requestContact = true })
    }),
    true,
    true,
    false,
    "message"
)

val dayButtons = InlineKeyboardMarkup().apply {
    var day = 0
    val callback = MasterState.DAY.toString()

    keyboard = mutableListOf(
        mutableListOf(
            InlineKeyboardButton().apply {
                text = "ПН"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "ВТ"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "СР"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "ЧТ"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "ПТ"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "СБ"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "ВС"
                callbackData = callback
            }
        )
    )
}

val hourButtons = InlineKeyboardMarkup().apply {
    val callback = MasterState.HOUR.toString()

    keyboard = mutableListOf(
        mutableListOf(
            InlineKeyboardButton().apply {
                text = "8:00"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "10:00"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "12:00"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "14:00"
                callbackData = callback
            },
        )
    )
}

val submitButtons = InlineKeyboardMarkup().apply {
    val callback = MasterState.SUBMIT.toString()

    keyboard = mutableListOf(
        mutableListOf(
            InlineKeyboardButton().apply {
                text = "ДА"
                callbackData = callback
            },
            InlineKeyboardButton().apply {
                text = "НЕТ"
                callbackData = callback
            }
        )
    )
}

fun commonMessage(message: Message) = SendMessage().apply {
    enableMarkdown(true)
    chatId = message.chatId.toString()
}

fun SendMessage.greetingsMessage(message: Message): SendMessage {
    text = "Привет, ${message.from.firstName}! Нам нужен твой телефон для связи"
    replyMarkup = greetingsButtons

    return this
}

fun greetingsMessage(message: Message? = null) = SendMessage().apply {
//    text = "Привет, ${message.from.firstName}! Нам нужен твой телефон для связи"
    replyMarkup = greetingsButtons

    return this
}

fun selectDayMessage(update: Update) = SendMessage().apply {
    chatId = Math.toIntExact(update.message.chatId).toString()
    text = "Выбери день"
    replyMarkup = dayButtons
}

fun selectHourMessage(update: Update) = EditMessageText().apply {
    chatId = Math.toIntExact(update.callbackQuery.message.chatId).toString()
    messageId = update.callbackQuery.message.messageId
    text = "Выбери время"
    replyMarkup = hourButtons
}

fun submitSignUpMessage(update: Update) = EditMessageText().apply {
    chatId = Math.toIntExact(update.callbackQuery.message.chatId).toString()
    messageId = update.callbackQuery.message.messageId
    text = "Мы тебя ждём 1 Мая в 15:00 \n Всё верно?"
    replyMarkup = submitButtons
}

fun summaryMessage(update: Update) = EditMessageText().apply {
    chatId = Math.toIntExact(update.callbackQuery.message.chatId).toString()
    messageId = update.callbackQuery.message.messageId
    text = "Мы тебя ждём 1 Мая в 15:00!"
    replyMarkup = null
}