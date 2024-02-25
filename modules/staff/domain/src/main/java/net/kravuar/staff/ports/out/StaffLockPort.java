package net.kravuar.staff.ports.out;

public interface StaffLockPort {
    /**
     * Lock action on the staff.
     *
     * @param staffId staff id to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(long staffId, boolean acquire);

    /**
     * Lock action on the staff invitation details
     *
     * @param businessId business id to lock on
     * @param sub invitation sub to lock on
     * @param acquire whether to acquire/release lock
     */
    void lock(long businessId, String sub, boolean acquire);
}