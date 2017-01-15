package de.x4fyr.timeline.domain

import java.util.Arrays
import java.util.Comparator
import java.util.LinkedList

/**
 * Timeline aggregating scheduled elements.
 *
 * Basically this is a LinkedList, that gets sorted on add.
 * @param E target elements of this timeline.
 * @author Benedikt Volkmer
 *         Created on 11/1/16.
 */
class Timeline<E : Comparable<E>>() : LinkedList<E>() {

    private val comparator = Comparator.naturalOrder<E>()

    @SafeVarargs
    constructor(vararg elements: E) : this() {
        this.addAll(Arrays.asList(*elements))
    }

    constructor(elements: Collection<E>) : this() {
        this.addAll(elements)
    }

    /** Add element */
    override fun add(element: E): Boolean {
        val modified = super.add(element)
        sortWith(comparator)
        return modified
    }

    /** Add element
     * index will be ignored as this list is sorted
     */
    override fun add(index: Int, element: E) {
        add(element)
    }

    /** Add all elements */
    override fun addAll(elements: Collection<E>): Boolean {
        val modified = super.addAll(elements)
        sortWith(comparator)
        return modified
    }

    /** Add all elements
     * index will be ignored as this list is sorted
     */
    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        val modified = super.addAll(size, elements)
        sortWith(comparator)
        return modified
    }

    /** Add element
     * It will be not necessarily be first, as this list is sorted
     */
    override fun addFirst(element: E) {
        add(element)
    }

    /** Add element
     * It will be not necessarily be last, as this list is sorted
     */
    override fun addLast(element: E) {
        add(element)
    }

    /** Replace element
     * It will no necessarily be at the same position, as this list is sorted
     */
    override fun set(index: Int, element: E): E {
        val oldElement = removeAt(index)
        add(element)
        return oldElement
    }

    /** Add element
     * It will not necessarily be first, as this list is sorted
     */
    override fun push(e: E) {
        addFirst(e)
    }

}
