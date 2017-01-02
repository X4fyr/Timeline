package de.x4fyr.timeline.domain.elements

import org.joda.time.Duration
import org.joda.time.LocalDateTime
import java.util.UUID

/**
 * Element scheduled for a specific date and time.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
open class ScheduledElement(
        /** Start of the Element */
        var start: LocalDateTime,
        /** Duration of the Element */
        var duration: Duration, title: String, notes: String,
        /** optional UUID of the linked external element */
        var externalUUID: UUID? = null)
    : Element(title, notes), Comparable<ScheduledElement> {

    /** Compares the start TimeDates */
    override fun compareTo(other: ScheduledElement) = start.compareTo(other.start)
}
