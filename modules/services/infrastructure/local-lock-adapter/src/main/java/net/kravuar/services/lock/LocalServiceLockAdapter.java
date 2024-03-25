package net.kravuar.services.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.services.ports.out.ServiceLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalServiceLockAdapter implements ServiceLockPort {
    private final ConcurrentMap<Long, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();

    @Override
    public void lock(long serviceId, boolean acquire) {
        var lock = idLocks.computeIfAbsent(serviceId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else if (lock.isHeldByCurrentThread())
            lock.unlock();
    }
}
