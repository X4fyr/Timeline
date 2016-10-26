package de.x4fyr.timeline.model;

import org.threeten.bp.LocalDate;

import java.util.SortedSet;

/**
 * Model interface for use in the adapter layer.
 */
public interface Model {

    SortedSet<Element> getAllElements();

    SortedSet<Element> getElements(LocalDate date);
}
