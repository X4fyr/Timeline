package timeline.domain.elements

import utils.date.DateTime
import utils.date.Duration

/**
 * Element coming from an external source like a calendar.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class ExternalElement(val id: Long? = null,
                           var title: String,
                           var notes: String,
                           var start: DateTime,
                           var duration: Duration,
                           internal val scheduledId: Long? = null)

