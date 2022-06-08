package com.github.xiao;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author wang xiao
 * @date 2022/6/8
 */
public class MemoryLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {



    private final MemoryLimiter memoryLimiter;

    public MemoryLinkedBlockingQueue(int capacity) {
        super();
        this.memoryLimiter = null;
    }

    @Override
    public void put(E e) throws InterruptedException {
        memoryLimiter.acquireInterruptibly(e);
        super.put(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        memoryLimiter.releaseInterruptibly(e,timeout,unit);
        return super.offer(e, timeout, unit);
    }

    @Override
    public boolean offer(E e) {
        return memoryLimiter.acquire(e) && super.offer(e);
    }

    @Override
    public E take() throws InterruptedException {
        final E e = super.take();
        memoryLimiter.releaseInterruptibly(e);
        return e;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        final E e = super.poll(timeout, unit);
        memoryLimiter.releaseInterruptibly(e, timeout, unit);
        return e;
    }

    @Override
    public E poll() {
        final E e = super.poll();
        memoryLimiter.release(e);
        return e;
    }



    @Override
    public boolean remove(Object o) {
        final boolean success = super.remove(o);
        if (success) {
            memoryLimiter.release(o);
        }
        return success;
    }

    @Override
    public void clear() {
        super.clear();
        memoryLimiter.clear();
    }
}
