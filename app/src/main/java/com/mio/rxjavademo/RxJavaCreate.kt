package com.mio.rxjavademo

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Author: Mioyuan
 * Date: 2024/10/22 16:53
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/10/22 1.0 首次创建
 */
object RxJavaCreate {
    val TAG = "RXJava Test Create"

    fun empty() {
        Observable.empty<Int>().subscribe(getObserver<Int>())
    }

    fun never() {
        Observable.never<Int>().subscribe(getObserver<Int>())
    }

    fun error() {
        Observable.error<Throwable>(object : Throwable("自定义错误类型") {})
            .subscribe(object : Observer<Throwable> {
                override fun onSubscribe(p0: Disposable) {
                    Log.d(TAG, "开始采用subscribe连接")
                }

                override fun onError(p0: Throwable) {
                    Log.d(TAG, "对Error事件作出响应 $p0")
                }

                override fun onComplete() {
                    Log.d(TAG, "对Complete事件作出响应")
                }

                override fun onNext(p0: Throwable) {
                    Log.d(TAG, "接收到了事件${p0.message}")
                }
            })
    }

    fun defer() {
        var value = 1
        val defer = Observable.defer(object : Callable<Observable<Int>> {
            override fun call(): Observable<Int> {
                return Observable.just(value)
            }
        })
        value = 5
        defer.subscribe(getObserver())
    }

    fun timer() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(getObserver<Long>())
    }

    fun interval() {
        Observable.interval(1, 5, TimeUnit.SECONDS).subscribe(getObserver<Long>())
    }

    fun intervalRange() {
        Observable.intervalRange(3, 4, 0, 1, TimeUnit.SECONDS).subscribe(getObserver<Long>())
    }

    fun range() {
        Observable.range(3, 4).subscribe(getObserver<Int>())
    }
}