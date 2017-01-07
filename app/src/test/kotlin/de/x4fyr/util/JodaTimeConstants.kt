package de.x4fyr.util

import org.joda.time.*
import java.util.Random
import java.util.stream.Collectors

/**
 * @author x4fyr
 * Created on 1/6/17.
 */

const val MAX_YEARS = 292278994
const val MAX_MONTHS = 12
const val MAX_DAYS = 31
const val MAX_HOURS = 23
const val MAX_MINUTES = 59
const val MAX_SECONDS = 59
const val MAX_MILLIS = 999

fun Random.localDate(): LocalDate = LocalDate(this.nextInt(MAX_YEARS), this.nextInt(MAX_MONTHS-1)+1, this.nextInt
(MAX_DAYS-1)+1)

fun Random.localTime(): LocalTime = LocalTime(this.nextInt(MAX_HOURS-1)+1, this.nextInt(MAX_MINUTES-1)+1,
        this.nextInt(MAX_SECONDS-1)+1, this.nextInt
(MAX_MILLIS-1)+1)

fun Random.localDateTime(): LocalDateTime = this.localDate().toLocalDateTime(this.localTime())

fun Random.duration(): Duration = Duration(this.nextLong())

fun Random.string(): String = this.ints().boxed().limit(10000000)
        .map(Int::toChar)
        .map(Char::toString)
        .collect(Collectors.joining())

