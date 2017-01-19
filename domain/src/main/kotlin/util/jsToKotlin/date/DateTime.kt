package util.jsToKotlin.date


/**
 * @author x4fyr
 * Created on 1/18/17.
 */
open class DateTime(year: Int, month: Int, dayOfMonth: Int, hours: Int, minutes: Int, seconds: Int, milliseconds: Int) : Comparable<DateTime> {

    val jsDate: JsDate = JsDate(year, month, dayOfMonth, hours, minutes, seconds, milliseconds);

    override fun toString(): String = "${getYear()}-${getMonth()}-${getDayOfMonth()}T${getHours()}:${getMinutes()}:${getSeconds()}"
    override fun compareTo(other: DateTime): Int = getEpochMillis() - other.getEpochMillis()
    override fun equals(other: Any?): Boolean = other is DateTime && getEpochMillis() == other.getEpochMillis()
    override fun hashCode(): Int = getEpochMillis()

    fun compareTo(other: DateOnly): Int = getEpochMillis() - other.getEpochMillis()

    fun toDate(): DateOnly {
        return util.jsToKotlin.date.DateOnly(getYear(), getMonth(), getDayOfMonth())
    }

    fun copy(year: Int = this.getYear(),
             month: Int = getMonth(),
             dayOfMonth: Int = this.getDayOfMonth(),
             hours: Int = this.getHours(),
             minutes: Int = this.getMinutes(),
             seconds: Int = this.getSeconds(),
             milliseconds: Int = this.getMillis()) =
            DateTime(year, month, dayOfMonth, hours, minutes, seconds, milliseconds)

    fun atStartOfDay() = this.withTime(Time.MIN)
    fun atEndOfDay() = this.withTime(Time.MAX)
    fun withTime(time: Time) = this.copy(hours = time.getHours(), minutes = time.getMinutes(), seconds = time
            .getSeconds(), milliseconds = time.getMillis())

    fun getYear(): Int = jsDate.getFullYear()
    fun getMonth(): Int = jsDate.getMonth()
    fun getDayOfMonth(): Int = jsDate.getDate()
    fun getHours(): Int = jsDate.getHours()
    fun getMinutes(): Int = jsDate.getMinutes()
    fun getSeconds(): Int = jsDate.getSeconds()
    fun getMillis(): Int = jsDate.getMilliseconds()
    fun getEpochMillis(): Int = jsDate.getTime()

    companion object {
        fun ofJsDate(jsDate: JsDate) : DateTime = DateTime(jsDate.getFullYear(), jsDate.getMonth(), jsDate.getDate(),
                jsDate.getHours(), jsDate.getMinutes(), jsDate.getSeconds(), jsDate.getMilliseconds())
        fun now(): DateTime = ofJsDate(JsDate())
    }

}
