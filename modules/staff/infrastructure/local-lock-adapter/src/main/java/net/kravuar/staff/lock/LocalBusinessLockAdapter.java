package net.kravuar.staff.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.ports.out.BusinessLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalBusinessLockAdapter implements BusinessLockPort {
    private final ConcurrentMap<Long, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();

    @Override
    public void lock(long businessId, boolean acquire) {
        var lock = idLocks.computeIfAbsent(businessId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else
            lock.unlock();
    }
}
