package timeline.adapter

import timeline.domain.elements.ExternalElement

class ExternalScheduleAdapterImpl : ExternalScheduleAdapter {
    override fun setExternalElementScheduled(elementId: Long): ExternalElement {
        throw UnsupportedOperationException("not implemented") //TODO
    }

    override fun unsetExternalElementScheduled(elementId: Long): ExternalElement {
        throw UnsupportedOperationException("not implemented") //TODO
    }
}