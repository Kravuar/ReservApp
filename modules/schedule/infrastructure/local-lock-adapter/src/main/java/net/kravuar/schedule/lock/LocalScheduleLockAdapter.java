package net.kravuar.schedule.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.schedule.ports.out.ScheduleLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalScheduleLockAdapter implements ScheduleLockPort {
    private final ConcurrentMap<Long, ReentrantLock> staffLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();
    private final ConcurrentMap<Long, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();

    @Override
    public void lock(long scheduleId, boolean acquire) {
        var lock = idLocks.computeIfAbsent(scheduleId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else if (lock.isHeldByCurrentThread())
            lock.unlock();
    }

    @Override
    public void lockByStaff(long staffId, boolean acquire) {
        var lock = staffLocks.computeIfAbsent(staffId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else if (lock.isHeldByCurrentThread())
            lock.unlock();
    }
}
