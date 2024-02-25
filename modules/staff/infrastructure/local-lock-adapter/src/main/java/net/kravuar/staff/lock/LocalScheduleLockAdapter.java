package net.kravuar.staff.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.ports.out.ScheduleLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalScheduleLockAdapter implements ScheduleLockPort {
    private final ConcurrentMap<ScheduleKey, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<ScheduleKey, ReentrantLock>build().asMap();

    @Override
    public void lock(long serviceId, long staffId, boolean acquire) {
        ScheduleKey key = new ScheduleKey(serviceId, staffId);
        var lock = idLocks.computeIfAbsent(key, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else
            lock.unlock();
    }

    record ScheduleKey(long serviceId, long staffId) {}
}
