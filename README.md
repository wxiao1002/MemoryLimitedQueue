# MemoryQueue
Java 实现内存限制的Block Queue, 防止OOM

## 说明
像ArrayBlockQueue 可以理解成无界队列，在线程池中使用会造成OOM,所以参考dubbo 的PR 实现，
<p>可以直接内存限制的队列，防止OOM,内存限制可以是100M</p>

## 问题
一般存储在链表中并不是真实的对象，而是附属对象，况且gc 情况下如何考虑
