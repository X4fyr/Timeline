package de.x4fyr.timeline.adapter;

import de.x4fyr.timeline.model.Model;

import javax.inject.Inject;

/**
 * Created by x4fyr on 10/18/16.
 */
public class ModelAdapter {

    private Model model;

    @Inject
    public ModelAdapter(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}
