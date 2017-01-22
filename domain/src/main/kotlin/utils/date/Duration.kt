package utils.date

/**
 * @author x4fyr
 * Created on 1/18/17.
 */
class Duration(var millis: Int) : Comparable<Duration> {
    override fun compareTo(other: Duration): Int = millis - other.millis
    override fun toString(): String {
        val hours: Int = millis/(60*60*1000)
        val minutes: Int = millis.rem(60*60*1000)/(60*1000)
        val seconds: Int = millis.rem(60*60*1000).rem(60*1000)/1000
        val millis: Int = millis.rem(60*60*1000).rem(60*1000).rem(1000)
        return "$hours:$minutes:$seconds.$millis"
    }
}