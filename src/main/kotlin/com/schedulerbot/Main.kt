package com.schedulerbot

import com.schedulerbot.databaseUtils.HibernateUtil
import com.schedulerbot.dto.SessionEntity
import org.hibernate.resource.transaction.spi.TransactionStatus
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.time.Instant

fun main(args: Array<String>) {
    TelegramBotsApi(DefaultBotSession::class.java).apply {
        registerBot(Bot())

        val session = HibernateUtil.sessionFactory?.openSession()

        session?.beginTransaction()

        session?.saveOrUpdate(
            SessionEntity(
                name = "Willis",
                phone = "+654654654654",
                language = "ru",
                dateRequest = Instant.now(),
                dateStart = Instant.now(),
                dateEnd = Instant.now()
            )
        )

        println(session?.createQuery("from SessionEntity", SessionEntity::class.java)?.resultList)

//        HibernateUtil.shutdown()
    }
}