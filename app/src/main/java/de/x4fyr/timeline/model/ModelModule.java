package de.x4fyr.timeline.model;

import dagger.Module;
import dagger.Provides;
import de.x4fyr.timeline.adapter.ModelAdapter;

import javax.inject.Singleton;

/**
 * Created by x4fyr on 10/18/16.
 */
@Module
public class ModelModule {

    private final Boolean isCalendarActivated;

    public ModelModule(Boolean isCalendarActivated) {
        this.isCalendarActivated = isCalendarActivated;
    }

    @Provides @Singleton
    Model provideModel() {
        return isCalendarActivated ? new CalendarModel() : new InAppModel();
    }
}
