package de.x4fyr.timeline.domain.elements

import org.joda.time.Duration
import org.joda.time.LocalDate

/**
 * Element stashed as a To-Do with optional date and duration.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class TodoElement(val id: Long? = null,
                       var title: String,
                       var notes: String,
                       var plannedDate: LocalDate? = null,
                       var plannedDuration: Duration? = null)
