package de.x4fyr.timeline.domain.elements

import org.joda.time.Duration
import org.joda.time.LocalDate

/**
 * Element stashed as a To-Do with optional date and duration.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
open class TodoElement(title: String, notes: String) : Element(title, notes) {
    /** Optional date this element is planned. */
    var plannedDate: LocalDate? = null
    /** Optional duration this element is planned. */
    var plannedDuration: Duration? = null
}
