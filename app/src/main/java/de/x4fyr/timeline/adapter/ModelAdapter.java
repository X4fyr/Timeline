package de.x4fyr.timeline.adapter;

import de.x4fyr.timeline.model.Model;

import javax.inject.Inject;

/**
 * Adapter for the view to communicate with the model.
 */
public class ModelAdapter {

    private final Model model;

    @Inject
    public ModelAdapter(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
