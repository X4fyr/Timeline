package utils.date

import utils.jsToKotlin.JsDate

/**
 * @author x4fyr
 * Created on 1/18/17.
 */
class Time(hour: Int, minute: Int, second: Int, millis: Int) : Comparable<Time> {

    val jsDate: JsDate = JsDate(0, 0, 0, hour, minute, second, millis)

    override fun toString(): String = "${getHours()}:${getMinutes()}:${getSeconds()}"
    override fun equals(other: Any?): Boolean = other is Time && getEpochMillis() == other.getEpochMillis()
    override fun hashCode(): Int = getEpochMillis()
    override fun compareTo(other: Time): Int = getEpochMillis() - other.getEpochMillis()

    fun getHours(): Int = jsDate.getHours()
    fun getMinutes(): Int = jsDate.getMinutes()
    fun getSeconds(): Int = jsDate.getSeconds()
    fun getMillis(): Int = jsDate.getMilliseconds()
    fun getEpochMillis(): Int = jsDate.getTime()

    companion object {
        val MIN = Time(0, 0, 0, 0)
        val MAX = Time(23, 59, 59, 999)
    }
}