package util.jsToKotlin.date

/**
 * @author x4fyr
 * Created on 1/19/17.
 */
@native("Date")
class JsDate {
    constructor(year: Int, month: Int, dayOfMonth: Int)
    constructor()
    constructor(millis: Int)
    constructor(year: Int, month: Int, dayOfMonth: Int, hours: Int, minutes: Int, seconds: Int, milliseconds: Int)

    fun getFullYear( ): Int = noImpl
    fun getMonth(): Int = noImpl
    fun getDate(): Int = noImpl
    fun getTime(): Int = noImpl
    fun getHours(): Int = noImpl
    fun getMinutes(): Int = noImpl
    fun getSeconds(): Int = noImpl
    fun getMilliseconds(): Int = noImpl
}