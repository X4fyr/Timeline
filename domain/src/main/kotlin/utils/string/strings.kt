package utils.string

/**
 * Created by x4fyr on 1/22/17.
 */

fun padWithZero(number: Int, minWidth: Int) : String {
    var result = number.toString()
    if (result.length < minWidth) {
        val diff = minWidth - result.length
        for (i in 1..diff) {
            result = "0" + result
        }
        return result
    } else {
        return result
    }
}
