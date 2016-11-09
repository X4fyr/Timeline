package de.x4fyr.timeline.domain;

import de.x4fyr.timeline.domain.elements.ScheduledElement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Timeline aggregating scheduled elements.
 * <p>Basically this is a LinkedList, that gets sorted on add.</p>
 *
 * @param <E> target elements of this timeline.
 *
 * @author Benedikt Volkmer
 *         Created on 11/1/16.
 */
public class Timeline<E extends ScheduledElement> extends LinkedList<E> {

    private static final Comparator<ScheduledElement> COMPARATOR = Comparator.comparing(ScheduledElement::getStart);

    public Timeline() {
        super();
    }

    @SafeVarargs
    public Timeline(E... elements) {
        this();
        this.addAll(Arrays.asList(elements));
    }

    public Timeline(Collection<E> elements) {
        this();
        this.addAll(elements);
    }

    @Override
    public boolean add(E element) {
        boolean modified = super.add(element);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public void add(int index, E element) {
        add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = super.addAll(c);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = super.addAll(size(), c);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public void addFirst(E element) {
        add(element);
    }

    @Override
    public void addLast(E element) {
        add(element);
    }

    @Override
    public void sort(Comparator<? super E> c) {
        super.sort(COMPARATOR);
    }

}
