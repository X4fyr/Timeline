package utils.date

/**
 * @author x4fyr
 * Created on 1/18/17.
 */
class Duration(var millis: Int) : Comparable<Duration> {
    override fun compareTo(other: Duration): Int = millis - other.millis
}