package de.x4fyr.timeline.domain.elements

import java.time.Duration
import java.time.LocalDateTime
import java.util.*

/**
 * Element scheduled for a specific date and time.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
open class ScheduledElement(var start: LocalDateTime, var duration: Duration, title: String, notes: String, var
externalUUID: UUID? = null)
    : Element(title, notes), Comparable<ScheduledElement> {

    override fun compareTo(other: ScheduledElement): Int = start.compareTo(other.start)
}
