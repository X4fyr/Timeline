package de.x4fyr.timeline.domain.elements

import java.time.Duration
import java.time.LocalDate

/**
 * Element stashed as a To-Do with optional date and duration.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
open class TodoElement(title: String, notes: String) : Element(title, notes) {
    var plannedDate: LocalDate? = null
    var plannedDuration: Duration? = null
}
