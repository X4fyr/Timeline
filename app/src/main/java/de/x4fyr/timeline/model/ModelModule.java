package de.x4fyr.timeline.model;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Model module declaration for dependency injection.
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
