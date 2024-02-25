package net.kravuar.business.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.business.ports.out.BusinessLockPort;
import net.kravuar.context.AppComponent;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalBusinessLockAdapter implements BusinessLockPort {
    private final ConcurrentMap<String, ReentrantLock> nameLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<String, ReentrantLock>build().asMap();
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

    @Override
    public void lock(String name, boolean acquire) {
        var lock = nameLocks.computeIfAbsent(name, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else
            lock.unlock();
    }
}
