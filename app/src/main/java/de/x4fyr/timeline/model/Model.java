package de.x4fyr.timeline.model;

import org.threeten.bp.LocalDate;

import java.util.SortedSet;

/**
 * Created by x4fyr on 10/16/16.
 */
public interface Model {

    SortedSet<Element> getAllElements();

    SortedSet<Element> getElements(LocalDate date);
}
