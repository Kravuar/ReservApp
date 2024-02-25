package net.kravuar.staff.lock;

import com.google.common.cache.CacheBuilder;
import net.kravuar.context.AppComponent;
import net.kravuar.staff.ports.out.StaffLockPort;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@AppComponent
class LocalStaffLockAdapter implements StaffLockPort {
    private final ConcurrentMap<InvitationKey, ReentrantLock> invitationLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<InvitationKey, ReentrantLock>build().asMap();
    private final ConcurrentMap<Long, ReentrantLock> idLocks = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .<Long, ReentrantLock>build().asMap();

    @Override
    public void lock(long staffId, boolean acquire) {
        var lock = idLocks.computeIfAbsent(staffId, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else
            lock.unlock();
    }

    @Override
    public void lock(long businessId, String sub, boolean acquire) {
        InvitationKey key = new InvitationKey(businessId, sub);
        var lock = invitationLocks.computeIfAbsent(key, (k) -> new ReentrantLock());
        if (acquire)
            lock.lock();
        else
            lock.unlock();
    }

    record InvitationKey(long businessId, String sub) {}
}
