package com.mio.rxjavademo

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BooleanSupplier
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Author: Mioyuan
 * Date: 2024/11/7 9:49
 * Description: 辅助操作符
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/11/7 1.0 首次创建
 */
object RxJavaAssistance {
    private val TAG = "RXJava Test Assistance"

    fun schedule() {
        Observable.intervalRange(1, 5, 0, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(TAG, "操作一：$it  Observer的工作线程为：${Thread.currentThread().name}")
            }
            .observeOn(Schedulers.newThread())
            .doOnNext {
                Log.d(TAG, "操作二：$it  Observer的工作线程为：${Thread.currentThread().name}")
            }
            .subscribe(getObserver())
    }

    @SuppressLint("CheckResult")
    fun doOperation() {
        var disposable: Disposable? = null
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
            .doOnEach {
                Log.d(TAG, "doOnEach")
//                1 / 0 触发error
            }
            .doOnNext {
                Log.d(TAG, "doOnNext")
            }
            .doAfterNext {
                Log.d(TAG, "doAfterNext")
            }
            .doOnComplete {
                Log.d(TAG, "doOnComplete")
            }
            .doOnError {
                Log.d(TAG, "doOnError")
            }
            .doOnTerminate {
                Log.d(TAG, "doOnTerminate")
            }
            .doAfterTerminate {
                Log.d(TAG, "doAfterTerminate")
            }
            .doFinally {
                Log.d(TAG, "doFinally")
            }
            .doOnSubscribe {
                Log.d(TAG, "doOnSubscribe")
            }
            .doOnDispose {
                Log.d(TAG, "doOnDispose")
            }
            .subscribe({
                Log.d(TAG, "doNext==$it")
                if (it == 1L) {
                    disposable?.dispose()
                }
            }, {
                Log.d(TAG, "doError:$it")
            }, {
                Log.d(TAG, "doComplete")
            })
    }

    fun onErrorReturn() {
        Observable.create<Int> {
            it.onNext(1)
            it.onError(Throwable("发生错误啦"))
        }
            .onErrorReturn(object : Function<Throwable, Int> {
                override fun apply(p0: Throwable): Int {
                    return 666
                }
            })
            .subscribe(getObserver())
    }

    fun onErrorResumeNext() {
        Observable.create<Int> {
            it.onNext(1)
            it.onError(Throwable("发生错误啦"))
        }
            .onErrorResumeNext(Observable.just(666, 999))
            .subscribe(getObserver())
    }

    fun onExceptionResumeNext() {
        Observable.create<Int> {
            it.onNext(1)
            it.onError(Throwable("发生错误啦"))
        }
            .onExceptionResumeNext(Observable.just(666, 999))
            .subscribe(getObserver())
    }

    fun retryUntil() {
        var retryCount = 0
        Observable.create<Int> {
            it.onNext(1)
            it.onError(Throwable("发生错误啦"))
        }.retryUntil(object : BooleanSupplier {
            override fun getAsBoolean(): Boolean {
                return ++retryCount >= 3
            }
        }).subscribe(getObserver())
    }

    fun retryWhen() {
        var retryCount = 0
        Observable.create<Int> {
            it.onNext(1)
            it.onError(Throwable("发生错误啦"))
        }
            .retryWhen(object : Function<Observable<Throwable>, Observable<Int>> {
                override fun apply(p0: Observable<Throwable>): Observable<Int> {
                    return p0.flatMap {
                        if (++retryCount <= 3) {
                            Observable.create {
                                it.onNext(1)
                            }
                        } else {
                            Observable.error(it)
                        }
                    }
                }
            })
            .subscribe(getObserver())
    }

    fun delay() {
        Observable.intervalRange(1, 3, 0, 1, TimeUnit.SECONDS)
            .delay(2L, TimeUnit.SECONDS, true)
            .subscribe(getObserver())
    }

    fun delayByObservable() {
        Observable.intervalRange(1, 3, 0, 3, TimeUnit.SECONDS)
            .delay(Observable.timer(2, TimeUnit.SECONDS), object : Function<Long, Observable<Long>> {
                override fun apply(p0: Long): Observable<Long> {
                    return Observable.timer(2, TimeUnit.SECONDS)
                }
            })
            .subscribe(getObserver())
    }

