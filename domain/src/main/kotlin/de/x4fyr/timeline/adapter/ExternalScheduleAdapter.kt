package de.x4fyr.timeline.adapter

import de.x4fyr.timeline.domain.elements.ExternalElement

/**
 * @author Benedikt Volkmer
 *         Created on 11/14/16.
 */
interface ExternalScheduleAdapter {

    fun setExternalElementScheduled(elementId: Long): ExternalElement

    fun unsetExternalElementScheduled(elementId: Long): ExternalElement
}
