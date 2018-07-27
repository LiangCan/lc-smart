//package com.mqtt.test;
//
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.RunnableScheduledFuture;
//
//private class ScheduledFutureTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
//    private final long sequenceNumber;
//    // 记录当前实例的序列号 private long time;
//// 记录当前任务下次开始执行的时间
//// 记录当前任务执行时间间隔，等于0则表示当前任务只执行一次，大于0表示当前任务为fixedRate类型的任务，
//// 小于0则表示其为fixedDelay类型的任务
//    private final long period;
//    RunnableScheduledFuture<V> outerTask = this;
//    // 记录需要周期性执行的任务的实例
//    int heapIndex;
//// 记录当前任务在队列数组中位置的下标
//
//    ScheduledFutureTask(Runnable r, V result, long ns, long period) {
//        super(r, result);
//        this.time = ns;
//        this.period = period;
//        this.sequenceNumber = sequencer.getAndIncrement();
//        // 序号在创建任务实例时指定，且后续不会变化 }
//
//    public long getDelay(TimeUnit unit) {
//        return unit.convert(time - now(), NANOSECONDS);
//    }
//
//    // 各个任务在队列中的存储方式是一个基于时间和序号进行比较的优先队列，当前方法定义了优先队列中两个
//// 任务执行的先后顺序。这里先对两个任务开始执行时间进行比较，时间较小者优先执行，若开始时间相同，
//// 则比较两个任务的序号，序号小的任务先执行
//    public int compareTo(Delayed other) {
//        if (other == this) return 0;
//        if (other instanceof ScheduledFutureTask) {
//            ScheduledFutureTask<?> x = (ScheduledFutureTask<?>) other;
//            long diff = time - x.time;
//            if (diff < 0) return -1;
//            else if (diff > 0) return 1;
//            else if (sequenceNumber < x.sequenceNumber) return -1;
//            else return 1;
//        }
//        long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
//        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
//    }
//
//    public boolean isPeriodic() {
//        // 判断是否为周期性任务
//        return period != 0;
//    }
//
//    // 当前任务执行之后，会判断当前任务是否为周期性任务，如果为周期性任务，那么就调用当前方法计算
//// 当前任务下次开始执行的时间。这里如果当前任务是fixedRate类型的任务(p > 0)，那么下次执行时间
//// 就是此次执行的开始时间加上时间间隔，如果当前任务是fixedDelay类型的任务(p < 0)，那么下次执行
//// 时间就是当前时间(triggerTime()方法会获取系统当前时间)加上任务执行时间间隔。可以看到，定频率
//// 和定延迟的任务的执行时间区别就在当前方法中进行了指定，因为调用当前方法时任务已经执行完成了，
//// 因而triggerTime()方法中获取的时间就是任务执行完成之后的时间点
//    private void setNextRunTime() {
//        long p = period;
//        if (p > 0) time += p;
//        else time = triggerTime(-p);
//    }
//
//    // 取消当前任务的执行，super.cancel(boolean)方法也即FutureTask.cancel(boolean)方法。该方法传入
//// true表示如果当前任务正在执行，那么立即终止其执行；传入false表示如果当前方法正在执行，那么等待其
//// 执行完成之后再取消当前任务。
//    public boolean cancel(boolean mayInterruptIfRunning) {
//        boolean cancelled = super.cancel(mayInterruptIfRunning);
//        // 判断是否设置了取消后移除队列中当前任务，是则移除当前任务
//        if (cancelled && removeOnCancel && heapIndex >= 0) remove(this);
//        return cancelled;
//    }
//
//    public void run() {
//        boolean periodic = isPeriodic();
//        // 判断是否为周期性任务
//        if (!canRunInCurrentRunState(periodic))
//        // 判断是否能够在当前状态下执行该任务
//            cancel(false); else if (!periodic)
//        // 如果能执行当前任务，但是任务不是周期性的，那么就立即执行该任务一次
//        ScheduledFutureTask.super.run(); else if (ScheduledFutureTask.super.runAndReset()) {
//            // 是周期性任务，则立即执行当前任务并且重置
//            setNextRunTime();
//            // 在当前任务执行完成后调用该方法计算当前任务下次执行的时间
//            reExecutePeriodic(outerTask);
//            // 将当前任务放入任务队列中以便下次执行
//        }
//    }
//}
//        }
