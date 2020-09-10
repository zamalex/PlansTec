package creativitysol.com.planstech.base

import io.reactivex.rxjava3.core.Scheduler

interface Executors {
    fun getMainThread(): Scheduler
    fun getIOThread(): Scheduler
}
