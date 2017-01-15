package de.x4fyr.timeline.domain.elements

import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * Element scheduled for a specific date and time.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class ScheduledElement(val id: Long? = null,
                            var title: String,
                            var notes: String,
                            var start: LocalDateTime,
                            var duration: Duration,
                            val externalId: Long? = null)
    : Comparable<ScheduledElement>{

    /** Compares the start TimeDates */
    override fun compareTo(other: ScheduledElement) = start.compareTo(other.start)
}
