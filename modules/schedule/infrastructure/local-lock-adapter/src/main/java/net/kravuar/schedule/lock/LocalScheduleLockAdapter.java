package net.kravuar.schedule.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.ports.out.ScheduleLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalScheduleLockAdapter implements ScheduleLockPort {
    private final ConcurrentMap<Long, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();
    private final ConcurrentMap<StaffServiceKey, ReentrantLock> staffLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<StaffServiceKey, ReentrantLock>build().asMap();

    @Override
    public void lock(long scheduleId, boolean acquire) {
        var lock = idLocks.computeIfAbsent(scheduleId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else if (lock.isHeldByCurrentThread())
            lock.unlock();
    }

    @Override
    public void lockByStaffAndService(long staffId, long serviceId, boolean acquire) {
        var key = new StaffServiceKey(staffId, serviceId);
        var lock = staffLocks.computeIfAbsent(key, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else if (lock.isHeldByCurrentThread())
            lock.unlock();
    }

    record StaffServiceKey(long staffId, long serviceId) {
    }
}
