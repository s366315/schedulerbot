package com.schedulerbot.dto

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "session_entity")
class SessionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Int? = null,

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @Column(name = "phone", nullable = false)
    val phone: String? = null,

    @Column(name = "language", nullable = false)
    val language: String? = null,

    @Column(name = "date_request", nullable = false)
    val dateRequest: Instant? = null,

    @Column(name = "date_start", nullable = false)
    val dateStart: Instant? = null,

    @Column(name = "date_end", nullable = false)
    val dateEnd: Instant? = null
) {
    override fun toString(): String {
        return "\n$id $name $phone $dateEnd"
    }
}