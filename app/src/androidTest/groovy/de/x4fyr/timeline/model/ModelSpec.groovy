package de.x4fyr.timeline.model

import org.reflections.Reflections
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by x4fyr on 10/18/16.
 */
@Unroll
class ModelSpec extends Specification {

    def static List<Model> MODELS;

    static { // find all implementations using reflections
        List<Model> modelsTmp = new LinkedList<>();
        Reflections reflections = new Reflections("de.x4fyr.timeline.model");
        Set<Class<? extends Model>> modelClasses = reflections.getSubTypesOf(Model.class);
        for (Class<? extends Model> model: modelClasses) {
            modelsTmp.add(model.getConstructor().newInstance())
        }
        MODELS = modelsTmp;
    }
}
