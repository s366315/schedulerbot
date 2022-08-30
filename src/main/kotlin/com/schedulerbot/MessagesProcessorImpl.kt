package com.schedulerbot

import com.schedulerbot.messages.*
import com.schedulerbot.messages.MasterState.*
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File
import java.io.IOException
import java.util.*

class MessagesProcessorImpl(private val bot: Bot) : MessagesProcessor<Update> {

    private val writer: SpreadsheetWriter<RowModel> by lazy { SpreadsheetWriterService() }

    private val messageParser: MessageParser by lazy { MessageParserImpl() }

    private var currentMasterState = GREETINGS

    private val subscriptionMaster: HashMap<MasterState, SendMessage> = HashMap()

    override fun setMessage(update: Update?) {
        val message = update?.message


        if (message?.hasText() == true) {

            when (message.text) {
                "/help" -> {
                    sendMsg(message, "can I help u?")
                }
                "/start" -> {
//                    resetMaster(message)
//                    writer.createNew()
                    sendMsg(commonMessage(message).greetingsMessage(message))
                    println("awaiting messages")
                    println(message.chat)
                    println(message.contact)
                    println(message.from)
                }
                "/report" -> {
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
        } else {
            update?: return

            val callbackState = MasterState.getState(update.callbackQuery?.data)

            when (callbackState) {
                GREETINGS -> {
                    if (message?.contact != null) {
                        sendMsg(selectDayMessage(update))
                    }
                }
                DAY -> {
                    sendMsg(selectHourMessage(update))
                }
                HOUR -> {
                    sendMsg(submitSignUpMessage(update))
                }
                SUBMIT -> {
                    sendMsg(summaryMessage(update))
                }
                SUMMARY -> {

                }
            }
        }

        if (!message?.caption.isNullOrEmpty()) {
            println(message?.caption)
            parseString(message!!.caption!!)
        }
    }

    private fun parseString(caption: String) {
        writeToSheet(messageParser.parseString(caption))
    }

    private fun writeToSheet(arrayRow: RowModel) {
        writer.write(arrayRow)
    }

    private fun sendMsg(message: Message, s: String) {
        try {
            bot.execute(SendMessage().apply {
                enableMarkdown(true)
                chatId = message.chatId.toString()
                replyToMessageId = message.messageId
                text = s
            })
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    private fun sendMsg(message: EditMessageText) {
        try {
            bot.execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    private fun sendMsg(message: SendMessage) {
        try {
            bot.execute(message)
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