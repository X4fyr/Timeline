package timeline.domain


/**
 * Timeline aggregating scheduled elements.
 *
 * Basically this is a Set, that gets sorted on modify.
 * @param E target elements of this timeline.
 * @author Benedikt Volkmer
 *         Created on 11/1/16.
 */
class Timeline<E : Comparable<E>>() : MutableList<E> {
    private var timeline: List<E> = listOf()

    constructor(elements: Set<E>) : this() {
        addAll(elements)
    }


    override val size: Int
        get() = timeline.size

    // Sorting functions
    override fun add(element: E): Boolean = modifySorted { it.add(element) }
    override fun addAll(elements: Collection<E>): Boolean = modifySorted { it.addAll(elements) }
    override fun clear() = modifySorted { it.clear() }
    override fun remove(element: E): Boolean = modifySorted { it.remove(element) }
    override fun removeAll(elements: Collection<E>): Boolean = modifySorted { it.removeAll(elements) }
    override fun retainAll(elements: Collection<E>): Boolean = modifySorted { it.retainAll(elements) }
    override fun removeAt(index: Int): E = modifySorted { it.removeAt(index) }

    // Delegation function
    override fun contains(element: E): Boolean = timeline.contains(element)
    override fun containsAll(elements: Collection<E>): Boolean = timeline.containsAll(elements)
    override fun iterator(): MutableIterator<E> = timeline.toMutableList().iterator()
    override fun isEmpty(): Boolean = timeline.isEmpty()
    override fun get(index: Int): E = timeline[index]
    override fun indexOf(element: E): Int = timeline.indexOf(element)
    override fun lastIndexOf(element: E): Int = timeline.lastIndexOf(element)
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> = timeline.toMutableList().subList(fromIndex,
            toIndex)

    // Sorting function breaking the expected function
    @Deprecated(message = "index useless as it is sorted")
    override fun add(index: Int, element: E) = modifySorted { it.add(index, element) }
    @Deprecated(message = "index useless as it is sorted")
    override fun addAll(index: Int, elements: Collection<E>): Boolean = modifySorted { it.addAll(index, elements) }
    @Deprecated(message = "index useless as it is sorted")
    override fun set(index: Int, element: E): E = modifySorted { it.set(index, element) }
    @Deprecated(message = "Not running on the same list. Not usable for modifications")
    override fun listIterator(): MutableListIterator<E> = timeline.toMutableList().listIterator()
    @Deprecated(message = "Not running on the same list. Not usable for modifications")
    override fun listIterator(index: Int): MutableListIterator<E> = timeline.toMutableList().listIterator()

    private fun <R> modifySorted(modification: (timeline: MutableList<E>) -> R): R {
        val tmp = timeline.toMutableList()
        val result = modification(tmp)
        timeline = tmp.sorted().toList()
        return result
    }

}
