package de.x4fyr.timeline.domain

import de.x4fyr.timeline.domain.elements.ScheduledElement
import java.util.*

/**
 * Timeline aggregating scheduled elements.
 *
 * Basically this is a LinkedList, that gets sorted on add.
 * @param E target elements of this timeline.
 * @author Benedikt Volkmer
 *         Created on 11/1/16.
 */
class Timeline<E : ScheduledElement>() : LinkedList<E>() {

    @SafeVarargs
    constructor(vararg elements: E) : this() {
        this.addAll(Arrays.asList(*elements))
    }

    constructor(elements: Collection<E>) : this() {
        this.addAll(elements)
    }

    override fun add(element: E): Boolean {
        val modified = super.add(element)
        sortWith(COMPARATOR)
        return modified
    }

    override fun add(index: Int, element: E) {
        add(element)
    }

    override fun addAll(elements: Collection<E>): Boolean {
        val modified = super.addAll(elements)
        sortWith(COMPARATOR)
        return modified
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        val modified = super.addAll(size, elements)
        sortWith(COMPARATOR)
        return modified
    }

    override fun addFirst(element: E) {
        add(element)
    }

    override fun addLast(element: E) {
        add(element)
    }

    override fun set(index: Int, element: E): E {
        val oldElement = removeAt(index)
        add(element)
        return oldElement
    }

    override fun push(e: E) {
        addFirst(e)
    }

    companion object {

        private val COMPARATOR = Comparator.naturalOrder<ScheduledElement>()
    }

}
