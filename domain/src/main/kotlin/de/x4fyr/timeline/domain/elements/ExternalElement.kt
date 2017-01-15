package de.x4fyr.timeline.domain.elements

import org.joda.time.Duration
import org.joda.time.LocalDateTime

/**
 * Element coming from an external source like a calendar.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class ExternalElement(val id: Long? = null,
                           var title: String,
                           var notes: String,
                           var start: LocalDateTime,
                           var duration: Duration,
                           internal val scheduledId: Long? = null)

