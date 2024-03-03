package net.kravuar.schedule.ports.out;

public interface ScheduleLockPort {
    /**
     * Lock action on the schedule.
     *
     * @param scheduleId schedule id to lock on
     * @param acquire    whether to acquire/release lock
     */
    void lock(long scheduleId, boolean acquire);

    /**
     * Lock action on the staff schedules.
     *
     * @param staffId schedules staff id to lock on
     * @param serviceId service id to lock on
     * @param acquire whether to acquire/release lock
     */
    void lockByStaffAndService(long staffId, long serviceId, boolean acquire);
}