    fun delaySubscription() {
        Observable.intervalRange(1, 3, 0, 2, TimeUnit.SECONDS)
            .delaySubscription(6L, TimeUnit.SECONDS)
            .subscribe(getObserver())
    }

    /**
     * timeout(long timeout, TimeUnit timeUnit, ObservableSource<? extends T> other)
     */
    fun timeout1() {
        Observable.timer(5, TimeUnit.SECONDS)
            .timeout(2L, TimeUnit.SECONDS) {
                it.onNext(666)
                it.onComplete()
            }
            .subscribe(getObserver())
    }

    /**
     * timeout(Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, ObservableSource<? extends T> other)
     */
    fun timeout2() {
        Observable.just(1, 2, 3)
            .concatMap {
                Observable.just(it).delay(2L, TimeUnit.SECONDS)
            }.timeout(object : Function<Int, Observable<Long>> {
                override fun apply(p0: Int): Observable<Long> {
                    return Observable.timer(1, TimeUnit.SECONDS)
                }
            }, Observable.just(666))
            .subscribe(getObserver())
    }

    @SuppressLint("CheckResult")
    fun timeInterval() {
        Observable.intervalRange(1, 3, 0, 2, TimeUnit.SECONDS)
            .timeInterval(TimeUnit.SECONDS)
            .subscribe {
                Log.d(TAG, "接收到了Time事件:间隔-${it.time()} 原始值-${it.value()}")
            }
    }

    @SuppressLint("CheckResult")
    fun timestamp() {
        Observable.intervalRange(1, 3, 0, 2, TimeUnit.SECONDS)
            .timestamp()
            .subscribe {
                Log.d(TAG, "接收到了Time事件:时间戳-${it.time()} 原始值-${it.value()}")
            }
    }

    fun repeatWhen() {
        var count = 0
        Observable.range(1, 3)
            .repeatWhen { completed ->
                if (++count <= 2) {
                    Observable.just(666).delay(3, TimeUnit.SECONDS)
                } else {
                    Observable.error<Any>(Throwable("中止repeat"))
                }
            }
            .subscribe(getObserver())
    }

    fun serialize() {
        Observable.create<String> {
            Thread {
                for (i in 1..3) {
                    it.onNext("thread1 $i")
                    if (i == 3) {
                        it.onComplete()
                    }
                }
            }.start()
            Thread {
                for (i in 1..3) {
                    it.onNext("thread2 $i")
                }
            }.start()
        }
            .serialize()
            .subscribe(getObserver())
    }

    fun toList() {
        Observable.just(1, 2, 3)
            .toList(object : Callable<MutableList<Int>> {
                override fun call(): MutableList<Int> {
                    return mutableListOf()
                }
            })
            .subscribe(object : SingleObserver<MutableList<Int>> {
                override fun onSubscribe(p0: Disposable) {
                    Log.d(TAG, "开始采用subscribe连接")
                }

                override fun onError(p0: Throwable) {
                    Log.d(TAG, "对Error事件作出响应:$p0")
                }

                override fun onSuccess(p0: MutableList<Int>) {
                    Log.d(TAG, "接收到了事件:$p0")
                }
            })
    }

    fun using() {
        Observable.using(object : Callable<Int> {
            override fun call(): Int {
                return 666
            }
        }, object : Function<Int, Observable<String>> {
            override fun apply(p0: Int): Observable<String> {
                return Observable.just("发射创建的数据：$p0")
            }
        }, object : Consumer<Int> {
            override fun accept(p0: Int?) {
                Log.d(TAG, "释放资源")
            }
        }, false).subscribe(getObserver())
    }

    fun materialize() {
        Observable.just(1, 2, 3)
            .materialize()
            .doOnNext {
                Log.d(TAG, "doOnNext===$it")
            }
            .dematerialize {
                when {
                    it.isOnNext -> Notification.createOnNext(it.value as Int)
                    it.isOnComplete -> Notification.createOnComplete()
                    else -> Notification.createOnError(it.error ?: Throwable())
                }
            }
            .subscribe(getObserver())
    }
}