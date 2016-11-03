package de.x4fyr.timeline.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Timeline aggregating elements.
 * <p>Basically this is a LinkedList, that gets sorted on add.</p>
 *
 * @author Benedikt Volkmer
 *         Created on 11/1/16.
 */
public class Timeline extends LinkedList<Element> {

    private static final Comparator<Element> COMPARATOR = Comparator.comparing(Element::getStart);

    public Timeline() {
        super();
    }

    public Timeline(Element... elements) {
        this();
        this.addAll(Arrays.asList(elements));
    }

    public Timeline(Collection<Element> elements) {
        this();
        this.addAll(elements);
    }

    @Override
    public boolean add(Element element) {
        boolean modified = super.add(element);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public void add(int index, Element element) {
        add(element);
    }

    @Override
    public boolean addAll(Collection<? extends Element> c) {
        boolean modified = super.addAll(c);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Element> c) {
        boolean modified = super.addAll(size(), c);
        super.sort(COMPARATOR);
        return modified;
    }

    @Override
    public void addFirst(Element element) {
        add(element);
    }

    @Override
    public void addLast(Element element) {
        add(element);
    }

    @Override
    public void sort(Comparator<? super Element> c) {
        super.sort(COMPARATOR);
    }

}
