package com.schedulerbot

import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File
import java.io.IOException

class MessagesProcessorImpl(private val bot: Bot): MessagesProcessor<Message> {

    private val writer: SpreadsheetWriter<RowModel> by lazy { SpreadsheetWriterService() }

    private val messageParser: MessageParser by lazy { MessageParserImpl() }

    override fun setMessage(message: Message) {
        if (message.hasText()) {
            when (message.text) {
                "/help" -> {
                    sendMsg(message, "can i help u?")
                }
                "/start" -> {
                    writer.createNew()
                    sendMsg(message, "awaiting messages")
                    println("awaiting messages")
                }
                "/end" -> {
                    try {
                        val f: File = writer.saveBook()
                        sendFile(message, InputFile(f))
                        f.delete()
                    } catch (e: IOException) {
                        sendMsg(message, "something gone wrong")
                    }
                }
                "/update" -> {
//                        novaPoshtaService?.getSettlements()
                }
                else -> {
                }
            }
        }

        if(!message.caption.isNullOrEmpty()) {
            println(message.caption)
            parseString(message.caption)
        }
    }

    private fun parseString(caption: String) {
        writeToSheet(messageParser.parseString(caption))
    }

    private fun writeToSheet(arrayRow: RowModel) {
        writer.write(arrayRow)
    }

    private val replyButtons = ReplyKeyboardMarkup(
        listOf(KeyboardRow().apply {
//            add(KeyboardButton("start"))
//            add(KeyboardButton("end"))
            buttons()
        }),
        true,
        false,
        false,
        "message"
    )

    fun buttons(): InlineKeyboardMarkup {
        val k =InlineKeyboardMarkup()
        val b = InlineKeyboardButton()
        b.text = "/start"
        b.callbackData = "sdsda"

        val s = listOf(
                listOf(
                    b, b, b
                )
            )
        k.keyboard = s

        return k
    }

    private fun sendMsg(message: Message, s: String) {
        try {
            bot.execute(SendMessage().apply {
                enableMarkdown(true)
                chatId = message.chatId.toString()
                replyToMessageId = message.messageId
                text = s
                replyMarkup = buttons()
            })
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    private fun sendFile(message: Message, file: InputFile) {
        try {
            bot.execute(SendDocument().apply {
                chatId = message.chatId.toString()
                document = file
            })
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}