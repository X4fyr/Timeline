package de.x4fyr.util

import org.joda.time.Duration
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import java.util.Random

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

fun Random.nextLocalDate(): LocalDate {
    val month = this.nextInt(de.x4fyr.util.MAX_MONTHS-1)+1
    val day: Int
    if (month == 2) {
        day = this.nextInt(27)+1
    } else {
        day = this.nextInt(MAX_DAYS-1)+1
    }
    return LocalDate(this.nextInt(MAX_YEARS), month, day)
}

fun Random.nextLocalTime(): LocalTime = LocalTime(this.nextInt(MAX_HOURS-1)+1, this.nextInt(MAX_MINUTES-1)+1,
        this.nextInt(MAX_SECONDS-1)+1, this.nextInt
(MAX_MILLIS-1)+1)

fun Random.nextLocalDateTime(): LocalDateTime = this.nextLocalDate().toLocalDateTime(this.nextLocalTime())

fun Random.nextDuration(): Duration = Duration(this.nextLong())

fun Random.nextString(length: Long = 1000): String {
    var result = ""
    for (i in 1..length) {
        result += this.nextInt(Character.MAX_VALUE.toInt()).toChar()
    }
    return result
}

fun Random.nextLongPositive(): Long {
    val long = this.nextLong()
    return if (long < 0) long*-1
    else long
}

fun Random.nextIntPositive(): Int {
    val int = this.nextInt()
    return if (int < 0) int*-1
    else int
}

fun Random.nextStartDurationEnd(): Triple<LocalDateTime, Duration, LocalDateTime> {
    val start = this.nextLocalDateTime()
    var duration = this.nextDuration()
    var end: LocalDateTime? = null
    while (end == null) {
        try {
            end = start.plus(duration)
        } catch (e: ArithmeticException) {
            duration = Duration(duration.millis/2)
        }
    }
    return Triple(start, duration, end)
}

