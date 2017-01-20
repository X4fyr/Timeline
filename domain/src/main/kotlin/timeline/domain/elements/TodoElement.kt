package timeline.domain.elements

import utils.date.DateOnly
import utils.date.Duration


/**
 * Element stashed as a To-Do with optional date and duration.
 * @author Benedikt Volkmer
 *         Created on 11/4/16.
 */
data class TodoElement(val id: Long? = null,
                       var title: String,
                       var notes: String,
                       var plannedDate: DateOnly? = null,
                       var plannedDuration: Duration? = null)
