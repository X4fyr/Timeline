/**
 * Created by x4fyr on 1/22/17.
 */

declare namespace timelineIonicAdapter {
    namespace timeline {
        class IonicServiceProvider {
            id: number;
            elementService: services.ElementService;
            scheduleService: services.ScheduleService;
            todoListService: services.TodoListService
        }
        namespace domain {
            class Timeline {
            }
            namespace elements {
                class ExternalElement {
                }
                class ScheduledElement {
                    constructor(id: number | null, title: string, notes: string, start: utils.date.DateTime, duration: utils.date.Duration, externalId: number | null);
                }
                class TodoElement {
                }
            }
        }
        namespace services {
            class ElementService {
                saveScheduledElement(element: domain.elements.ScheduledElement): domain.elements.ScheduledElement
                saveTodoElement(element: domain.elements.TodoElement): domain.elements.TodoElement
                scheduleElement(todoElement: domain.elements.TodoElement, startTime: utils.date.Time, startDate: utils.date.DateTime): domain.elements.ScheduledElement
                unscheduleElement(scheduledElement: domain.elements.ScheduledElement, keepDate: Boolean | true): domain.elements.TodoElement
                scheduleExternalElement(externalElement: domain.elements.ExternalElement): domain.elements.ScheduledElement
            }
            class TodoListService {
            }
            class ScheduleService {
            }
        }
    }
    namespace utils {
        namespace date {
            class DateOnly {
                constructor(year: number, month: number, dayOfMonth: number);

                at_e3hzcg$(time: utils.date.Time): utils.date.DateTime;

                atStartOfDay(): utils.date.DateTime;

                atEndOfDay(): utils.date.DateTime;

                getYear(): number;

                getMonth(): number;

                getDayOfMonth(): number;

                getEpochMillis(): number;
            }
            class DateTime {
                constructor(year: number, month: number, dayOfMonth: number, hours: number, minutes: number, seconds: number, milliseconds: number);

                copy_ui44o2$(year: number, month: number, dayOfMonth: number, hours: number, minutes: number, seconds: number, milliseconds: number): utils.date.DateTime;

                atStartOfDay(): utils.date.DateTime;

                atEndOfDay(): utils.date.DateTime;

                withTime_e3hzcg$(time: utils.date.Time): utils.date.DateTime;

                getYear(): number;

                getMonth(): number;

                getDayOfMonth(): number;

                getHours(): number;

                getMinutes(): number;

                getSeconds(): number;

                getMillis(): number;

                getEpochMillis(): number;

                static now(): utils.date.DateTime;
            }
            class Duration {
                constructor(millis: number);
            }
            class Time {
                constructor(hour: number, minute: number, second: number, millis: number);

                getHours(): number;

                getMinutes(): number;

                getSeconds(): number;

                getMillis(): number;

                getEpochMillis(): number;

                Companion: {
                    MIN: utils.date.Time;
                    MAX: utils.date.Time;
                }
            }
        }
    }
}

declare module "timeline-ionicAdapter" {
    export = timelineIonicAdapter;
}