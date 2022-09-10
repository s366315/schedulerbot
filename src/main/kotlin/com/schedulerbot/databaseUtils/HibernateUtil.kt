package com.schedulerbot.databaseUtils

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object HibernateUtil {
    var sessionFactory: SessionFactory? = buildSessionFactory()

    private fun buildSessionFactory(): SessionFactory {
        try {
            if (sessionFactory == null) {
                val standardRegistry = StandardServiceRegistryBuilder()
                    .configure("config/hibernate.cfg.xml")
                    .build()

                val metadata = MetadataSources(standardRegistry)
                    .metadataBuilder
                    .build()

                sessionFactory = metadata.sessionFactoryBuilder.build()
            }
            return sessionFactory!!
        } catch (e: Exception) {
            throw ExceptionInInitializerError(e)
        }
    }

    fun shutdown() {
        sessionFactory?.close()
    }
}