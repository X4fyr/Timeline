package de.x4fyr.timeline.model;

import org.threeten.bp.LocalDate;

import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Created by x4fyr on 10/18/16.
 */
public class InAppModel implements Model {

    SortedMap<LocalDate, SortedSet<Element>> database; //TODO: replace with reasonable on disk database

    @Override
    public SortedSet<Element> getAllElements() {
        return null;
    }

    @Override
    public SortedSet<Element> getElements(LocalDate date) {
        return null;
    }
}
