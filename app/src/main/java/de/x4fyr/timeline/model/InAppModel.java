package de.x4fyr.timeline.model;

import org.threeten.bp.LocalDate;

import java.util.SortedMap;
import java.util.SortedSet;

/**
 * Model containing the in-app data.
 */
class InAppModel implements Model {

    private SortedMap<LocalDate, SortedSet<Element>> database; //TODO: replace with reasonable on disk database

    @Override
    public SortedSet<Element> getAllElements() {
        return null;
    }

    @Override
    public SortedSet<Element> getElements(LocalDate date) {
        return null;
    }
}
