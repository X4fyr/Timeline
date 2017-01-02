package de.x4fyr.timeline.domain.elements

import java.util.UUID
import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * Element coming from an external source like a calendar.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
open class ExternalElement(start: LocalDateTime, duration: Duration, title: String, notes: String, externalUUID: UUID)
    : ScheduledElement(start, duration, title, notes, externalUUID) {
    /** Indicates if this element is scheduled */
    var isScheduled = false

}
